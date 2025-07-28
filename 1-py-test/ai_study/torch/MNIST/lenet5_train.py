import torch
import torch.nn as nn
import torch.optim as optim
import torchvision
import torchvision.transforms as T
import matplotlib.pyplot as plt
import os


# -----------------------------
# 1. 网络定义：BackBone + Head
# -----------------------------
class BackBone(nn.Module):
    """卷积部分：C1 + S2 + C3 + S4"""

    def __init__(self):
        super().__init__()
        self.features = nn.Sequential(
            nn.Conv2d(1, 6, kernel_size=5, padding=2),  # C1
            nn.ReLU(inplace=True),
            nn.MaxPool2d(kernel_size=2, stride=2),  # S2
            nn.Conv2d(6, 16, kernel_size=5),  # C3
            nn.ReLU(inplace=True),
            nn.MaxPool2d(kernel_size=2, stride=2)  # S4
        )

    def forward(self, x):
        return self.features(x)


class Head(nn.Module):
    """全连接部分：C5 + F6 + OUTPUT"""

    def __init__(self, num_classes=10):
        super().__init__()
        self.classifier = nn.Sequential(
            nn.Conv2d(16, 120, kernel_size=5),  # C5 (5×5→1×1)
            nn.Tanh(),
            nn.Flatten(),  # 展开
            nn.Linear(120, 84),  # F6
            nn.Tanh(),
            nn.Linear(84, num_classes)  # OUTPUT
        )

    def forward(self, x):
        return self.classifier(x)


class LeNet5(nn.Module):
    """完整网络：BackBone + Head"""

    def __init__(self, num_classes=10):
        super().__init__()
        self.backbone = BackBone()
        self.head = Head(num_classes)

    def forward(self, x):
        x = self.backbone(x)
        x = self.head(x)
        return x


# -----------------------------
# 2. 工具：评估函数
# -----------------------------
@torch.no_grad()
def evaluate(model, loader, device):
    model.eval()
    correct = 0
    total = 0
    for imgs, labels in loader:
        imgs, labels = imgs.to(device), labels.to(device)
        outputs = model(imgs)
        preds = outputs.argmax(1)
        correct += (preds == labels).sum().item()
        total += labels.size(0)
    return correct / total


# -----------------------------
# 3. 训练主流程
# -----------------------------
def main():
    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
    epochs = 10
    batch_size = 64
    lr = 1e-3

    # 数据
    transform = T.Compose([
        T.ToTensor(),
        T.Normalize((0.1307,), (0.3081,))
    ])
    train_set = torchvision.datasets.MNIST(root='./data', train=True,
                                           download=True, transform=transform)
    test_set = torchvision.datasets.MNIST(root='./data', train=False,
                                          download=True, transform=transform)
    train_loader = torch.utils.data.DataLoader(train_set, batch_size=batch_size,
                                               shuffle=True, num_workers=2)
    test_loader = torch.utils.data.DataLoader(test_set, batch_size=batch_size,
                                              shuffle=False, num_workers=2)

    # 模型
    model = LeNet5().to(device)
    criterion = nn.CrossEntropyLoss()
    optimizer = optim.Adam(model.parameters(), lr=lr)

    # 记录
    train_loss_history = []
    train_acc_history = []
    test_acc_history = []
    best_acc = 0.0
    os.makedirs('weights', exist_ok=True)

    # 训练
    for epoch in range(epochs):
        model.train()
        running_loss = 0.0
        correct = 0
        total = 0
        for imgs, labels in train_loader:
            imgs, labels = imgs.to(device), labels.to(device)

            optimizer.zero_grad()
            outputs = model(imgs)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            running_loss += loss.item() * imgs.size(0)
            preds = outputs.argmax(1)
            correct += (preds == labels).sum().item()
            total += labels.size(0)

        # 记录
        train_loss = running_loss / total
        train_acc = correct / total
        test_acc = evaluate(model, test_loader, device)

        train_loss_history.append(train_loss)
        train_acc_history.append(train_acc)
        test_acc_history.append(test_acc)

        print(f'Epoch {epoch + 1:02d}/{epochs} | '
              f'Loss: {train_loss:.4f} | '
              f'Train Acc: {train_acc:.4f} | '
              f'Test Acc: {test_acc:.4f}')

        # 保存权重
        torch.save(model.state_dict(), 'weights/last.pth')
        if test_acc > best_acc:
            best_acc = test_acc
            torch.save(model.state_dict(), 'weights/best.pth')

    # -----------------------------
    # 4. 绘图
    # -----------------------------
    plt.figure(figsize=(12, 4))
    plt.subplot(1, 2, 1)
    plt.title('Loss Curve')
    plt.plot(train_loss_history, label='train')
    plt.xlabel('epoch')
    plt.ylabel('loss')
    plt.legend()

    plt.subplot(1, 2, 2)
    plt.title('Accuracy Curve')
    plt.plot(train_acc_history, label='train')
    plt.plot(test_acc_history, label='test')
    plt.xlabel('epoch')
    plt.ylabel('accuracy')
    plt.legend()
    plt.tight_layout()
    plt.savefig('lenet5_curve.png')
    plt.show()


if __name__ == '__main__':
    main()

    # # 继续训练、加载权重
    # model = LeNet5()
    # model.load_state_dict(torch.load('weights/best.pth'))
