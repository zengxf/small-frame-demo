"""
测试 PyTorch cross_entropy 函数
"""

import torch
import torch.nn.functional as F

# 模拟一个 batch_size 为 2，类别为 3 的分类问题
logits = torch.tensor([
    [2.0, 1.0, 0.1],  # 样本 0 的 logits
    [0.5, 2.5, 1.0]  # 样本 1 的 logits
])

# 真实标签：每个样本对应一个类别索引（0, 1, 或 2）
targets = torch.tensor([0, 1])  # 样本 0 结果为类别 0；样本 1 结果为类别 1。loss: 0.36169
# targets = torch.tensor([0, 0])  # loss: 1.36169

# 计算交叉熵损失
loss = F.cross_entropy(logits, targets)

print("Logits:\n", logits)
print("\nTargets:", targets)
print("\nLoss:", loss.item())
