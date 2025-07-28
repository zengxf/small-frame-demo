"""
MaxPool2d

https://docs.pytorch.org/docs/stable/generated/torch.nn.MaxPool2d.html
"""

import torch
import torch.nn as nn

# pool of square window of size=3, stride=2
m = nn.MaxPool2d(3, stride=2)

# pool of non-square window
m = nn.MaxPool2d((3, 2), stride=(2, 1))

input = torch.randn(20, 16, 50, 32)
output = m(input)

print(output.shape)
