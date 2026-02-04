"""
测试 ReLU 函数
"""

import torch
import torch.nn as nn
import torch.nn.functional as F

# 创建一个包含正负值的张量
x = torch.tensor([-2.0, -1.0, 0.0, 1.0, 2.0])
print("输入张量:", x)

# 方法1：使用 torch.nn.functional.relu
y1 = F.relu(x)
print("F.relu 输出:", y1)

# 方法2：使用 torch.relu（等价于 F.relu）
y2 = torch.relu(x)
print("torch.relu 输出:", y2)

# 方法3：使用 nn.ReLU 模块（适用于模型中）
relu_layer = nn.ReLU()
y3 = relu_layer(x)
print("nn.ReLU() 输出:", y3)

# 方法4：原地 ReLU（修改原张量）
x_inplace = x.clone()  # 克隆避免影响原始数据
x_inplace.relu_()  # 原地操作，带下划线
print("原地 ReLU 后:", x_inplace)
