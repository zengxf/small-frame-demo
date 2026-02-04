"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=1, H=2, W=2
    Conv2d 层：1 in, 2 out, 1x1 kernel
"""

import torch
import torch.nn as nn

# 定义 Conv2d 层：1 输入通道，2 输出通道，1x1 卷积核
conv = nn.Conv2d(in_channels=1, out_channels=2, kernel_size=1, bias=False)

# 手动设置权重以便清晰观察输出
# 权重 shape: (out_channels, in_channels, kH, kW) = (2, 1, 1, 1)
with torch.no_grad():
    conv.weight[0, 0, 0, 0] = 1.0  # 第一个输出通道的权重
    conv.weight[1, 0, 0, 0] = -1.0  # 第二个输出通道的权重

# 构造输入张量: shape (1, 1, 2, 2)
x = torch.tensor([[[[1.0, 2.0],
                    [3.0, 4.0]]]])

print("输入 x (shape: {}):\n".format(x.shape), x.squeeze())
print("\n卷积核权重 (shape: {}):".format(conv.weight.shape))
print("  通道0权重:", conv.weight[0].item())  # 1.0
print("  通道1权重:", conv.weight[1].item())  # -1.0

# 前向传播
output = conv(x)
print("\n输出 shape:", output.shape)  # 应为 (1, 2, 2, 2)
print("输出:\n", output.squeeze())  # squeeze 后为 (2, 2, 2)
