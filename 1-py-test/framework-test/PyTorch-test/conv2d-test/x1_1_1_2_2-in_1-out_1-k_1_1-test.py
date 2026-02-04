"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=1, H=2, W=2
    Conv2d 层：1 in, 1 out, 1x1 kernel
"""

import torch
import torch.nn as nn

# 定义 Conv2d 层：1 in, 1 out, 1x1 kernel
conv = nn.Conv2d(in_channels=1, out_channels=1, kernel_size=1, bias=False)

# 手动设置卷积核权重（便于观察结果）
# 权重 shape: (out_channels, in_channels, kH, kW) = (1, 1, 1, 1)
with torch.no_grad():
    conv.weight[0, 0, 0, 0] = 2.0  # 将卷积核设为 [[2.0]]

# 构造输入：batch=1, channel=1, H=2, W=2
x = torch.tensor([[[[1.0, 2.0],
                    [3.0, 4.0]]]])  # shape: (1, 1, 2, 2)

print("输入 x shape:", x.shape)
print("输入 x:\n", x)
print("输入 x.squeeze():\n", x.squeeze())  # squeeze 后显示 (2,2)

print("\n卷积核 weight:", conv.weight)  # 应为 2.0
print("\n卷积核 weight.squeeze():", conv.weight.squeeze().item())  # 应为 2.0

# 前向传播
output = conv(x)
print("\n输出 shape:", output.shape)  # (1, 1, 2, 2)
print("输出:\n", output.squeeze())  # 应为 [[2, 4], [6, 8]]
