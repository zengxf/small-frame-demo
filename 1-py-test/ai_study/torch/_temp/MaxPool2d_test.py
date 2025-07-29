"""
MaxPool2d

https://docs.pytorch.org/docs/stable/generated/torch.nn.MaxPool2d.html
"""

import torch
import torch.nn as nn

# 输入：batch=1, channels=1, H=4, W=4
x = torch.tensor([[[[1, 2, 3, 4],
                    [5, 6, 7, 8],
                    [9, 10, 11, 12],
                    [13, 14, 15, 16]]]], dtype=torch.float32)

pool = nn.MaxPool2d(kernel_size=2, stride=2)
out = pool(x)
print(out)
"""
计算过程：
左上 2×2 区域 max(1,2,5,6)=6
右上 2×2 区域 max(3,4,7,8)=8
左下 2×2 区域 max(9,10,13,14)=14
右下 2×2 区域 max(11,12,15,16)=16
"""
# tensor([[[ 6.,  8.],
#          [14., 16.]]])


x = torch.tensor([[[[1, 2, 3],
                    [4, 5, 6],
                    [7, 8, 9]]]], dtype=torch.float32)

pool = nn.MaxPool2d(kernel_size=3, stride=1, padding=1)
out = pool(x)
print(out)
# tensor([[[[5., 6., 6.],
#           [8., 9., 9.],
#           [8., 9., 9.]]]])


x = torch.tensor([[[[1, 2, 3, 4, 5],
                    [6, 7, 8, 9, 10],
                    [11, 12, 13, 14, 15],
                    [16, 17, 18, 19, 20],
                    [21, 22, 23, 24, 25]]]], dtype=torch.float32)

pool = nn.MaxPool2d(kernel_size=2, stride=1, dilation=2)
out = pool(x)
print(out)
"""
计算说明（dilation=2 时空洞间距为 1）：
第一个窗口取第 0,2 行 × 第 0,2 列，即元素 [1,3,11,13]，max=13
第二个窗口取第 0,2 行 × 第 1,3 列，即 [2,4,12,14]，max=14
依此类推……
"""
# tensor([[[[13., 14., 15.],
#           [18., 19., 20.],
#           [23., 24., 25.]]]])
