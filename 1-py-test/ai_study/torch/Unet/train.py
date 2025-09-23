import torch
import torch.nn.functional as F
import torch.nn as nn
from model import ResUNetSegmentation
from dataset import SegmentationDataset  # 修改为使用SegmentationDataset
import torch.optim as optim
from torch.utils.data import DataLoader
from torch.optim.lr_scheduler import ExponentialLR
from tqdm import tqdm
import matplotlib.pyplot as plt
import os
from torchvision import transforms
import numpy as np


class FocalLoss(nn.Module):
    def __init__(self, alpha=0.9, gamma=2.0, reduction='mean'):
        super().__init__()
        self.alpha = alpha
        self.gamma = gamma
        self.reduction = reduction

    def forward(self, logits, labels):
        BCE_loss = F.binary_cross_entropy_with_logits(logits, labels, reduction='none')
        pt = torch.sigmoid(logits)
        pt = torch.where(labels == 1, pt, 1 - pt)
        focal_weight = (1 - pt) ** self.gamma
        alpha_t = self.alpha * labels + (1 - self.alpha) * (1 - labels)
        loss = alpha_t * focal_weight * BCE_loss
        if self.reduction == 'mean':
            return loss.mean()
        elif self.reduction == 'sum':
            return loss.sum()
        else:
            return loss


def compute_miou(preds, labels, num_classes=2):
    """
    计算 mIoU（Mean Intersection over Union）

    Args:
        preds: 预测结果 [B, 1, H, W]，通过 sigmoid 得到的二值化预测
        labels: 真实标签 [B, 1, H, W]
        num_classes: 类别数量，默认为 2 类（前景和背景）

    Returns:
        miou: 平均交并比（mIoU）
    """
    # 将预测和标签转换为二值化结果
    preds = (torch.sigmoid(preds) > 0.5).float()
    labels = labels.float()

    # 初始化交集和并集
    intersection = torch.zeros(num_classes).to(preds.device)
    union = torch.zeros(num_classes).to(preds.device)

    for i in range(num_classes):
        # 当前类别的预测和标签
        pred_i = (preds == i).float()
        label_i = (labels == i).float()

        # 计算交集和并集
        intersection[i] = (pred_i * label_i).sum()
        union[i] = pred_i.sum() + label_i.sum() - intersection[i]

    # 计算每个类别的 IoU
    iou = intersection / (union + 1e-6)  # 防止除以零
    miou = iou.mean()  # mIoU 是所有类别 IoU 的平均值

    return miou.item()


if __name__ == "__main__":
    EPOCHS = 55
    BATCH_SIZE = 4
    LEARNING_RATE = 1e-4
    DEVICE = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    MODEL_SAVE_PATH = "./checkpoints"
    os.makedirs(MODEL_SAVE_PATH, exist_ok=True)
    WEIGHTED_LOSS = False
    USE_FOCAL_LOSS = True  # 使用 Focal Loss
    GAMMA = 2.0  # Focal Loss 参数
    ALPHA = 0.9  # Focal Loss 正样本权重
    IMG_SIZE = (480, 640)
    NUM_CLASSES = 2

    # model = ResUNetSegmentation()
    model = ResUNetSegmentation()
    model = model.to(DEVICE)

    # 数据路径 - 修改为使用图像和mask分开的路径
    train_img_dir = r'F:\3DVideo\Unet\imgs\train_img'
    train_mask_dir = r'F:\3DVideo\Unet\imgs\train_mask'
    val_img_dir = r'F:\3DVideo\Unet\imgs\val_img'
    val_mask_dir = r'F:\3DVideo\Unet\imgs\val_mask'

    # 创建 dataset 和 dataloader - 使用SegmentationDataset
    train_dataset = SegmentationDataset(
        data_dir=train_img_dir,
        mask_dir=train_mask_dir,
        output_size=IMG_SIZE,
        num_classes=NUM_CLASSES
    )
    train_dataloader = DataLoader(train_dataset, batch_size=BATCH_SIZE, shuffle=True, num_workers=4)

    val_dataset = SegmentationDataset(
        data_dir=val_img_dir,
        mask_dir=val_mask_dir,
        output_size=IMG_SIZE,
        num_classes=NUM_CLASSES
    )
    val_dataloader = DataLoader(val_dataset, batch_size=BATCH_SIZE, shuffle=False, num_workers=4)

    # 计算类别权重（用于处理类别不平衡）
    class_weights = train_dataset.get_class_weights().to(DEVICE)
    print(f"类别权重: {class_weights}")

    checkpoint_path = os.path.join(MODEL_SAVE_PATH, "best_model.pth")
    start_epoch = 0
    best_val_miou = 0.0  # 改为 best_val_miou 更准确
    best_val_loss = float('inf')
    current_lr = LEARNING_RATE

    if os.path.exists(checkpoint_path):
        print(f"加载检查点: {checkpoint_path}")
        checkpoint = torch.load(checkpoint_path, map_location=DEVICE)
        model.load_state_dict(checkpoint['model_state_dict'])
        best_val_miou = checkpoint['best_val_miou']
        best_val_loss = checkpoint['best_val_loss']
        current_lr = checkpoint.get('current_lr', LEARNING_RATE)
        start_epoch = checkpoint.get('epoch', 0)
        print(f"恢复 epoch={start_epoch}, mIoU={best_val_miou:.4f}, loss={best_val_loss:.4f}, lr={current_lr}")

    # 优化器
    optimizer = optim.AdamW(model.parameters(), lr=current_lr, weight_decay=0.0005)

    # 学习率衰减：每 epoch 衰减一次
    scheduler = ExponentialLR(optimizer, gamma=0.98)

    # 损失函数
    if USE_FOCAL_LOSS:
        criterion = FocalLoss(alpha=ALPHA, gamma=GAMMA, reduction='mean').to(DEVICE)
    elif WEIGHTED_LOSS:
        # 使用计算得到的类别权重
        criterion = nn.BCEWithLogitsLoss(pos_weight=class_weights[1])  # 假设类别1是正样本
    else:
        criterion = nn.BCEWithLogitsLoss()

    train_losses = []
    train_mious = []  # 改为 mious 更准确
    val_losses = []
    val_mious = []  # 改为 mious 更准确

    for epoch in range(start_epoch, EPOCHS):
        print(f"\n{'=' * 50}")
        print(f"EPOCH {epoch + 1}/{EPOCHS}")

        model.train()
        epoch_train_loss = 0.0
        epoch_train_miou = 0.0  # mIoU 初始化
        train_loop = tqdm(train_dataloader, desc="Training")
        for batch_idx, (images, masks) in enumerate(train_loop):
            images = images.to(DEVICE)  # [B, 1, 480, 640]
            masks = masks.to(DEVICE).unsqueeze(1)  # [4, 480, 640]

            optimizer.zero_grad()
            outputs = model(images)  # [B, 1, 480, 640]

            # 计算损失
            loss = criterion(outputs, masks)
            loss.backward()
            optimizer.step()

            # 累加损失
            epoch_train_loss += loss.item() * images.size(0)

            # 计算 mIoU
            with torch.no_grad():
                miou = compute_miou(outputs, masks)
                epoch_train_miou += miou * images.size(0)  # 乘以batch size

            # 更新进度条显示
            train_loop.set_postfix({
                'Loss': f'{loss.item():.4f}',
                'mIoU': f'{miou:.4f}'
            })

        # 计算 epoch 平均指标
        avg_train_loss = epoch_train_loss / len(train_dataset)
        avg_train_miou = epoch_train_miou / len(train_dataset)  # 除以总样本数
        train_losses.append(avg_train_loss)
        train_mious.append(avg_train_miou)

        print(f"Train Loss: {avg_train_loss:.6f}, mIoU: {avg_train_miou:.4f}")

        # --- 验证阶段 ---
        model.eval()
        epoch_val_loss = 0.0
        epoch_val_miou = 0.0  # mIoU 初始化

        val_loop = tqdm(val_dataloader, desc="Validating")
        with torch.no_grad():
            for batch_idx, (images, masks) in enumerate(val_loop):
                images = images.to(DEVICE)
                masks = masks.to(DEVICE).unsqueeze(1)
                outputs = model(images)  # [B, 1, 480, 640]

                loss = criterion(outputs, masks)
                epoch_val_loss += loss.item() * images.size(0)

                # 计算 mIoU
                miou = compute_miou(outputs, masks)
                epoch_val_miou += miou * images.size(0)  # 乘以batch size

                # 更新进度条显示
                val_loop.set_postfix({
                    'Loss': f'{loss.item():.4f}',
                    'mIoU': f'{miou:.4f}'
                })

        # 计算 epoch 平均指标
        avg_val_loss = epoch_val_loss / len(val_dataset)
        avg_val_miou = epoch_val_miou / len(val_dataset)  # 除以总样本数
        val_losses.append(avg_val_loss)
        val_mious.append(avg_val_miou)

        print(f"Val Loss: {avg_val_loss:.6f}, mIoU: {avg_val_miou:.4f}")

        # --- 学习率更新 ---
        scheduler.step()
        current_lr = optimizer.param_groups[0]['lr']
        print(f"Current LR: {current_lr:.6e}")

        # --- 保存最佳模型 ---
        if avg_val_miou > best_val_miou:  # 使用 mIoU 作为评估标准
            best_val_miou = avg_val_miou
            best_val_loss = avg_val_loss
            checkpoint = {
                'epoch': epoch + 1,
                'model_state_dict': model.state_dict(),
                'optimizer_state_dict': optimizer.state_dict(),
                'scheduler_state_dict': scheduler.state_dict(),
                'best_val_miou': best_val_miou,  # 改为 mIoU
                'best_val_loss': best_val_loss,
                'train_losses': train_losses,
                'val_losses': val_losses,
                'train_mious': train_mious,  # 改为 mious
                'val_mious': val_mious,  # 改为 mious
                'class_weights': class_weights.cpu()  # 保存类别权重
            }
            save_path = os.path.join(MODEL_SAVE_PATH, "best_model.pth")
            torch.save(checkpoint, save_path)
            print(f"✅ Best model saved! mIoU: {best_val_miou:.4f}, Loss: {best_val_loss:.6f}")

        # 定期保存检查点
        if (epoch + 1) % 10 == 0:
            checkpoint_path = os.path.join(MODEL_SAVE_PATH, f"checkpoint_epoch_{epoch + 1}.pth")
            torch.save(checkpoint, checkpoint_path)
            print(f"📁 Checkpoint saved at epoch {epoch + 1}")

    # -------------------------------
    # 绘制训练曲线
    # -------------------------------
    plt.figure(figsize=(12, 5))

    plt.subplot(1, 2, 1)
    plt.plot(train_losses, label='Train Loss')
    plt.plot(val_losses, label='Val Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.title('Loss Curve')
    plt.legend()

    plt.subplot(1, 2, 2)
    plt.plot(train_mious, label='Train mIoU')
    plt.plot(val_mious, label='Val mIoU')
    plt.xlabel('Epoch')
    plt.ylabel('mIoU')
    plt.title('mIoU Curve')
    plt.legend()

    plt.tight_layout()
    plt.savefig('training_validation_curve.png')
    plt.show()

    print("✅ 训练完成，曲线已保存。")