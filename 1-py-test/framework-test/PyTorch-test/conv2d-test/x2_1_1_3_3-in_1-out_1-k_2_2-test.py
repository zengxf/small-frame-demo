"""
测试 PyTorch Conv2d：
    输入：batch=1, channel=1, H=3, W=3
    Conv2d 层：1 in, 1 out, 2x2 kernel
"""

import torch
import torch.nn as nn

# 1. 创建输入张量 (batch=1, channel=1, H=3, W=3)
input_tensor = torch.arange(1, 10).float().view(1, 1, 3, 3)
print("Input shape:", input_tensor.shape)
print("Input:\n", input_tensor.squeeze())

# 2. 创建 Conv2d 层 (1 in, 1 out, 2x2 kernel)
# 固定权重便于手动验证
conv = nn.Conv2d(in_channels=1, out_channels=1, kernel_size=2, bias=False)
conv.weight.data = torch.tensor([[[[1.0, 0.0],
                                   [0.0, 1.0]]]])  # 1x1x2x2
print("\nKernel weight:\n", conv.weight.squeeze())

# 3. 执行卷积
output = conv(input_tensor)
print("\nOutput shape:", output.shape)  # (1, 1, 2, 2)
print("Output:\n", output.squeeze())

# 4. 手动验证卷积计算
print("\n--- Manual Verification ---")
# 卷积滑动窗口计算 (stride=1, padding=0)
# 位置 (0,0): 1*1 + 2*0 + 4*0 + 5*1 = 1 + 5 = 6
# 位置 (0,1): 2*1 + 3*0 + 5*0 + 6*1 = 2 + 6 = 8
# 位置 (1,0): 4*1 + 5*0 + 7*0 + 8*1 = 4 + 8 = 12
# 位置 (1,1): 5*1 + 6*0 + 8*0 + 9*1 = 5 + 9 = 14
manual_result = torch.tensor([[6., 8.],
                              [12., 14.]])
print("Manual result:\n", manual_result)
print("Match:", torch.allclose(output.squeeze(), manual_result))
