import os
import random

import cv2
import matplotlib.pyplot as plt
import numpy as np
import torch
import torch.nn as nn
import torchvision
from PIL import Image
from PIL import ImageEnhance
# 供dataset和dataloader使用的
from torch.utils.data.dataset import Dataset
from torchvision import transforms

from tqdm import tqdm


class Conv(nn.Module):
    """
    定义一个可以复用的conv-relu模块
    """

    def __init__(self, in_c, out_c, k, s, p=0, is_act=True):
        super().__init__()
        # 卷积
        self.conv = nn.Conv2d(in_c, out_c, k, s, padding=p)
        self.act = nn.ReLU()
        self.is_act = is_act

    def forward(self, input_x):
        if self.is_act:
            return self.act(self.conv(input_x))
        return self.conv(input_x)


class MaxPool(nn.Module):
    def __init__(self, k, s, p=0):
        super().__init__()
        self.max_pool = nn.MaxPool2d(k, s, padding=p)

    def forward(self, input_x):
        return self.max_pool(input_x)


def age_to_group(age):
    """将年龄转换为分组标签（0-9），左闭右开区间"""
    age = int(age)  # 确保 age 是整数
    if age < 8:
        group = 0  # 0-7 岁 → 组 0
    elif 8 <= age < 16:
        group = 1  # 8-15 岁 → 组 1
    elif 16 <= age < 22:
        group = 2  # 16-21 岁 → 组 2
    elif 22 <= age < 30:
        group = 3  # 22-29 岁 → 组 3
    elif 30 <= age < 38:
        group = 4  # 30-37 岁 → 组 4 (补全缺失区间)
    elif 38 <= age < 48:
        group = 5  # 38-47 岁 → 组 5
    elif 48 <= age < 58:
        group = 6  # 48-57 岁 → 组 6
    elif 58 <= age < 68:
        group = 7  # 58-67 岁 → 组 7
    elif 68 <= age < 78:
        group = 8  # 68-77 岁 → 组 8
    elif 78 <= age < 88:
        group = 9  # 78-87 岁 → 组 9
    else:
        group = 10  # 88+ 岁 → 组 10 (原代码漏了88-110)
    return group


# 继承torch.dataset 这个库
class MyDataSet(Dataset):
    def __init__(self, path, device, is_train=True, transforms=None):
        """用来做一些初始化"""
        self.all_imgs = os.listdir(path)
        # 如果在这里有可能会导致数据泄露
        # random.shuffle(self.all_imgs)
        cout_all = len(self.all_imgs)
        train_num = int(cout_all * 0.8)
        if is_train:
            self.all_imgs = self.all_imgs[0:train_num]
            # 训练的数据被打乱。而测试的数据不会
            random.shuffle(self.all_imgs)
        else:
            self.all_imgs = self.all_imgs[train_num:cout_all]
        self.root = path
        self.device = device
        self.transform = transforms
        self.is_train = is_train

    def _get_label_from_image_name(self, file_name):
        label = int(file_name.split("_")[0])
        label = int(label)
        label = age_to_group(label)

        return torch.tensor(label, dtype=torch.float32).to(self.device)

    def _get_image_bgr(self, file_name):
        image = cv2.imread(f"{self.root}\\{file_name}")
        gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

        if self.transform:
            norm_gray_image = self.transform(gray_image)
            return norm_gray_image
        else:
            # 自己来进行数据的转换
            resize_gray_image = cv2.resize(gray_image, (224, 224))
            resize_gray_image = np.transpose(resize_gray_image, axes=[2, 1, 0])
            norm_gray_image = resize_gray_image / 255.0
            return torch.tensor(norm_gray_image, dtype=torch.float32).to(self.device)

    def __getitem__(self, item):
        file_name = self.all_imgs[item]
        gray_img = self._get_image_bgr(file_name)
        label_index = self._get_label_from_image_name(file_name)

        return gray_img, label_index

    def __len__(self):
        """返回整个数据集的数量"""
        return len(self.all_imgs)


class Vgg16_Sub(nn.Module):
    def __init__(self, class_num=10):
        super().__init__()
        self.class_num = class_num

        self.conv1_1 = Conv(3, 64, 3, 1, 1)
        self.conv1_2 = Conv(64, 64, 3, 1, 1)
        self.max_pool1 = MaxPool(2, 2)

        self.conv2_1 = Conv(64, 128, 3, 1, 1)
        self.conv2_2 = Conv(128, 128, 3, 1, 1)
        self.max_pool2 = MaxPool(2, 2)

        self.conv3_1 = Conv(128, 256, 3, 1, 1)
        self.conv3_2 = Conv(256, 256, 3, 1, 1)
        self.conv3_3 = Conv(256, 256, 3, 1, 1)
        self.max_pool3 = MaxPool(2, 2)

        self.conv4_1 = Conv(256, 512, 3, 1, 1)
        self.conv4_2 = Conv(512, 512, 3, 1, 1)
        self.conv4_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool4 = MaxPool(2, 2)

        self.conv4_1x1 = Conv(512, 256, 1, 1)
        # self.conv5_1 = Conv(512, 512, 3, 1, 1)
        # self.conv5_2 = Conv(512, 512, 3, 1, 1)
        # self.conv5_3 = Conv(512, 512, 3, 1, 1)
        # self.max_pool5 = MaxPool(2, 2)  # 这里的输出 7x7x512

        self.fc1 = nn.Linear(14 * 14 * 256, 512)
        self.fc2 = nn.Linear(512, 256)
        self.fc3 = nn.Linear(256, self.class_num)

        self.flat = nn.Flatten()
        self.relu = nn.ReLU()

    def forward(self, x):
        x = self.conv1_1(x)  #
        x = self.conv1_2(x)  #
        x = self.max_pool1(x)  # 1x64x112x112

        x = self.conv2_1(x)
        x = self.conv2_2(x)
        x = self.max_pool2(x)

        x = self.conv3_1(x)
        x = self.conv3_2(x)
        x = self.conv3_3(x)
        x = self.max_pool3(x)

        x = self.conv4_1(x)
        x = self.conv4_2(x)
        x = self.conv4_3(x)
        x = self.max_pool4(x)  # 256 x 14 x 14

        x = self.conv4_1x1(x)  # 增加1个1x1卷积，用来减少进入全连接的参数量
        # x = self.conv5_1(x)
        # x = self.conv5_2(x)
        # x = self.conv5_3(x)
        # x = self.max_pool5(x) # 512x7x7

        x = self.flat(x)
        x = self.fc1(x)
        x = self.relu(x)

        x = self.fc2(x)
        x = self.relu(x)

        out = self.fc3(x)
        return out


class RandomSharpness(object):
    def __init__(self, sharpness_factor=1.5, p=0.5):
        self.sharpness_factor = sharpness_factor
        self.p = p

    def __call__(self, img):
        if random.random() < self.p:
            enhancer = ImageEnhance.Sharpness(img)
            img = enhancer.enhance(self.sharpness_factor)
        return img


class RandomCutout(object):
    def __init__(self, n_holes=1, length=8, fill_value=0):
        self.n_holes = n_holes  # 打孔数量
        self.length = length  # 打孔的尺寸
        self.fill_value = fill_value  # 填充颜色

    def __call__(self, img):
        """
        img: PIL Image
        返回增强后的图像
        """
        img = np.array(img)  # 转换为numpy数组
        h, w, _ = img.shape

        for _ in range(self.n_holes):
            # 随机选择起始点
            y = random.randint(0, h - self.length)
            x = random.randint(0, w - self.length)

            # 打孔：填充为指定的值
            img[y:y + self.length, x:x + self.length, :] = self.fill_value

        return Image.fromarray(img)


if __name__ == "__main__":
    path = r"F:/data_source/age_face/crop_part1"
    device = "cuda:0" if torch.cuda.is_available() else "cpu"

    my_transform = transforms.Compose([
        transforms.Resize((224, 224)),  # 先调整图片尺寸
        # ---------------- 数据增强，顺序不要搞反
        transforms.RandomEqualize(p=0.5),  # 增强：随机均衡化
        transforms.RandomHorizontalFlip(p=0.1),  # 增强：随机水平翻转
        transforms.RandomRotation(5),  # 增强：随机旋转15度
        transforms.RandomVerticalFlip(p=0.1),  # 增强：随机垂直翻转
        # 其他增强操作可以按需打开，比如：
        transforms.ColorJitter(brightness=0.2, contrast=0.2, saturation=0.2, hue=0.1),
        # transforms.RandomAffine(degrees=10, translate=(0.1, 0.1), scale=(0.8, 1.2), shear=10),
        # transforms.RandomPerspective(distortion_scale=0.5, p=0.1),
        # transforms.RandomPosterize(bits=4, p=0.1),
        # RandomSharpness(sharpness_factor=2, p=0.5),
        transforms.GaussianBlur(kernel_size=(5, 5), sigma=(0.1, 2.0)),
        RandomCutout(n_holes=8, length=8, fill_value=114),
        # ----------------
        transforms.ToTensor(),  # 转换为Tensor
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])  # 最后标准化
    ])

    val_transform = transforms.Compose([
        transforms.Resize((224, 224)),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])  # 标准化
    ])

    # is_auto_aug = False
    # if is_auto_aug:
    #     train_dataset = MyDataSet(path, device, transforms=my_transform,is_train=True)
    # else:
    #     train_dataset = MyDataSet(path, device, transforms=None,is_train=True)

    num_classes = 2  # 对应之前年龄分组的0-10组

    model = torchvision.models.resnet34(pretrained=False)
    model.load_state_dict(torch.load('./resnet34-b627a593.pth'))
    model.fc = torch.nn.Linear(model.fc.in_features, num_classes)
    # 冻结所有层
    for param in model.parameters():
        param.requires_grad = False
    # 解冻所有参数进行训练 全量更新
    # for i, (name, module) in enumerate(model.named_modules()):
    #     print(name)
    #     module.requires_grad = True

    # 部分权重更新。微调
    for name, module in model.named_modules():
        if name.startswith('layer4'):
            for param in module.parameters():
                param.requires_grad = True

    # 解冻最后的全连接层 (fc)
    model.fc.requires_grad = True

    # model = torchvision.models.mobilenet_v3_small(pretrained=False)
    # model.load_state_dict(torch.load('./mobilenet_v3_small-047dcff4.pth'))
    # model.classifier[3] = torch.nn.Linear(model.classifier[3].in_features, num_classes)

    # model = torchvision.models.efficientnet_b1(pretrained=False)
    # model.load_state_dict(torch.load('./efficientnet_b1_rwightman-bac287d4.pth'))
    # model.classifier[1] = torch.nn.Linear(model.classifier[1].in_features, num_classes)

    model.to(device)
    # 训练集用增强后的 transform
    train_dataset = torchvision.datasets.ImageFolder(
        root=r"F:\data_source\age_face\age_face4\train",
        transform=my_transform
    )
    # 验证集不增强的 transform
    test_dataset = torchvision.datasets.ImageFolder(
        root=r"F:\data_source\age_face\age_face4\val",
        transform=val_transform
    )

    batch_size = 16
    train_dataloader = torch.utils.data.DataLoader(
        dataset=train_dataset,
        batch_size=batch_size,
        shuffle=True
    )

    # val_dataset = MyDataSet(path, device, is_train=False)
    val_dataloader = torch.utils.data.DataLoader(
        dataset=test_dataset,
        batch_size=batch_size,
        shuffle=False
    )
    # 假设类别数量（根据你的任务修改）

    EPOCHS = 35  # 设置epoch次数
    isLoad = False
    isWeight = False
    init_lr = 1e-5
    current_lr = init_lr
    # model = Vgg16_Sub(class_num=num_classes).to(device)

    if isLoad and os.path.exists('./best.pt'):
        print(f'加载初始权重best.pt')
        checkpoint = torch.load('best.pt')
        model.load_state_dict(checkpoint['model_state_dict'])
        current_lr = checkpoint['current_lr']
        best_val_accuracy = checkpoint['best_val_accuracy']
        best_val_loss = checkpoint['best_val_loss']
        print(f'历史学习率={current_lr},上次验证损失={best_val_loss}，验证acc={best_val_accuracy}')
        # init_lr = current_lr
    # VGG的学习率
    optim = torch.optim.AdamW(model.parameters(), lr=init_lr)
    # 每训练10次缩小0.1
    # scheduler = StepLR(optim, step_size=20, gamma=0.1)
    # 设置损失函数
    """
        class_weights = torch.tensor([1.0, 5.0]).float()
        # 创建损失函数并传入权重
        criterion = nn.CrossEntropyLoss(weight=class_weights)
    """
    if isWeight:
        class_weights = torch.tensor([0.5, 1.0, 1.0, 1.0, 1.0, 1.5, 1.0, 1.0, 1.9, 1.0, 1.5]).float()
        criterion = nn.CrossEntropyLoss(weight=class_weights.to(device))
    else:
        criterion = nn.CrossEntropyLoss()

    # 用于保存最佳模型
    best_val_loss = float('inf')  # 初始化最佳验证损失为正无穷大
    best_val_accuracy = 0.0  # 初始化最佳准确率为0

    # 记录每个epoch的损失和准确率
    train_losses = []
    train_accuracies = []
    val_losses = []
    val_accuracies = []

    # 循环训练
    for epoch in range(EPOCHS):
        model.train(True)

        # 初始化统计量
        train_loss_epoch = 0.0
        train_accuracy_epoch = 0.0
        total_train_samples = 0
        train_class_correct = [0] * num_classes  # 每个类别的正确预测数
        train_class_total = [0] * num_classes  # 每个类别的总样本数

        # 训练阶段
        for input_x, label in tqdm(train_dataloader, desc=f"Training Epoch {epoch + 1}/{EPOCHS}", unit="batch"):
            optim.zero_grad()
            input_x = input_x.to(device)
            label = label.to(device)
            pre_y = model(input_x)

            loss = criterion(pre_y, label.long())
            pred = torch.argmax(pre_y, dim=1)
            accuracy = torch.sum(pred == label) / label.shape[0]

            # 累计整体指标
            train_loss_epoch += loss.item() * input_x.size(0)
            train_accuracy_epoch += accuracy.item() * input_x.size(0)
            total_train_samples += input_x.size(0)

            # 统计每个类别的正确预测
            for c in range(num_classes):
                train_class_correct[c] += ((pred == label) & (label == c)).sum().item()
                train_class_total[c] += (label == c).sum().item()

            loss.backward()
            optim.step()
            # scheduler.step()
            current_lr = optim.param_groups[0]['lr']

        # 计算并打印训练结果
        avg_train_loss = train_loss_epoch / total_train_samples
        avg_train_accuracy = train_accuracy_epoch / total_train_samples
        train_losses.append(avg_train_loss)
        train_accuracies.append(avg_train_accuracy)

        print(f"\nTrain [{epoch + 1}/{EPOCHS}] Loss: {avg_train_loss:.4f}, Acc: {avg_train_accuracy:.2%}")
        print("Class-wise Train Accuracy:")
        for c in range(num_classes):
            if train_class_total[c] > 0:
                print(
                    f"  Class {c}: {train_class_correct[c] / train_class_total[c]:.2%} ({train_class_correct[c]}/{train_class_total[c]})")
            else:
                print(f"  Class {c}: No samples")

        # 验证阶段
        model.eval()
        val_loss_epoch = 0.0
        val_accuracy_epoch = 0.0
        total_val_samples = 0
        val_class_correct = [0] * num_classes
        val_class_total = [0] * num_classes

        with torch.no_grad():
            for input_x, label in tqdm(val_dataloader, desc=f"Validating Epoch {epoch + 1}/{EPOCHS}", unit="batch"):
                input_x = input_x.to(device)
                label = label.to(device)
                pre_y = model(input_x)

                loss = criterion(pre_y, label.long())
                pred = torch.argmax(pre_y, dim=1)
                accuracy = torch.sum(pred == label) / label.shape[0]

                # 累计整体指标
                val_loss_epoch += loss.item() * input_x.size(0)
                val_accuracy_epoch += accuracy.item() * input_x.size(0)
                total_val_samples += input_x.size(0)

                # 统计每个类别的正确预测
                for c in range(num_classes):
                    val_class_correct[c] += ((pred == label) & (label == c)).sum().item()
                    val_class_total[c] += (label == c).sum().item()

        # 计算并打印验证结果
        avg_val_loss = val_loss_epoch / (total_val_samples + 1e-8)
        avg_val_accuracy = val_accuracy_epoch / (total_val_samples + 1e-8)
        val_losses.append(avg_val_loss)
        val_accuracies.append(avg_val_accuracy)

        print(f"\nVal [{epoch + 1}/{EPOCHS}] Loss: {avg_val_loss:.4f}, Acc: {avg_val_accuracy:.2%}")
        print("Class-wise Val Accuracy:")
        for c in range(num_classes):
            if val_class_total[c] > 0:
                print(
                    f"  Class {c}: {val_class_correct[c] / val_class_total[c]:.2%} ({val_class_correct[c]}/{val_class_total[c]})")
            else:
                print(f"  Class {c}: No samples")

        # 保存最佳模型（修正逻辑：当准确率更高时保存）
        if avg_val_accuracy > best_val_accuracy:  # 改为大于号
            best_val_accuracy = avg_val_accuracy
            best_val_loss = avg_val_loss
            checkpoint = {
                'model_state_dict': model.state_dict(),
                'current_lr': current_lr,
                'best_val_accuracy': best_val_accuracy,
                'best_val_loss': best_val_loss
            }
            torch.save(checkpoint, "best.pt")
            print(f"\n✨ Saving Best Model (Acc: {best_val_accuracy:.2%}, Loss: {best_val_loss:.4f})")

    # 绘制损失曲线和准确率曲线
    # 训练和验证损失曲线
    plt.figure(figsize=(12, 6))

    # 绘制训练和验证损失
    plt.subplot(1, 2, 1)
    plt.plot(range(1, EPOCHS + 1), train_losses, label="Train Loss")
    plt.plot(range(1, EPOCHS + 1), val_losses, label="Validation Loss")
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.title('Train and Validation Loss')
    plt.legend()

    # 绘制训练和验证准确率
    plt.subplot(1, 2, 2)
    plt.plot(range(1, EPOCHS + 1), train_accuracies, label="Train Accuracy")
    plt.plot(range(1, EPOCHS + 1), val_accuracies, label="Validation Accuracy")
    plt.xlabel('Epoch')
    plt.ylabel('Accuracy')
    plt.title('Train and Validation Accuracy')
    plt.legend()

    # 保存图像
    plt.tight_layout()
    plt.savefig('training_validation_curve_fake.png')
    # plt.show()
