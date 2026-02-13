"""
nn.Embedding 是 PyTorch 中用于将离散的整数索引（如词 ID）映射为稠密向量（即“嵌入”）的模块。
    它常用于自然语言处理（NLP）中，将词汇表中的每个词转换为其对应的向量表示。

作用：
    将一个整数（通常是类别或 token 的索引）转换为一个固定维度的浮点向量。
    这些向量在训练过程中会被优化，使得语义相近的 token 在向量空间中距离更近。

把它想象成一个可学习的查找表（Lookup Table）。

入参必须是整数类型（torch.long 或 torch.int64）
"""

import torch
import torch.nn as nn

# 假设词汇表大小为 10（token id 范围是 0~9），每个 token 映射到 4 维向量
embedding = nn.Embedding(num_embeddings=10, embedding_dim=4)

print("embedding.weight >>> \n", embedding.weight)  # shape: [vocab_size, embed_dim]

print("*" * 50)

# 输入：一批 token 的索引（shape: [2, 3]）
input_ids = torch.tensor([[1, 2, 4],
                          [5, 0, 9]])

# 输出：对应的嵌入向量（shape: [2, 3, 4]）
embedded = embedding(input_ids)

print("input_ids.dtype >>> ", input_ids.dtype)  # 查看元素类型
print(embedded.shape)  # torch.Size([2, 3, 4])
print(embedded)

print("*" * 50)

input_ids = torch.tensor([7, 8, 9])  # 只能 0~9，否则 IndexError: index out of range in self
embedded = embedding(input_ids)
print(embedded.shape)  # torch.Size([3, 4])
print(embedded)
