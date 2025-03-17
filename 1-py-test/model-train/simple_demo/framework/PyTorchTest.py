import torch

# 创建张量
print("\n------------------------------- 1")
# 创建全零张量（3行4列的矩阵）
x = torch.zeros(3, 4)
print(x)
print("------------------------------- ")
# 创建随机张量（2行3列的矩阵）
rand_tensor = torch.rand(2, 3)
print(rand_tensor)
print("------------------------------- ")
# 从列表创建张量
a = torch.tensor([1, 2, 3])
b = torch.tensor([4, 5, 6])
print(a + b)  # 输出：tensor([5, 7, 9])

# 自动微分
print("\n------------------------------- 2")
x = torch.tensor([2.0], requires_grad=True)
print(x)
print("------------------------------- ")
y = x * x + 3
print(y)
print("------------------------------- ")
y.backward()  # 反向传播计算梯度
print(y)
print("------------------------------- ")
print(x.grad)  # 输出：tensor([4.0])

# 定义简单全连接网络
print("\n------------------------------- 3")


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
in_data = torch.randn(1, 28 * 28)
print(in_data)
print("------------------------------- ")
out_data = model.forward(in_data)
print(out_data)

# 设备管理
print("\n------------------------------- 4")
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
print(device)
