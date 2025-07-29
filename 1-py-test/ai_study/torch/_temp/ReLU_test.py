import torch.nn as nn
import torch

"""
nn.ReLU(inplace=True) 中的 inplace=True 表示 “原地操作”，意思是 ReLU 激活函数会直接修改输入张量的数据，而不创建新的输出张量。
优点：节省内存，可能略微提升运行速度。
缺点：原始输入数据会被破坏，后续无法再访问。
"""

x = torch.tensor([-1, 0, 1], dtype=torch.float32)

# 1. inplace=True（原始x会被修改）
relu_inplace = nn.ReLU(inplace=True)
output = relu_inplace(x)  # x变为[0, 0, 1]，output和x指向同一块内存

print('x:', x)
print('output:', output)
# x: tensor([0., 0., 1.])
# output: tensor([0., 0., 1.])

# 2. inplace=False（默认，x不变）
x = torch.tensor([-1, 0, 1], dtype=torch.float32)
relu_no_inplace = nn.ReLU(inplace=False)
output = relu_no_inplace(x)  # x仍是[-1, 0, 1]，output是新张量[0, 0, 1]

print('x:', x)
print('output:', output)
# x: tensor([-1.,  0.,  1.])
# output: tensor([0., 0., 1.])
