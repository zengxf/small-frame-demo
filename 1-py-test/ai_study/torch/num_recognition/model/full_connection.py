import torch
from torch import nn


class FCNet(nn.Module):
    def __init__(self):
        super(FCNet, self).__init__()
        self.fc1 = nn.Linear(in_features=1024, out_features=256)
        self.fc2 = nn.Linear(in_features=256, out_features=128)
        self.fc3 = nn.Linear(in_features=128, out_features=64)
        self.fc4 = nn.Linear(in_features=64, out_features=10)
        self.act = nn.ReLU()
        self.out_f = nn.Softmax(dim=1)

    def forward(self, x):
        # 线性 + 激活 组成的对，才是完整的一个层
        x = self.fc1(x)  # 线性
        x = self.act(x)  # 激活 -> 层 1
        x = self.fc2(x)  # 线性
        x = self.act(x)  # 激活 -> 层 2
        x = self.fc3(x)  # 线性
        x = self.act(x)  # 激活 -> 层 3
        x = self.fc4(x)  # 线性
        x = self.act(x)  # 激活 -> 层 4
        x = self.out_f(x)
        return x


if __name__ == '__main__':
    model = FCNet()
    in_ = torch.randn(2, 1024)
    y = model(in_)
    print(y.shape)
    print('y: ', y)

    pass
