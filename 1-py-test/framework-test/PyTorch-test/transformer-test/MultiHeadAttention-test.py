"""
多头(自)注意力
"""

import torch
import math
from torch import nn
import torch.nn.functional as F

n_embd = 4
n_heads = 2
dim = 10
dropout = 0.0

# 隐藏层维度必须是头数的整数倍，因为后面我们会将输入拆成头数个矩阵
assert dim % n_heads == 0

# 每个头的维度，等于模型维度除以头的总数。
head_dim = dim // n_heads

# Wq, Wk, Wv 参数矩阵，每个参数矩阵为 n_embd x dim
# 这里通过三个组合矩阵来代替了n个参数矩阵的组合，其逻辑在于矩阵内积再拼接其实等同于拼接矩阵再内积，
# 不理解的读者可以自行模拟一下，每一个线性层其实相当于n个参数矩阵的拼接
wq = nn.Linear(n_embd, n_heads * head_dim, bias=False)  # in 4, out 10
wk = nn.Linear(n_embd, n_heads * head_dim, bias=False)  # in 4, out 10
wv = nn.Linear(n_embd, n_heads * head_dim, bias=False)  # in 4, out 10
# 输出权重矩阵，维度为 dim x dim（head_dim = dim / n_heads）
wo = nn.Linear(n_heads * head_dim, dim, bias=False)  # in 10, out 10
# 注意力的 dropout
attn_dropout = nn.Dropout(dropout)
# 残差连接的 dropout
resid_dropout = nn.Dropout(dropout)

# -------------- forward --------------


x = torch.ones(1, 10, 4)
q = x
k = x
v = x

# 获取批次大小和序列长度，[batch_size, seq_len, dim]
bsz, seqlen, _ = q.shape

# 计算查询（Q）、键（K）、值（V）,输入通过参数矩阵层，维度为 (B, T, n_embed) x (n_embed, dim) -> (B, T, dim)
xq, xk, xv = wq(q), wk(k), wv(v)  # (1, 10, 10)

# 将 Q、K、V 拆分成多头，维度为 (B, T, n_head, dim // n_head)，然后交换维度，变成 (B, n_head, T, dim // n_head)
# 因为在注意力计算中我们是取了后两个维度参与计算
# 为什么要先按B*T*n_head*C//n_head展开再互换1、2维度而不是直接按注意力输入展开，是因为view的展开方式是直接把输入全部排开，
# 然后按要求构造，可以发现只有上述操作能够实现我们将每个头对应部分取出来的目标
xq = xq.view(bsz, seqlen, n_heads, head_dim)  # (1, 10, 2, 5)
xk = xk.view(bsz, seqlen, n_heads, head_dim)  # (1, 10, 2, 5)
xv = xv.view(bsz, seqlen, n_heads, head_dim)  # (1, 10, 2, 5)
xq = xq.transpose(1, 2)  # (1, 2, 10, 5)
xk = xk.transpose(1, 2)  # (1, 2, 10, 5)
xv = xv.transpose(1, 2)  # (1, 2, 10, 5)

# -------------- 分数计算 --------------


# 注意力计算
# 计算 QK^T / sqrt(d_k)，维度为 (B, nh, T, hs) x (B, nh, hs, T) -> (B, nh, T, T)
# xk.transpose(2, 3) -> (1, 2, 5, 10)
# matmul -> (1, 2, 10, 10)
scores = torch.matmul(xq, xk.transpose(2, 3)) / math.sqrt(head_dim)  # (1, 2, 10, 10)
# 计算 softmax，维度为 (B, nh, T, T)
scores = F.softmax(scores.float(), dim=-1).type_as(xq)  # (1, 2, 10, 10)
# 做 Dropout
scores = attn_dropout(scores)
print("scores  >>>  \n", scores)

# V * Score，维度为(B, nh, T, T) x (B, nh, T, hs) -> (B, nh, T, hs)
output = torch.matmul(scores, xv)  # (1, 2, 10, 5)
print("output   src  >>>  \n", output)

# 恢复时间维度并合并头。
# 将多头的结果拼接起来, 先交换维度为 (B, T, n_head, dim // n_head)，再拼接成 (B, T, n_head * dim // n_head)
# contiguous 函数用于重新开辟一块新内存存储，因为Pytorch设置先transpose再view会报错，
# 因为view直接基于底层存储得到，然而transpose并不会改变底层存储，因此需要额外存储
# transpose -> (1, 2, 10, 5)
# view -> (1, 10, 10)
output = output.transpose(1, 2).contiguous().view(bsz, seqlen, -1)  # (1, 10, 10)
print("output  view  >>>  \n", output)

# 最终投影回残差流。
output = wo(output)  # (1, 10, 10)
print("output    wo  >>>  \n", output)

output = resid_dropout(output)
print("output   end  >>>  \n", output)
