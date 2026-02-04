"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=2, H=2, W=2
    Conv2d 层：2 in, 1 out, 1x1 kernel
"""

import torch
import torch.nn as nn

# 定义 Conv2d 层：2 输入通道，1 输出通道，1x1 卷积核
conv = nn.Conv2d(in_channels=2, out_channels=1, kernel_size=1, bias=False)

# 手动设置权重以便清晰观察输出
# 权重 shape: (out_channels, in_channels, kH, kW) = (1, 2, 1, 1)
with torch.no_grad():
    conv.weight[0, 0, 0, 0] = 1.0  # 第0个输入通道的权重
    conv.weight[0, 1, 0, 0] = 2.0  # 第1个输入通道的权重

# 构造输入张量: shape (1, 2, 2, 2)
# 通道0: [[1, 2], [3, 4]]
# 通道1: [[5, 6], [7, 8]]
x = torch.tensor([[[[1.0, 2.0],
                    [3.0, 4.0]],
                   [[5.0, 6.0],
                    [7.0, 8.0]]]])

print("输入 x shape:", x.shape)  # (1, 2, 2, 2)
print("通道 0:\n", x[0, 0])
print("通道 1:\n", x[0, 1])

print("\n卷积核权重 (shape: {}):".format(conv.weight.shape))
print("  weight[0,0] =", conv.weight[0, 0].item())  # 1.0
print("  weight[0,1] =", conv.weight[0, 1].item())  # 2.0

# 前向传播
output = conv(x)
print("\n输出 shape:", output.shape)  # (1, 1, 2, 2)
print("输出 feature map:\n", output.squeeze())  # squeeze 后为 (2, 2)

"""
每个空间位置 (i, j) 的输出计算方式为：
output[i, j] = weight[0] * input_ch0[i, j] + weight[1] * input_ch1[i, j]
              = 1.0 * ch0[i,j] + 2.0 * ch1[i,j]
所以：
左上角：1*1 + 2*5 = 11
右上角：1*2 + 2*6 = 14
左下角：1*3 + 2*7 = 17
右下角：1*4 + 2*8 = 20
"""