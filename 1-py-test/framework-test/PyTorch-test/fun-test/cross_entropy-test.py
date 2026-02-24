"""
测试 PyTorch cross_entropy 函数
"""

import torch
from torch import nn
import torch.nn.functional as F

# 模拟一个 batch_size 为 3，类别为 5 的分类问题
logits = torch.tensor([
    [2.0, 1.0, 0.1, 0.5, 0.3],  # 样本 1 的 logits
    [0.2, 3.0, 1.5, 0.8, 1.0],  # 样本 2 的 logits
    [1.0, 0.5, 2.5, 3.0, 0.1]  # 样本 3 的 logits
])

# 真实标签：每个样本对应一个类别索引（0, 1, 2, 3, 4）
targets = torch.tensor([0, 1, 3])  # 样本 0 结果为类别 0；样本 1 结果为类别 1；样本 2 结果为类别 3。=> loss: 0.57002
# targets = torch.tensor([0, 0, 0])  # => loss: 2.17002

# 计算交叉熵损失
loss = F.cross_entropy(logits, targets)

print("Logits:\n", logits)
print("\nTargets:", targets)
print("\nLoss:", loss.item())
print("*" * 50)
print("*" * 50)

# ------------------
# ------------------

# 创建损失函数
criterion = nn.CrossEntropyLoss()

# 计算损失（自动应用 softmax + NLLLoss）
loss = criterion(logits, targets)
print(f"损失值: {loss.item():.4f}")

# 验证：手动计算 softmax + NLLLoss
# NLLLoss：Negative Log Likelihood Loss，负对数似然损失函数。
softmax = nn.Softmax(dim=1)
probs = softmax(logits)

# range(len(targets)) 即 range(0, 3), 也就是 0 ~ 2
# probs[range(len(targets)), targets] 相当于取 probs[0][0], probs[1][1], probs[2][3]
nll_loss = -torch.log(probs[range(len(targets)), targets]).mean()
print(f"手动计算结果: {nll_loss.item():.4f} (应与上面相同)")
