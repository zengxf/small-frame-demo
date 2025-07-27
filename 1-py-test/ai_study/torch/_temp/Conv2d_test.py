"""
2D 卷积 - 测试

https://docs.pytorch.org/docs/stable/generated/torch.nn.Conv2d.html
"""

import torch
import torch.nn as nn

# With square kernels and equal stride
m = nn.Conv2d(16, 33, 3, stride=2)

# non-square kernels and unequal stride and with padding
m = nn.Conv2d(16, 33, (3, 5), stride=(2, 1), padding=(4, 2))

# non-square kernels and unequal stride and with padding and dilation
m = nn.Conv2d(16, 33, (3, 5), stride=(2, 1), padding=(4, 2), dilation=(3, 1))

in_param = torch.randn(20, 16, 50, 100)
output = m(in_param)

print(in_param.shape)
# torch.Size([20, 16, 50, 100])

print(output.shape)
# torch.Size([20, 33, 26, 100])