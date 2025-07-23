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

    torch.serialization.add_safe_globals([MyModel2])
    torch.serialization.add_safe_globals([torch.nn.modules.flatten.Flatten])
    torch.serialization.add_safe_globals([torch.nn.modules.container.Sequential])
    torch.serialization.add_safe_globals([torch.nn.modules.linear.Linear])
    torch.serialization.add_safe_globals([torch.nn.modules.activation.Sigmoid])
    torch.serialization.add_safe_globals([torch.nn.modules.activation.Softmax])

    # 8. 保存模型【保存图结构、保存权重】
    # 方法一
    torch.save(net, f"temp_all.anet")  # 是将模型和权重都进行保存
    model1 = torch.load('./temp_all.anet')  # 加载模型和权重

    # 方法二
    torch.save(net.state_dict(), f'temp_weight.wnet')  # 保存的是权重
    weights = torch.load('temp_weight.wnet')
    model2 = net.load_state_dict(weights)

    print("*" * 40)
    print(model1)
    print("*" * 40)
    print(model2)
