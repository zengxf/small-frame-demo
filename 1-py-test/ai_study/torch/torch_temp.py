import torch
from torch import nn

m = nn.ReLU()
input = torch.randn(2)
output = m(input)

print(input)
print(output)
print()
# tensor([ 1.5854, -0.1043])
# tensor([1.5854, 0.0000])

m = nn.ReLU()
input = torch.randn(2).unsqueeze(0)
output = torch.cat((m(input), m(-input)))

print(input)
print(output)
# tensor([[ 1.0531, -0.8241]])
# tensor([[1.0531, 0.0000],
#         [0.0000, 0.8241]])
