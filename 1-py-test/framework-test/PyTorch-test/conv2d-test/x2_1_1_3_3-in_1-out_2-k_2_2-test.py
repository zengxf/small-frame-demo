"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=1, H=3, W=3
    Conv2d 层：1 in, 2 out, 2x2 kernel
"""

import torch
import torch.nn as nn

# 1. 构造输入张量: shape = (1, 1, 3, 3)
input_tensor = torch.tensor([[
    [[1., 2., 3.],
     [4., 5., 6.],
     [7., 8., 9.]]
]])
print("输入张量形状:", input_tensor.shape)  # (1, 1, 3, 3)
print("输入张量内容:\n", input_tensor.squeeze())

# 2. 定义 Conv2d 层: 1 个输入通道, 2 个输出通道, 卷积核大小 2x2，不使用偏置以便验证
conv = nn.Conv2d(in_channels=1, out_channels=2, kernel_size=2, bias=False)

# 手动设置卷积核权重：
# 权重形状应为 (out_channels, in_channels, kH, kW) = (2, 1, 2, 2)
# 设定两个不同的卷积核：
#   - 输出通道 0 的卷积核: [[1, 0], [0, 1]] （提取主对角线）
#   - 输出通道 1 的卷积核: [[0, 1], [1, 0]] （提取副对角线）
conv.weight.data = torch.tensor([
    [[[1., 0.],
      [0., 1.]]],  # 输出通道 0 的卷积核
    [[[0., 1.],
      [1., 0.]]]  # 输出通道 1 的卷积核
])
print("\n卷积核权重形状:", conv.weight.shape)  # (2, 1, 2, 2)
print("输出通道 0 的卷积核:\n", conv.weight[0, 0])
print("输出通道 1 的卷积核:\n", conv.weight[1, 0])

# 3. 执行卷积运算
output = conv(input_tensor)
print("\n输出张量形状:", output.shape)  # (1, 2, 2, 2)
print("输出通道 0 的结果:\n", output[0, 0])
print("输出通道 1 的结果:\n", output[0, 1])

# 4. 手动验证每个输出通道
print("\n--- 手动验证计算过程 ---")

# 输出通道 0（卷积核 [[1,0],[0,1]]）:
# (0,0): 1*1 + 2*0 + 4*0 + 5*1 = 1 + 5 = 6
# (0,1): 2*1 + 3*0 + 5*0 + 6*1 = 2 + 6 = 8
# (1,0): 4*1 + 5*0 + 7*0 + 8*1 = 4 + 8 = 12
# (1,1): 5*1 + 6*0 + 8*0 + 9*1 = 5 + 9 = 14
out0_manual = torch.tensor([[6., 8.],
                            [12., 14.]])

# 输出通道 1（卷积核 [[0,1],[1,0]]）:
# (0,0): 1*0 + 2*1 + 4*1 + 5*0 = 2 + 4 = 6
# (0,1): 2*0 + 3*1 + 5*1 + 6*0 = 3 + 5 = 8
# (1,0): 4*0 + 5*1 + 7*1 + 8*0 = 5 + 7 = 12
# (1,1): 5*0 + 6*1 + 8*1 + 9*0 = 6 + 8 = 14
out1_manual = torch.tensor([[6., 8.],
                            [12., 14.]])

print("输出通道 0 手动计算结果:\n", out0_manual)
print("输出通道 1 手动计算结果:\n", out1_manual)

# 验证是否匹配
match0 = torch.allclose(output[0, 0], out0_manual)
match1 = torch.allclose(output[0, 1], out1_manual)
print("\n输出通道 0 是否匹配:", match0)
print("输出通道 1 是否匹配:", match1)
