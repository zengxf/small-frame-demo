"""
前馈神经网络，
也叫多层感知器
"""

import torch
from torch import nn
import torch.nn.functional as F

dim = 10
hidden_dim = 5
dropout = 0.2

# 定义第一层线性变换，从输入维度到隐藏维度
w1 = nn.Linear(dim, hidden_dim, bias=False)
# 定义第二层线性变换，从隐藏维度到输入维度
w2 = nn.Linear(hidden_dim, dim, bias=False)
# 定义dropout层，用于防止过拟合
dropout = nn.Dropout(dropout)

# -------------- forward --------------

# 创建 0~9 的有序张量
# 注意：Linear 层接受的输入形状通常是 [batch_size, in_features]
# 所以需要 view(1, -1) 将其变为 [1, 10]
xa = torch.arange(0, dim)  # (10,)
x = xa.float().view(1, dim)  # (1, 10)
print("xa", xa)
print("x", x)
print()

# 首先，输入x通过第一层线性变换和RELU激活函数
# 最后，通过第二层线性变换和dropout层
w1_x = w1(x)  # (1, 5)
relu_x = F.relu(w1_x)  # (1, 5)
w2_x = w2(relu_x)  # (1, 10)
dropout_x = dropout(w2_x)  # (1, 10)

print("w1_x", w1_x)
print("relu_x", relu_x)
print("w2_x", w2_x)
print("dropout_x", dropout_x)
