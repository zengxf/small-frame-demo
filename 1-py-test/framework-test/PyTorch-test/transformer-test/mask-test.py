"""
掩码
"""

import torch
import torch.nn.functional as F

max_seq_len = 10
seq_len = 5

# 创建一个上三角矩阵，用于遮蔽未来信息。
# 先通过 full 函数创建一个 1 * seq_len * seq_len 的矩阵
# mask = torch.full((1, max_seq_len, max_seq_len), float("-inf"))
mask = torch.full((1, max_seq_len, max_seq_len), float("-9"))
print("mask src  ==>  \n", mask)

# triu 函数的功能是创建一个上三角矩阵
mask = torch.triu(mask, diagonal=1)  # 保留主对角线上方 1 行及以上的元素（不含主对角线），忽略主对角线及以下的元素。
print("mask TRIU ==>  \n", mask)

# -------------- forward --------------

# 此处的 scores 为计算得到的注意力分数，mask 为上文生成的掩码矩阵
scores = torch.ones(2, 5, 5)
print("scores src  ==>  \n", scores)

scores = scores + mask[:, :seq_len, :seq_len]
print("scores mask ==>  \n", scores)

# xq = torch.ones(1, 1, 1).type(torch.int32)  # type_as() 后全是 0
xq = torch.ones(1, 1, 1).type(torch.float16)
# xq = torch.ones(1, 1, 1)  # 不影响数据
scores = F.softmax(scores.float(), dim=-1).type_as(xq)  # type_as() 转换成与目标张量（xq）相同的数据类型（dtype）和设备（device）
print("scores soft ==>  \n", scores)
