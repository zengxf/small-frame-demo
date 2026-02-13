"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=2, H=3, W=3
    Conv2d 层：2 in, 2 out, 2 group, 2x2 kernel
"""

import torch
import torch.nn as nn

# 硬编码输入
x = torch.tensor([[
    [[1, 2, 3],
     [4, 5, 6],
     [7, 8, 9]],
    [[10, 11, 12],
     [13, 14, 15],
     [16, 17, 18]]
]], dtype=torch.float32)  # shape: (1, 2, 3, 3)

# 创建 Conv2d 层
conv = nn.Conv2d(in_channels=2, out_channels=2, kernel_size=2, groups=2, bias=False)

# 硬编码权重
weight_data = torch.tensor([
    [[[1, 0],
      [0, 1]]],  # group 0
    [[[0, 1],
      [1, 0]]]  # group 1
], dtype=torch.float32)  # shape: (2, 1, 2, 2)

conv.weight.data = weight_data

# 前向计算
output = conv(x)

print("Output shape:", output.shape)
print("Output:\n", output.squeeze(0))  # squeeze batch dim
