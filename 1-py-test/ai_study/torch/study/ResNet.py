import torch.nn as nn
import torch
import torchvision
from torchvision import transforms


class Conv(nn.Module):
    def __init__(self, in_c, out_c, k, s=1, p=0, is_act=True):
        super(Conv, self).__init__()
        self.conv = nn.Conv2d(in_c, out_c, kernel_size=k, stride=s, padding=p)
        self.bn = nn.BatchNorm2d(out_c)
        self.is_act = is_act
        self.relu = nn.ReLU()

    def forward(self, x):
        x = self.bn(self.conv(x))
        if self.is_act:
            x = self.relu(x)
        return x


class BottleNeck(nn.Module):
    """ 只考虑残差的情况，不考虑各种其它情况 """

    def __init__(self, in_c, is_resnet=True):
        super(BottleNeck, self).__init__()
        out_c = int(in_c / 4)
        self.conv1x1_1 = Conv(in_c, out_c, k=1)  # 256->64
        self.conv3x3_s1 = Conv(out_c, out_c, k=3, s=1, p=1)
        self.conv1x1_2 = Conv(out_c, in_c, k=1, s=1, is_act=False)  # 64->256

        self.download = Conv(out_c, out_c, k=3, s=2, p=1)
        self.download_conv = Conv(out_c, in_c, k=1, s=1, is_act=True)

        self.relu = nn.ReLU()
        self.is_resnet = is_resnet

    def forward(self, x):
        x1 = self.conv1x1_1(x)

        if self.is_resnet:
            x1 = self.conv3x3_s1(x1)
            x1 = x + self.conv1x1_2(x1)
            return self.relu(x1)
        else:
            x1 = self.download(x1)
            x1 = self.download_conv(x1)
            return x1


class MyBlock(nn.Module):
    """ 将 resnet 各种步长的情况再考虑到这里来进行组合 """

    def __init__(self, in_c, repeat=1, isFirst=False):
        super(MyBlock, self).__init__()
        self.repeat = repeat
        self.isFirst = isFirst
        self.block1 = BottleNeck(in_c, is_resnet=True)
        self.block2 = BottleNeck(in_c, is_resnet=False)

    def forward(self, x):
        if self.isFirst:
            # 1x256x56x56
            for _ in range(self.repeat):
                x = self.block1(x)
        else:
            for i in range(self.repeat):
                if i == 0:
                    # 下采样
                    x = self.block2(x)  # 1x512x28x28
                else:
                    x = self.block1(x)
        return x


class ResNet(nn.Module):
    def __init__(self, num_class=10, is_tain=True):
        self.is_train = is_tain
        super(ResNet, self).__init__()
        self.conv7x7 = Conv(3, 64, 7, s=2, p=3)

        self.max_pool = nn.MaxPool2d(kernel_size=3, stride=2, padding=1)

        self.conv1x1_1 = Conv(64, 256, k=1, s=(1, 1))
        self.b1 = MyBlock(256, repeat=3, isFirst=True)

        self.conv2x1_1 = Conv(256, 512, k=1, s=(1, 1))
        self.b2 = MyBlock(512, repeat=4, isFirst=False)

        self.conv3x1_1 = Conv(512, 1024, k=1, s=(1, 1))
        self.b3 = MyBlock(1024, repeat=6, isFirst=False)

        self.conv4x1_1 = Conv(1024, 2048, k=1, s=(1, 1))
        self.b4 = MyBlock(2048, repeat=3, isFirst=False)

        self.pool = nn.AvgPool2d(kernel_size=7)
        self.flat = nn.Flatten()
        self.fc = nn.Linear(2048, num_class)

    def forward(self, x):
        x = self.conv7x7(x)
        if self.is_train:
            x = x + torch.randn(x.shape)
        x = self.max_pool(x)

        x = self.conv1x1_1(x)
        x = self.b1(x)

        x = self.conv2x1_1(x)
        x = self.b2(x)

        x = self.conv3x1_1(x)
        x = self.b3(x)

        x = self.conv4x1_1(x)
        x = self.b4(x)

        x = self.pool(x)
        x = self.flat(x)
        x = self.fc(x)
        return x


if __name__ == "__main__":
    x = torch.randn(1, 3, 224, 224)
    net = ResNet(num_class=10)
    print(net)
    # x = Conv(64, 256, k=1, s=(1, 1))(x)
    #
    # x = MyBlock(256, repeat=3, isFirst=True)(x)  # 1x256x56x56
    #
    # x = Conv(256, 512, k=1, s=(1, 1))(x)  # 1x512x56x56
    # x = MyBlock(512, repeat=4, isFirst=False)(x)
    # x = BottleNeck(128, 128, 4, isFirst=False)(x)
