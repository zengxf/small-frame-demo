"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=2, H=3, W=3
    Conv2d 层：2 in, 1 out, 2x2 kernel
"""

import torch
import torch.nn as nn

# 1. 构造输入张量: shape = (1, 2, 3, 3)
# 第一个通道是 1~9，第二个通道是 10~18
input_tensor = torch.tensor([[
    [[1., 2., 3.],
     [4., 5., 6.],
     [7., 8., 9.]],  # 通道 0
    [[10., 11., 12.],
     [13., 14., 15.],
     [16., 17., 18.]]  # 通道 1
]])
print("输入张量形状:", input_tensor.shape)  # (1, 2, 3, 3)
print("输入张量内容:\n", input_tensor)

# 2. 定义 Conv2d 层: 2 个输入通道, 1 个输出通道, 卷积核大小 2x2，不使用偏置以便验证
conv = nn.Conv2d(in_channels=2, out_channels=1, kernel_size=2, bias=False)

# 手动设置卷积核权重：
# 权重形状应为 (out_channels, in_channels, kH, kW) = (1, 2, 2, 2)
# 设定：
#   - 通道 0 的卷积核为 [[1, 0], [0, 1]]
#   - 通道 1 的卷积核为 [[0, 1], [1, 0]]
conv.weight.data = torch.tensor([[
    [[1., 0.],
     [0., 1.]],  # 用于输入通道 0 的卷积核
    [[0., 1.],
     [1., 0.]]  # 用于输入通道 1 的卷积核
]])
print("\n卷积核权重形状:", conv.weight.shape)  # (1, 2, 2, 2)
print("输入通道 0 对应的卷积核权重:\n", conv.weight[0, 0])
print("输入通道 1 对应的卷积核权重:\n", conv.weight[0, 1])

# 3. 执行卷积运算
output = conv(input_tensor)
print("\n输出张量形状:", output.shape)  # (1, 1, 2, 2)
print("输出张量内容:\n", output.squeeze())

# 4. 手动验证输出结果（每个输出位置 = 各通道卷积结果之和）
print("\n--- 手动验证计算过程 ---")
# 输出位置 (0,0):
#   通道0: 1*1 + 2*0 + 4*0 + 5*1 = 1 + 5 = 6
#   通道1: 10*0 + 11*1 + 13*1 + 14*0 = 11 + 13 = 24
#   总和 = 6 + 24 = 30

# (0,1):
#   通道0: 2*1 + 3*0 + 5*0 + 6*1 = 2 + 6 = 8
#   通道1: 11*0 + 12*1 + 14*1 + 15*0 = 12 + 14 = 26
#   总和 = 8 + 26 = 34

# (1,0):
#   通道0: 4*1 + 5*0 + 7*0 + 8*1 = 4 + 8 = 12
#   通道1: 13*0 + 14*1 + 16*1 + 17*0 = 14 + 16 = 30
#   总和 = 12 + 30 = 42

# (1,1):
#   通道0: 5*1 + 6*0 + 8*0 + 9*1 = 5 + 9 = 14
#   通道1: 14*0 + 15*1 + 17*1 + 18*0 = 15 + 17 = 32
#   总和 = 14 + 32 = 46

manual_result = torch.tensor([[30., 34.],
                              [42., 46.]])
print("手动计算结果:\n", manual_result)
print("是否与 PyTorch 输出一致:", torch.allclose(output.squeeze(), manual_result))
