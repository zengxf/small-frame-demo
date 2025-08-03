import torch.nn as nn
import torch

net1 = nn.Conv2d(3,3,kernel_size=(3,1),stride=1)
net2 = nn.Conv2d(3,3,kernel_size=(1,3),stride=1)

x = torch.randn((1, 3,3,3)) # bchw

y1 = net1(x)
print(y1.shape)
y2 = net2(y1)
print(y2.shape)