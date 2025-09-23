import os
import torch
from torch.utils.data import Dataset, DataLoader
import numpy as np
import cv2
from torchvision import transforms
import matplotlib.pyplot as plt


class SegmentationDataset(Dataset):
    def __init__(self, data_dir, mask_dir, transform=None, output_size=(480, 640), num_classes=2):
        """
        语义分割 Dataset：读取图像和对应的Mask，支持多类别

        参数：
            data_dir: 图像文件路径
            mask_dir: Mask图像文件路径
            transform: 图像预处理
            output_size: (H, W) = (480, 640)
            num_classes: 类别数量（包括背景）
        """
        self.data_dir = data_dir
        self.mask_dir = mask_dir
        self.transform = transform
        self.output_size = output_size
        self.num_classes = num_classes

        # 获取所有图像文件
        self.img_files = [f for f in os.listdir(data_dir) if f.endswith(('.jpg', '.png', '.jpeg'))]

        # 确保每个图像都有对应的Mask
        self.valid_files = []
        for img_file in self.img_files:
            mask_file = self.get_mask_name(img_file)
            mask_path = os.path.join(mask_dir, mask_file)
            if os.path.exists(mask_path):
                self.valid_files.append(img_file)
            else:
                print(f"警告: 找不到对应的Mask文件: {mask_file}")

        print(f"找到 {len(self.valid_files)} 对有效的图像-Mask对")
        print(f"类别数量: {num_classes}")

    def get_mask_name(self, img_name):
        """根据图像文件名生成对应的Mask文件名"""
        # 根据你的文件命名规则修改这个函数
        name, ext = os.path.splitext(img_name)
        return f"{name}_mask.png"

    def __len__(self):
        return len(self.valid_files)

    def __getitem__(self, idx):
        img_name = self.valid_files[idx]
        img_path = os.path.join(self.data_dir, img_name)
        mask_name = self.get_mask_name(img_name)
        mask_path = os.path.join(self.mask_dir, mask_name)

        # 1. 读取图像
        img = cv2.imread(img_path)
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)  # 转换为RGB

        # 2. 读取Mask
        mask = cv2.imread(mask_path, cv2.IMREAD_GRAYSCALE)  # 以灰度模式读取

        # 3. 确保Mask中的像素值是有效的类别标签
        unique_vals = np.unique(mask)
        # 检查是否有超出类别范围的像素值
        if len(unique_vals) > 0:
            max_val = max(unique_vals)
            if max_val >= self.num_classes:
                print(f"警告: Mask {mask_name} 包含超出类别范围的像素值 {max_val} (最大允许: {self.num_classes - 1})")

        # 4. 调整大小
        if self.output_size:
            img = cv2.resize(img, (self.output_size[1], self.output_size[0]))  # OpenCV使用 (宽, 高)
            mask = cv2.resize(mask, (self.output_size[1], self.output_size[0]),
                              interpolation=cv2.INTER_NEAREST)  # 对Mask使用最近邻插值

        # 5. 应用变换（如果有）
        if self.transform:
            # 注意：可能需要为图像和Mask分别定义不同的变换
            img = self.transform(img)
            # 对于Mask，通常只需要调整大小和转换为张量
            mask = torch.tensor(mask, dtype=torch.float32)  # 转换为LongTensor用于分类任务
        else:
            # 默认转换
            img = transforms.ToTensor()(img)
            mask = torch.tensor(mask, dtype=torch.float32)

        return img, mask  # img: [3, H, W], mask: [H, W]

    def get_class_weights(self):
        """
        计算每个类别的权重，用于处理类别不平衡问题
        返回一个长度为num_classes的权重张量
        """
        # 统计所有Mask中每个类别的像素数量
        class_pixels = np.zeros(self.num_classes, dtype=np.int64)
        total_pixels = 0

        for img_name in self.valid_files:
            mask_name = self.get_mask_name(img_name)
            mask_path = os.path.join(self.mask_dir, mask_name)
            mask = cv2.imread(mask_path, cv2.IMREAD_GRAYSCALE)

            # 统计每个类别的像素数量
            for class_id in range(self.num_classes):
                class_pixels[class_id] += np.sum(mask == class_id)

            total_pixels += mask.size

        # 计算每个类别的权重（使用逆频率加权）
        class_weights = total_pixels / (self.num_classes * class_pixels + 1e-6)  # 加1e-6避免除以零

        # 归一化权重
        class_weights = class_weights / np.sum(class_weights)

        return torch.tensor(class_weights, dtype=torch.float32)


if __name__ == "__main__":
    # 定义图像预处理
    transform = transforms.Compose([
        transforms.ToTensor(),  # 将numpy数组转换为torch张量
    ])

    # 数据路径
    data_dir = r'D:\MyData\pub-project\small-frame-demo\1-py-test\ai_study\torch\Unet\imgs\train_img'
    mask_dir = r'D:\MyData\pub-project\small-frame-demo\1-py-test\ai_study\torch\Unet\imgs\train_mask'  # 假设Mask文件存放在这里

    # 创建dataset和dataloader
    dataset = SegmentationDataset(
        data_dir=data_dir,
        mask_dir=mask_dir,
        transform=transform,
        output_size=(480, 640),
        num_classes=2  # 假设是二分类问题（例如棋盘格角点和背景）
    )

    # 计算类别权重（用于处理类别不平衡）
    class_weights = dataset.get_class_weights()
    print(f"类别权重: {class_weights}")

    dataloader = DataLoader(dataset, batch_size=2, shuffle=True)

    # 测试
    for imgs, masks in dataloader:
        print("Image shape:", imgs.shape)  # [4, 3, 480, 640]
        print("Mask shape:", masks.shape)  # [4, 480, 640]

        # 显示第一个样本
        img = imgs[0].permute(1, 2, 0).numpy()  # 转换为HWC格式
        mask = masks[0].numpy()

        # 反标准化图像
        img = img
        img = np.clip(img, 0, 1)

        # 显示图像和Mask
        plt.figure(figsize=(10, 5))
        plt.subplot(1, 2, 1)
        plt.imshow(img)
        plt.title('Image')
        plt.axis('off')

        plt.subplot(1, 2, 2)
        plt.imshow(mask, cmap='jet')
        plt.title('Mask')
        plt.axis('off')

        plt.show()
        break  # 只显示第一个batch
