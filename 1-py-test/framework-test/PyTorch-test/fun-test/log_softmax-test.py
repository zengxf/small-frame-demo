"""
log_softmax 测试
"""

import torch
import torch.nn.functional as F

# 1. 创建一个模拟的 logits 张量 (batch_size=2, num_classes=5)
logits = torch.tensor([[1.0, 2.0, 3.0, 4.0, 5.0],
                       [2.0, 1.0, 0.0, -1.0, -2.0]])

print("输入 logits:\n", logits)

# 2. 在类别维度（dim=1）上应用 log_softmax
log_probs = F.log_softmax(logits, dim=1)
print("\nlog_softmax 输出 (dim=1):\n", log_probs)

# 3. 验证：log_softmax 等价于 log(softmax(x))
softmax_probs = F.softmax(logits, dim=1)
print("\nsoftmax 输出 (dim=1):\n", softmax_probs)

log_of_softmax = torch.log(softmax_probs)
print("\n验证: log(softmax(x)) == log_softmax(x)?")
print(torch.allclose(log_probs, log_of_softmax))  # 应为 True

# 4. 实际用途：配合 NLLLoss 计算损失
# 假设真实标签为 [4, 0]（即第一个样本属于第4类，第二个属于第0类）
targets = torch.tensor([4, 0])

# 使用 NLLLoss（要求输入是 log-probabilities）
nll_loss = F.nll_loss(log_probs, targets)
print("\nNLLLoss:", nll_loss.item())

# 对比：直接使用 CrossEntropyLoss（内部自动做 softmax + nll）
ce_loss = F.cross_entropy(logits, targets)
print("CrossEntropyLoss (应与 NLLLoss 相同):", ce_loss.item())
print("两者是否相等?", torch.allclose(nll_loss, ce_loss))  # 应为 True
