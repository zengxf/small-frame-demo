"""
nn.Dropout 是 PyTorch 中用于正则化（regularization）的一种常用技术，主要作用是防止神经网络过拟合。

在训练过程中，Dropout 会随机将一部分神经元的输出置为 0（即“丢弃”这些神经元），
其余神经元的输出会按比例放大（除以保留概率 1 - p），以保持整体期望值不变。
"""

import torch
import torch.nn as nn

# 创建一个简单的输入张量 (batch_size=2, features=5)
x = torch.ones(2, 5)
print("原始输入 x:\n", x)

# 定义 Dropout 层，丢弃概率 p=0.5
dropout = nn.Dropout(p=0.5)

# 模型处于训练模式（默认）
dropout.train()  # 可省略，因为默认就是 train 模式
y_train = dropout(x)
print("\n训练模式下的输出（部分元素被置0，并缩放）:\n", y_train)

# 切换到评估模式（测试/推理）
dropout.eval()
y_eval = dropout(x)
print("\n评估模式下的输出（无变化，等于输入）:\n", y_eval)
