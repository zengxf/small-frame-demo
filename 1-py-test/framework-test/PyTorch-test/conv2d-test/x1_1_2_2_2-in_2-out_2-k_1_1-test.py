"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=2, H=2, W=2
    Conv2d 层：2 in, 2 out, 1x1 kernel
"""

import torch
import torch.nn as nn

# 定义 Conv2d 层：2 in, 2 out, 1x1 kernel（无偏置便于观察）
conv = nn.Conv2d(in_channels=2, out_channels=2, kernel_size=1, bias=False)

# 手动设置权重以便清晰验证输出
# 权重 shape: (out_channels, in_channels, kH, kW) = (2, 2, 1, 1)
with torch.no_grad():
    # 第一个输出通道：1.0 * ch0 + 0.5 * ch1
    conv.weight[0, 0, 0, 0] = 1.0  # w00
    conv.weight[0, 1, 0, 0] = 0.5  # w01

    # 第二个输出通道：-1.0 * ch0 + 2.0 * ch1
    conv.weight[1, 0, 0, 0] = -1.0  # w10
    conv.weight[1, 1, 0, 0] = 2.0  # w11

# 构造输入张量: shape (1, 2, 2, 2)
x = torch.tensor([[[[1.0, 2.0],
                    [3.0, 4.0]],  # 通道 0
                   [[5.0, 6.0],
                    [7.0, 8.0]]]])  # 通道 1

print("输入 x shape:", x.shape)
print("通道 0:\n", x[0, 0])
print("通道 1:\n", x[0, 1])

print("\n卷积核权重 shape:", conv.weight.shape)
print("权重[0] (输出通道0):", conv.weight[0].squeeze().tolist())  # [1.0, 0.5]
print("权重[1] (输出通道1):", conv.weight[1].squeeze().tolist())  # [-1.0, 2.0]

# 前向传播
output = conv(x)
print("\n输出 shape:", output.shape)  # (1, 2, 2, 2)
print("输出通道 0:\n", output[0, 0])
print("输出通道 1:\n", output[0, 1])
