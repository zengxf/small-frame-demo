"""
自定义 FC 网络

https://docs.pytorch.org/docs/stable/generated/torch.nn.Linear.html
"""

import torch
import torch.nn as nn


class MyModel2(nn.Module):
    def __init__(self, num_class):
        # 继承父类中的初始构造函数
        # super(MyModel2, self).__init__()
        super().__init__()
        self.flat = nn.Flatten()  # 定义了一个网络层
        self.ll = nn.Sequential(  # 快一点。缺点，排错困难
            nn.Linear(in_features=784, out_features=784),
            nn.Sigmoid(),
            nn.Linear(in_features=784, out_features=128),
            nn.Sigmoid(),
            nn.Linear(in_features=128, out_features=num_class),
        )  # 分类层
        self.softmax = nn.Softmax()

    def forward(self, input_x):
        x = self.flat(input_x)
        x = self.ll(x)
        out = self.softmax(x)
        return out


if __name__ == "__main__":
    net = MyModel2(num_class=10)
    print(net)

    # 模拟实现整个数据的前向传播
    data = torch.randn(16, 28, 28, 1)
    pre = net(data)
    print(pre.shape)
    # torch.Size([16, 10])
