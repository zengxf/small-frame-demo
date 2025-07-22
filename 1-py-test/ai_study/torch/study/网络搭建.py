import torch
import torch.nn as nn


# 整个输入的图像 x = 28 x 28 x 1
class MyModel(nn.Module):
    '''一般在init中来构建网络算子层的初始属性'''
    def __init__(self, num_class):
        # 继承父类中的初始构造函数
        super(MyModel, self).__init__()
        self.flat = nn.Flatten()  # 定义了一个网络层
        self.fc1 = nn.Linear(in_features=784, out_features=784)  # FC1
        self.fc2 = nn.Linear(in_features=784, out_features=128)  # FC2
        self.cls = nn.Linear(in_features=128, out_features=num_class)  # 分类层
        self.act = nn.Sigmoid()  # 激活函数
        self.softmax = nn.Softmax()  # 互斥概率之和为1

        # torch.sigmoid()，计算函数，常用来求损失;
        # nn.Sigmoid() 算一个图结构中的层，在PyTorch中算一个网络层

    # forward，必须叫这个名称。但是输入的参数，可以有多少。至少有1个input_X
    def forward(self, input_X):
        x = self.flat(input_X) # 16x784
        x = self.fc1(x)
        x = self.act(x)
        x = self.fc2(x)
        x = self.act(x)
        out = self.softmax(self.cls(x))
        return out


"""
1. 继承nn.Module
2. 在__init__中描述算子层(nn.xx)
3. forward中实现输入x的前向传播
"""
class MyModel2(nn.Module):
    '''一般在init中来构建网络算子层的初始属性'''

    def __init__(self, num_class):
        # 继承父类中的初始构造函数
        super(MyModel2, self).__init__()
        self.flat = nn.Flatten()  # 定义了一个网络层
        self.ll = nn.Sequential( # 快一点。缺点，排错困难
            nn.Linear(in_features=784, out_features=784),
            nn.Sigmoid(),
            nn.Linear(in_features=784, out_features=128),
            nn.Sigmoid(),
            nn.Linear(in_features=128, out_features=num_class),
            nn.Sigmoid()
        )  # 分类层
        self.act = nn.Sigmoid()  # 激活函数
        self.softmax = nn.Softmax()  # 互斥概率之和为1

        # torch.sigmoid()，计算函数，常用来求损失;
        # nn.Sigmoid() 算一个图结构中的层，在PyTorch中算一个网络层

    ''' forward，必须叫这个名称。但是输入的参数，可以有多少。至少有1个input_X'''

    def forward(self, input_X):
        x = self.flat(input_X)
        x = self.ll(x)
        out = self.softmax(self.act(x))
        return out


if __name__ == "__main__":
    device = "cuda:0" if torch.cuda.is_available() else "cpu"
    net = MyModel(num_class=10).to(device)
    print(net)
    # 模拟实现整个数据的前向传播
    # pytorch的输入应该是一个4维矩阵。[batch,height,width,channel] . batch，有几张图片用来输入网络
    data = torch.randn(16, 28, 28, 1).to(device)
    pre = net(data)
    print(pre.shape)
