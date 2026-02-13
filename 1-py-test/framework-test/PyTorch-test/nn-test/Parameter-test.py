"""
nn.Parameter 是一个特殊的张量（Tensor），它被包装后会自动成为一个模型的可学习参数。

如果有一些需要通过训练来学习的权重或偏置，就不能只是普通 Tensor，而要用 nn.Parameter 包装。
"""

import torch
import torch.nn as nn


class ScaleLayer(nn.Module):
    def __init__(self, init_value):
        super().__init__()
        # 使用 nn.Parameter 将一个标量注册为可学习参数
        self.scale = nn.Parameter(torch.tensor(init_value))  # 默认 requires_grad=True
        # self.scale = torch.tensor(init_value)  # 错误：只是一个普通 Tensor，不会被优化！

    def forward(self, x):
        return x * self.scale


# 创建模型实例
model = ScaleLayer(init_value=2.0)

# 查看参数
print("模型参数:")
for name, param in model.named_parameters():
    print(f"  {name}: {param.data}")

# 验证是否在 parameters() 中
print("\nmodel.parameters() 是否包含 scale?")
print(list(model.parameters()))  # 应该包含 tensor(2., requires_grad=True)

# 前向传播示例
x = torch.tensor([1.0, 2.0, 3.0])
output = model(x)
print("\n输入:", x)
print("输出:", output)  # 应该是 [2., 4., 6.]

# 如果使用优化器，scale 会被更新
optimizer = torch.optim.SGD(model.parameters(), lr=0.1)
loss = output.sum()
loss.backward()
optimizer.step()

print("\n更新后的 scale 参数:", model.scale.data)
