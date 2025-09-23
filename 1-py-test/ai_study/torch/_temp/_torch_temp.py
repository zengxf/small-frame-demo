import torch

x = torch.tensor([[1.0]], requires_grad=True)  # 模型输出
y = torch.tensor([[2.0]])  # 真实标签（常量，不需要梯度）

# 自定义损失函数
loss = (x - y).pow(2) + torch.sin(x)

loss.backward()

print("x.grad:", x.grad)
# x.grad: tensor([[-1.4597]])