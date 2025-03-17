import torch

# 数据加载与预处理
from torchvision import datasets, transforms

# 数据标准化转换
transform = transforms.Compose([
    transforms.ToTensor(),
    transforms.Normalize((0.5,), (0.5,))  # 像素值映射到[-1,1]范围[4](@ref)
])
# 加载数据集
train_data = datasets.MNIST(root='./data', train=True, download=True, transform=transform)
train_loader = torch.utils.data.DataLoader(train_data, batch_size=64, shuffle=True)


# ------------------------------------
# 定义简单全连接网络
class SimpleNN(torch.nn.Module):
    def __init__(self):
        super().__init__()
        self.fc1 = torch.nn.Linear(28 * 28, 128)  # 输入层到隐藏层
        self.fc2 = torch.nn.Linear(128, 10)  # 隐藏层到输出层（10个类别）

    def forward(self, x):
        x = x.view(-1, 28 * 28)  # 展平图像[2](@ref)
        x = torch.relu(self.fc1(x))
        return self.fc2(x)


model = SimpleNN()

# ------------------------------------
# 训练流程
criterion = torch.nn.CrossEntropyLoss()
optimizer = torch.optim.Adam(model.parameters(), lr=0.001)

for epoch in range(5):
    for inputs, labels in train_loader:
        optimizer.zero_grad()
        outputs = model(inputs)
        loss = criterion(outputs, labels)
        loss.backward()  # 反向传播
        optimizer.step()  # 参数更新
    print(f'Epoch {epoch + 1}, Loss: {loss.item():.4f}')

# ------------------------------------
# GPU加速
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
model = model.to(device)  # 将模型移至GPU
inputs = inputs.to(device)  # 数据也需转移至相同设备

# ------------------------------------
# 模型保存与加载
# 保存模型权重
torch.save(model.state_dict(), 'mnist_model.pth')
# 加载模型
loaded_model = SimpleNN()
loaded_model.load_state_dict(torch.load('mnist_model.pth'))
