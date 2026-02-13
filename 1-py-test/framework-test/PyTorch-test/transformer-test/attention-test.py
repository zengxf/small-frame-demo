"""
注意力计算
"""

import torch
import math


def attention(query, key, value, dropout=None):
    """
    args:
        query: 查询值矩阵
        key: 键值矩阵
        value: 真值矩阵
    """
    # 获取键向量的维度，键向量的维度和值向量的维度相同
    d_k = query.size(-1)

    # 计算 Q 与 K 的内积并除以根号 dk
    # transpose —— 相当于转置  (2, 3, 4)  ->  (2, 4, 3)
    product = torch.matmul(query, key.transpose(-2, -1))  # (2, 3, 3)
    scores = product / math.sqrt(d_k)  # 相当于除以 2。 (2, 3, 3)
    # Softmax
    p_attn = scores.softmax(dim=-1)  # (2, 3, 3)
    if dropout is not None:
        p_attn = dropout(p_attn)  # 采样
    # 根据计算结果对 value 进行加权求和
    result = torch.matmul(p_attn, value)  # value (2, 3, 4)  =>  (2, 3, 3)
    return result, p_attn


# -------------- forward --------------

# 设置随机种子以便复现
torch.manual_seed(0)

# 假设 batch_size=2, num_heads=1（或不考虑多头）, seq_len=3, d_k=d_v=4
batch_size = 2
seq_len = 3
d_k = d_v = 4

# query = torch.randn(batch_size, seq_len, d_k)
dim = batch_size * seq_len * d_k
query = torch.arange(0, dim).view(batch_size, seq_len, d_k).float()  # (2, 3, 4)
key = torch.arange(11, dim + 11).view(batch_size, seq_len, d_k).float()  # (2, 3, 4)
value = torch.arange(8, dim + 8).view(batch_size, seq_len, d_k).float()  # (2, 3, 4)

print("query >>> \n", query)
print("key >>> \n", key)
print("value >>> \n", value)

# 可选：添加 dropout
dropout = torch.nn.Dropout(p=0.1)

# 调用 attention 函数
output, attn_weights = attention(query, key, value, dropout=dropout)
print("output >>> \n", output)
print("attn_weights >>> \n", attn_weights)

# 自注意力
x = query
output, attn_weights = attention(x, x, x)
print("output >>> \n", output)
print("attn_weights >>> \n", attn_weights)
