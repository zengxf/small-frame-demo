import torch
import torch.nn as nn
import torch.optim as optim
from torchvision import datasets, transforms

# 1. 超参数
BATCH_SIZE = 64
EPOCHS = 3
LR = 1e-3

# 2. 数据
transform = transforms.Compose([transforms.ToTensor()])
train_ds = datasets.MNIST(root='.', train=True, download=True, transform=transform)
test_ds = datasets.MNIST(root='.', train=False, download=True, transform=transform)
train_loader = torch.utils.data.DataLoader(train_ds, batch_size=BATCH_SIZE, shuffle=True)
test_loader = torch.utils.data.DataLoader(test_ds, batch_size=BATCH_SIZE)


# 3. 网络（3 层全连接）
class Net(nn.Module):
    def __init__(self):
        super().__init__()
        self.fc = nn.Sequential(
            nn.Flatten(),  # 28*28 -> 784
            nn.Linear(784, 128),
            nn.ReLU(),
            nn.Linear(128, 64),
            nn.ReLU(),
            nn.Linear(64, 10)  # 10 类
        )

    def forward(self, x):
        return self.fc(x)


model = Net()
print(model)

# 4. 损失与优化器
criterion = nn.CrossEntropyLoss()
optimizer = optim.Adam(model.parameters(), lr=LR)

# 5. 训练
for epoch in range(EPOCHS):
    for x, y in train_loader:
        pred = model(x)
        loss = criterion(pred, y)

        optimizer.zero_grad()
        loss.backward()
        optimizer.step()
    print('epoch', epoch + 1, 'loss', loss.item())

# 6. 测试
correct = total = 0
with torch.no_grad():
    for x, y in test_loader:
        pred = model(x).argmax(1)
        correct += (pred == y).sum().item()
        total += y.size(0)
print('test acc:', correct / total)
