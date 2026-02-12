import torch
import math

block_size = 10
n_embd = 6  # 要是双数

# block size 是序列的最大长度
pe = torch.zeros(block_size, n_embd)  # (10, 6)
position = torch.arange(0, block_size).unsqueeze(1)  # (10, 1)
print("pe", pe)
print("position", position)

# 计算 theta
div_term = torch.exp(
    torch.arange(0, n_embd, 2) * -(math.log(10000.0) / n_embd)
)
print("torch.arange(0, n_embd, 2)", torch.arange(0, n_embd, 2))  # tensor([0, 2, 4])
print("div_term", div_term)  # tensor([1.0000, 0.0464, 0.0022])

# 分别计算 sin、cos 结果
pe[:, 0::2] = torch.sin(position * div_term)  # (10, 1) * (3) = (10, 3)
pe[:, 1::2] = torch.cos(position * div_term)  # pe[:, 0::2] 和 pe[:, 1::2] 都是 (10, 3)，刚好能映射的上
pe = pe.unsqueeze(0)  # (1, 10, 6)

print(pe)
