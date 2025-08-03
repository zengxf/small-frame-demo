import torch
import torchvision
from tqdm import tqdm
import cv2
# 供dataset和dataloader使用的
from torch.utils.data.dataset import Dataset
from torch.utils.data import DataLoader
import os
import random
import torch.nn as nn
from torchvision import transforms
import torchvision
from tqdm import tqdm
import matplotlib.pyplot as plt
import numpy as np


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
    def __init__(self, path, device, is_train=True):
        """用来做一些初始化"""
        self.all_imgs = os.listdir(path)
        random.shuffle(self.all_imgs)
        cout_all = len(self.all_imgs)
        train_num = int(cout_all * 0.8)
        if is_train:
            self.all_imgs = self.all_imgs[0:train_num]
        else:
            self.all_imgs = self.all_imgs[train_num:cout_all]
        self.root = path
        self.device = device

    def _get_label_from_image_name(self, file_name):
        label = int(file_name.split("_")[0])
        label = int(label)
        label = age_to_group(label)

        return torch.tensor(label, dtype=torch.float32).to(self.device)

    def _get_image_bgr(self, file_name):
        image = cv2.imread(f"{self.root}\\{file_name}")
        gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
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


if __name__ == "__main__":
    path = r"F:/data_source/age_face/crop_part1"
    device = "cuda:0" if torch.cuda.is_available() else "cpu"
    train_dataset = MyDataSet(path, device)
    batch_size = 32
    train_dataloader = torch.utils.data.DataLoader(
        dataset=train_dataset,
        batch_size=batch_size,
        shuffle=True
    )

    val_dataset = MyDataSet(path, device, is_train=False)
    val_dataloader = torch.utils.data.DataLoader(
        dataset=val_dataset,
        batch_size=batch_size,
        shuffle=False
    )

    EPOCHS = 20
    num_classes = 11  # 根据你的年龄分组类别数调整
    model = Vgg16_Sub(class_num=num_classes).to(device)
    # VGG的学习率
    optim = torch.optim.Adam(model.parameters(), lr=1e-4)

    # 设置损失函数
    criterion = nn.CrossEntropyLoss()
    # 初始化记录变量
    best_val_loss = float('inf')
    best_val_accuracy = 0.0
    train_losses = []
    train_accuracies = []
    val_losses = []
    val_accuracies = []


    # 自定义损失函数：计算每个类别的损失后求和
    def class_weighted_loss(preds, targets, num_classes):
        total_loss = 0.0
        for c in range(num_classes):
            class_mask = (targets == c)
            if class_mask.any():
                class_loss = criterion(preds[class_mask], targets[class_mask])
                total_loss += class_loss
        return total_loss


    for epoch in range(EPOCHS):
        model.train(True)

        # 训练阶段
        train_loss_epoch = 0.0
        train_accuracy_epoch = 0.0
        total_train_samples = 0
        train_class_correct = [0] * num_classes
        train_class_total = [0] * num_classes
        train_class_loss = [0.0] * num_classes  # 新增：每个类别的损失

        for input_x, label in tqdm(train_dataloader, desc=f"Train Epoch {epoch + 1}/{EPOCHS}"):
            optim.zero_grad()
            input_x, label = input_x.to(device), label.to(device)
            pre_y = model(input_x)

            # 计算每个类别的损失并求和
            loss = class_weighted_loss(pre_y, label, num_classes)

            pred = torch.argmax(pre_y, dim=1)
            accuracy = (pred == label).float().mean()

            # 累计统计
            train_loss_epoch += loss.item() * input_x.size(0)
            train_accuracy_epoch += accuracy.item() * input_x.size(0)
            total_train_samples += input_x.size(0)

            # 统计每个类别的准确率和损失
            with torch.no_grad():
                for c in range(num_classes):
                    class_mask = (label == c)
                    if class_mask.any():
                        train_class_correct[c] += (pred[class_mask] == label[class_mask]).sum().item()
                        train_class_total[c] += class_mask.sum().item()
                        train_class_loss[c] += criterion(pre_y[class_mask],
                                                         label[class_mask]).item() * class_mask.sum().item()

        # 计算平均指标
        avg_train_loss = train_loss_epoch / total_train_samples
        avg_train_accuracy = train_accuracy_epoch / total_train_samples

        # 打印训练结果
        print(f"\nTrain Epoch {epoch + 1}")
        print(f"Overall - Loss: {avg_train_loss:.4f}, Acc: {avg_train_accuracy:.2%}")
        print("Class-wise Performance:")
        for c in range(num_classes):
            if train_class_total[c] > 0:
                acc = train_class_correct[c] / train_class_total[c]
                loss = train_class_loss[c] / train_class_total[c]
                print(f"  Class {c}: Loss={loss:.4f}, Acc={acc:.2%} ({train_class_correct[c]}/{train_class_total[c]})")
            else:
                print(f"  Class {c}: No samples")

        # 验证阶段（结构与训练阶段类似）
        model.eval()
        val_loss_epoch = 0.0
        val_accuracy_epoch = 0.0
        total_val_samples = 0
        val_class_correct = [0] * num_classes
        val_class_total = [0] * num_classes
        val_class_loss = [0.0] * num_classes

        with torch.no_grad():
            for input_x, label in tqdm(val_dataloader, desc=f"Val Epoch {epoch + 1}/{EPOCHS}"):
                input_x, label = input_x.to(device), label.to(device)
                pre_y = model(input_x)

                loss = class_weighted_loss(pre_y, label, num_classes)
                pred = torch.argmax(pre_y, dim=1)

                # 累计统计
                val_loss_epoch += loss.item() * input_x.size(0)
                val_accuracy_epoch += (pred == label).float().mean().item() * input_x.size(0)
                total_val_samples += input_x.size(0)

                # 类别统计
                for c in range(num_classes):
                    class_mask = (label == c)
                    if class_mask.any():
                        val_class_correct[c] += (pred[class_mask] == label[class_mask]).sum().item()
                        val_class_total[c] += class_mask.sum().item()
                        val_class_loss[c] += criterion(pre_y[class_mask],
                                                       label[class_mask]).item() * class_mask.sum().item()

        # 计算验证指标
        avg_val_loss = val_loss_epoch / total_val_samples
        avg_val_accuracy = val_accuracy_epoch / total_val_samples

        # 打印验证结果
        print(f"\nValidation Epoch {epoch + 1}")
        print(f"Overall - Loss: {avg_val_loss:.4f}, Acc: {avg_val_accuracy:.2%}")
        print("Class-wise Performance:")
        for c in range(num_classes):
            if val_class_total[c] > 0:
                acc = val_class_correct[c] / val_class_total[c]
                loss = val_class_loss[c] / val_class_total[c]
                print(f"  Class {c}: Loss={loss:.4f}, Acc={acc:.2%} ({val_class_correct[c]}/{val_class_total[c]})")
            else:
                print(f"  Class {c}: No samples")

        # 保存最佳模型（根据验证准确率）
        if avg_val_accuracy > best_val_accuracy:
            best_val_accuracy = avg_val_accuracy
            best_val_loss = avg_val_loss
            torch.save(model.state_dict(), "best_model.pt")
            print(f"🔥 Saved Best Model (Acc: {best_val_accuracy:.2%}, Loss: {best_val_loss:.4f})")
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
    plt.show()
