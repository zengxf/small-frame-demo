import torch

x = torch.tensor([2.0], requires_grad=True)
w = torch.tensor([3.0], requires_grad=True)
b = torch.tensor([1.0], requires_grad=True)

# 执行操作
y = w * x  # 乘法操作
z = y + b  # 加法操作

print(w.grad_fn)       # def: None
print(x.requires_grad) # True
print(y.requires_grad) # True (因为它的输入 requires_grad=True)
print(y.grad_fn)       # <MulBackward0 object at ...> (记录了乘法操作)
print(z.grad_fn)       # <AddBackward0 object at ...> (记录了加法操作)

# 假设 z 是我们的最终输出（例如损失函数）
z.backward() # 触发反向传播

# 查看梯度
print(w.grad) # tensor([2.]) (dz/dw = x = 2.0)
print(x.grad) # tensor([3.]) (dz/dx = w = 3.0)
print(b.grad) # tensor([1.]) (dz/db = 1)