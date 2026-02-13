import torch
from torch import nn

features = 5

a_2 = nn.Parameter(torch.ones(features))
b_2 = nn.Parameter(torch.zeros(features))
eps = 1e-6

print("a_2", a_2)
print("b_2", b_2)
print("*" * 40)

# -------------- forward --------------

x = torch.arange(1, 21).view(4, 5).float()  # 最后一个维度为 features
print("x", x)

# 在统计每个样本所有维度的值，求均值和方差
mean = x.mean(-1, keepdim=True)  # mean: [bsz, max_len, 1]
std = x.std(-1, keepdim=True)  # std: [bsz, max_len, 1]
# 注意这里也在最后一个维度发生了广播
norm_simple = (x - mean) / std  # 标准简单版
norm = a_2 * (x - mean) / (std + eps) + b_2

print("mean", mean)
print("std", std)
print("norm_simple", norm_simple)
print("norm", norm)
