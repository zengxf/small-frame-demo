# 调的库名
import torch.nn as nn
import torch

"""
变成一个算子库。模型结构库

"""


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


class Vgg16(nn.Module):
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

        self.conv5_1 = Conv(512, 512, 3, 1, 1)
        self.conv5_2 = Conv(512, 512, 3, 1, 1)
        self.conv5_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool5 = MaxPool(2, 2)  # 这里的输出

        self.fc1 = nn.Linear(7 * 7 * 512, 4096)
        self.fc2 = nn.Linear(4096, 4096)
        self.fc3 = nn.Linear(4096, self.class_num)

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
        x = self.max_pool4(x)

        x = self.conv5_1(x)
        x = self.conv5_2(x)
        x = self.conv5_3(x)
        x = self.max_pool5(x)

        x = self.flat(x)
        x = self.fc1(x)
        x = self.relu(x)

        x = self.fc2(x)
        x = self.relu(x)

        out = self.fc3(x)
        print(out.shape)
        return out


class Vgg16_Add(nn.Module):
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

        self.conv5_1 = Conv(512, 512, 3, 1, 1)
        self.conv5_2 = Conv(512, 512, 3, 1, 1)
        self.conv5_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool5 = MaxPool(2, 2)  # 这里的输出 7x7x512

        self.conv6_1 = Conv(512, 512, 3, 1, 1)
        self.conv6_2 = Conv(512, 512, 3, 1, 1)
        self.conv6_3 = Conv(512, 512, 3, 1, 1)

        self.fc1 = nn.Linear(7 * 7 * 512, 4096)
        self.fc2 = nn.Linear(4096, 4096)
        self.fc3 = nn.Linear(4096, self.class_num)

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
        x = self.max_pool4(x)

        x = self.conv5_1(x)
        x = self.conv5_2(x)
        x = self.conv5_3(x)
        x = self.max_pool5(x)  # 512x7x7

        x = self.conv6_1(x)  # 加一层有效，加3层有效，要靠试验。感觉欠拟合
        x = self.conv6_2(x)
        x = self.conv6_3(x)

        x = self.flat(x)
        x = self.fc1(x)
        x = self.relu(x)

        x = self.fc2(x)
        x = self.relu(x)

        out = self.fc3(x)
        return out


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

        self.fc1 = nn.Linear(14 * 14 * 256, 4096)
        self.fc2 = nn.Linear(4096, 4096)
        self.fc3 = nn.Linear(4096, self.class_num)

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


class Vgg16NOTFC(nn.Module):
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

        self.conv5_1 = Conv(512, 512, 3, 1, 1)
        self.conv5_2 = Conv(512, 512, 3, 1, 1)
        self.conv5_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool5 = MaxPool(2, 2)  # 这里的输出

        self.global_pool = nn.AdaptiveAvgPool2d(1) # 一般说来就在7x7左右
        self.global_conv1 = Conv(512, 256, 1, 1)
        self.global_conv2 = Conv(256, 128, 1, 1)
        # 最后一层的输出，是否需要relu
        # self.out_conv3 = Conv(128, self.class_num, 1, 1, is_act=False)
        self.out_conv3 = nn.Conv2d(128, self.class_num, 1, 1)

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
        x = self.max_pool4(x)

        x = self.conv5_1(x)
        x = self.conv5_2(x)
        x = self.conv5_3(x)
        x = self.max_pool5(x)

        x = self.global_pool(x)
        x = self.global_conv1(x)

        x = self.global_conv2(x)
        out = self.out_conv3(x)  # 1x128x1x1 , 1, 10
        out = torch.reshape(out, (out.shape[0], -1))  # torch.xx代表一个数学运算

        return out


if __name__ == "__main__":
    from torchvision import transforms
    import torchvision
    from tqdm import tqdm
    import matplotlib.pyplot as plt

    device = "cuda:0" if torch.cuda.is_available() else "cpu"
    """
    项目的难点
    我们的数据情况是怎样的
    数据优化方法是怎样的。
    模型的优化方向是怎样的。
    
    
    """
    model = Vgg16NOTFC(class_num=5).to(device)
    batchSize = 30
    """
    如果训练的时候减去均值和方差，那么推理的时候也需要进行
    """
    transforms = transforms.Compose(
        [
            transforms.Resize([224, 224]),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),

        ]
    )
    # 配置dataset
    train_dataset = torchvision.datasets.ImageFolder(
        root=r"D:/DLAI/dataSet/flower_photos_data/train",
        transform=transforms
    )
    test_dataset = torchvision.datasets.ImageFolder(
        root=r"D:/DLAI/dataSet/flower_photos_data/val",
        transform=transforms
    )
    # 配置dataloader

    train_loader = torch.utils.data.DataLoader(
        train_dataset,
        batch_size=batchSize,
        shuffle=True
    )
    # 初始化 DataLoader 和其他内容
    # 初始化 DataLoader 和其他内容
    val_loader = torch.utils.data.DataLoader(
        test_dataset,
        batch_size=batchSize,
        shuffle=False
    )

    # VGG的学习率
    optim = torch.optim.Adam(model.parameters(), lr=1e-5)

    # 设置损失函数
    criterion = nn.CrossEntropyLoss()

    EPOCHS = 10  # 设置epoch次数

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

        # 初始化每个epoch的累计损失和准确率
        train_loss_epoch = 0.0
        train_accuracy_epoch = 0.0
        total_train_samples = 0

        # 训练数据循环，添加 tqdm 进度条
        for input_x, label in tqdm(train_loader, desc=f"Training Epoch {epoch + 1}/{EPOCHS}", unit="batch"):
            optim.zero_grad()
            input_x = input_x.to(device)
            label = label.to(device)
            pre_y = model(input_x)  # 前向传播，得到预测值

            # 计算损失
            loss = criterion(pre_y, label)
            pred = torch.argmax(pre_y, dim=1)
            accuracy = torch.sum(pred == label) / label.shape[0]

            # 累加损失和准确率
            train_loss_epoch += loss.item() * input_x.size(0)  # 累计损失
            train_accuracy_epoch += accuracy.item() * input_x.size(0)  # 累计准确率
            total_train_samples += input_x.size(0)  # 累计样本数

            loss.backward()  # 反向传播
            optim.step()  # 优化器更新权重

        # 计算每个epoch的平均训练损失和准确率
        avg_train_loss = train_loss_epoch / total_train_samples
        avg_train_accuracy = train_accuracy_epoch / total_train_samples

        # 保存训练损失和准确率
        train_losses.append(avg_train_loss)
        train_accuracies.append(avg_train_accuracy)

        print(f"train.[{epoch + 1}/{EPOCHS}] Avg Loss: {avg_train_loss:.4f}, Avg Acc: {avg_train_accuracy:.2f}")

        model.eval()  # 进入评估模式

        # 初始化每个epoch的验证损失和准确率
        val_loss_epoch = 0.0
        val_accuracy_epoch = 0.0
        total_val_samples = 0

        # 验证数据循环，添加 tqdm 进度条
        with torch.no_grad():  # 不计算梯度
            for input_x, label in tqdm(val_loader, desc=f"Validating Epoch {epoch + 1}/{EPOCHS}", unit="batch"):
                input_x = input_x.to(device)
                label = label.to(device)
                pre_y = model(input_x)  # 前向传播

                # 计算损失
                loss = criterion(pre_y, label)
                pred = torch.argmax(pre_y, dim=1)
                accuracy = torch.sum(pred == label) / label.shape[0]

                # 累加验证损失和准确率
                val_loss_epoch += loss.item() * input_x.size(0)  # 累计损失
                val_accuracy_epoch += accuracy.item() * input_x.size(0)  # 累计准确率
                total_val_samples += input_x.size(0)  # 累计样本数

        # 计算每个epoch的平均验证损失和准确率
        avg_val_loss = val_loss_epoch / total_val_samples
        avg_val_accuracy = val_accuracy_epoch / total_val_samples

        # 保存验证损失和准确率
        val_losses.append(avg_val_loss)
        val_accuracies.append(avg_val_accuracy)

        print(f"val.[{epoch + 1}/{EPOCHS}] Avg Loss: {avg_val_loss:.4f}, Avg Acc: {avg_val_accuracy:.2f}")

        # 保存最佳模型（如果当前验证损失更小）
        if avg_val_accuracy < best_val_accuracy:  # 如果当前验证损失更小
            best_val_accuracy = avg_val_accuracy
            torch.save(model.state_dict(), "best.pt")
            print(f"Saving Best Model at Epoch {epoch + 1} with Val acc: {avg_val_loss:.4f}")

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