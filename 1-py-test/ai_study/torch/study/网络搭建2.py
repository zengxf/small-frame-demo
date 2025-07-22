import torch
import torch.nn as nn
from torchvision.datasets import MNIST
import torchvision
from torchvision import transforms

# torchvision一般用来处理数据的一些库
# MNIST，手写数字识别

# 整个输入的图像x= 28 x 28 x 1
class MyModel(nn.Module):
    '''一般在init中来构建网络算子层的初始属性'''

    def __init__(self, num_class):
        # 继承父类中的初始构造函数
        super(MyModel, self).__init__()
        self.flat = nn.Flatten()  # 定义了一个网络层
        # 用Sequential把它串起来。串串
        self.fc = nn.Sequential(
            nn.Linear(in_features=784, out_features=784),
            nn.Sigmoid(),
            nn.Linear(in_features=784, out_features=128),
            nn.Sigmoid(),
            nn.Linear(in_features=128, out_features=num_class),
            # nn.Softmax()
        )


    def forward(self, input_x):
        return self.fc(self.flat(input_x))


# 输入维度 [ batch, channel, height,width] ->PyTorch
#        [ batch, height,  width,  channel] ->TensorFlow
if __name__ == "__main__":
    # 0. 搭建模型
    net = MyModel(num_class=10)
    # 1. 下载一些公开数据集 图像分类时：X[60000,28,28],Y[60000]，一张图片1个label,label每个分类下标索引
    transforms = transforms.Compose(
        [
            transforms.Resize([28, 28]),
            transforms.ToTensor(),
            transforms.Grayscale(),

        ]
    )
    train_dataset = MNIST('./data', train=True,
                          transform=transforms,
                          download=True)
    test_dataset = MNIST('./data', train=False,
                         transform=transforms,
                         download=True)

    # transform，对图片进行一些额外的处理。一般比如说，图片转成tensor.resize等

    # train_dataset = torchvision.datasets.ImageFolder(
    #     root=r"D:\DLAI\dataSet\train",
    #     transform = transforms
    # )
    # test_dataset = torchvision.datasets.ImageFolder(
    #     root=r"D:\DLAI\dataSet\val",
    #     transform = transforms
    # )


    # 2. 将X存入到dataloader对象中。 dataloader供pytorch自动按batch【每次训练多少张图片】喂入网络
    # 转换成一个生成器对象
    train_loader = torch.utils.data.DataLoader(
        train_dataset,
        batch_size=10,
        shuffle=True
    )#进行小批量梯度下降
    val_loader = torch.utils.data.DataLoader(
        test_dataset,
        batch_size=10,
        shuffle=False
    )

    # 3. 设置优化器
    # net.parameters() 相当于权重 w
    optim = torch.optim.Adam(net.parameters(), lr=1e-4)

    # 4. 设置损失函数
    criterion = nn.CrossEntropyLoss()

    EPOCHS = 1  # epoch的次数，一共要训练多个轮
    # 5. 设置学习的轮数
    for epoch in range(EPOCHS):
        net.train(True)
        # 6. 遍历生成器中的train_loader来进行训练
        for data, label in train_loader:
            net.zero_grad()  # 将上一批的梯度清0。上一个batch的梯度不应该传梯到下一批数据
            # 实现前向传播
            pre_out = net(data)
            # pre_out = torch.argmax(pre_out, dim=1)
            # 利用损失函数，求损失
            loss = criterion(pre_out, label)
            print("训练损失：", loss.item())
            # 根据损失反向传播。求梯
            loss.backward()
            # 根据优化器进行w的更新。梯度下降
            optim.step()

        # 7. 验证集
        net.train(False)
        for t_data, t_label in val_loader:
            pre_out = net(t_data)
            # pre_out = torch.argmax(pre_out, dim=1)
            loss = criterion(pre_out, t_label) # 分类的概率值, 分类的标签
            print("验证损失：", loss.item())

    # 8. 保存模型【保存图结构、保存权重】
    # 方法一
    torch.save(net, f"{epoch}.pth")  # 是将模型和权重都进行保存
    model1 = torch.load('./0.pth') # 加载模型和权重

    # 方法二
    torch.save(net.state_dict(), f'{epoch}-weight.pt')  # 保存的是权重
    weights = torch.load('0-weight.pt')
    model2 = net.load_state_dict(weights)
    pass
