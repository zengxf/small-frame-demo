import torch
import torch.nn as nn

# 假设输入特征图通道数为 3
bn = nn.BatchNorm2d(3, eps=1e-5, momentum=0.1, affine=True)

# 手动设置 running_mean 和 running_var（实际中这些是训练得到的）
with torch.no_grad():
    bn.running_mean = torch.tensor([0.2, 0.5, 0.7])
    bn.running_var = torch.tensor([0.3, 0.4, 0.2])

    # 设置 gamma（weight）和 beta（bias）为可学习的仿射参数
    bn.weight = nn.Parameter(torch.tensor([1.5, 2.0, 0.5]))
    bn.bias = nn.Parameter(torch.tensor([0.1, -0.3, 0.8]))

# 切换到评估模式（使用全局统计量）
bn.eval()

# 构造一个单样本（batch_size=1）、通道=3、高=2、宽=2 的输入张量：
x = torch.tensor([[[[1.0, 2.0],
                    [3.0, 4.0]],

                   [[5.0, 6.0],
                    [7.0, 8.0]],

                   [[9.0, 10.0],
                    [11.0, 12.0]]]])

# 实际计算
with torch.no_grad():
    y = bn(x)
print(y)
"""
$$
\hat{x} 
= \frac{1.0 - 0.2}{\sqrt{0.3 + 1e-5}} 
~= 1.461  
\
\rightarrow y 
= 1.461 \times 1.5 + 0.1 
~= 2.291
$$
"""
# tensor([[[[ 2.2909,  5.0294],
#           [ 7.7680, 10.5066]],
#
#          [[13.9301, 17.0923],
#           [20.2545, 23.4168]],
#
#          [[10.0795, 11.1975],
#           [12.3155, 13.4335]]]])

nn.LayerNorm