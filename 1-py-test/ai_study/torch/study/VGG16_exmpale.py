# 调的库名
import torch.nn as nn
import torch

"""
变成一个算子库。模型结构库

"""


class Conv(nn.Module):
    """
    定义一个可以复用的conv-relu模块
    """

    def __init__(self, in_c, out_c, k, s, p=0, is_act=True):
        super().__init__()
        # 卷积
        self.conv = nn.Conv2d(in_c, out_c, k, s, padding=p)
        self.act = nn.ReLU()
        self.is_act = is_act

    def forward(self, input_x):
        if self.is_act:
            return self.act(self.conv(input_x))
        return self.conv(input_x)


class MaxPool(nn.Module):
    def __init__(self, k, s, p=0):
        super().__init__()
        self.max_pool = nn.MaxPool2d(k, s, padding=p)

    def forward(self, input_x):
        return self.max_pool(input_x)


class Vgg16(nn.Module):
    def __init__(self, class_num=10):
        super().__init__()
        self.class_num = class_num

        self.conv1_1 = Conv(3, 64, 3, 1, 1)
        self.conv1_2 = Conv(64, 64, 3, 1, 1)
        self.max_pool1 = MaxPool(2, 2)

        self.conv2_1 = Conv(64, 128, 3, 1, 1)
        self.conv2_2 = Conv(128, 128, 3, 1, 1)
        self.max_pool2 = MaxPool(2, 2)

        self.conv3_1 = Conv(128, 256, 3, 1, 1)
        self.conv3_2 = Conv(256, 256, 3, 1, 1)
        self.conv3_3 = Conv(256, 256, 3, 1, 1)
        self.max_pool3 = MaxPool(2, 2)

        self.conv4_1 = Conv(256, 512, 3, 1, 1)
        self.conv4_2 = Conv(512, 512, 3, 1, 1)
        self.conv4_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool4 = MaxPool(2, 2)

        self.conv5_1 = Conv(512, 512, 3, 1, 1)
        self.conv5_2 = Conv(512, 512, 3, 1, 1)
        self.conv5_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool5 = MaxPool(2, 2)  # 这里的输出

        self.fc1 = nn.Linear(7 * 7 * 512, 4096)
        self.fc2 = nn.Linear(4096, 4096)
        self.fc3 = nn.Linear(4096, self.class_num)

        self.flat = nn.Flatten()
        self.relu = nn.ReLU()

    def forward(self, x):
        x = self.conv1_1(x)  #
        x = self.conv1_2(x)  #
        x = self.max_pool1(x)  # 1x64x112x112

        x = self.conv2_1(x)
        x = self.conv2_2(x)
        x = self.max_pool2(x)

        x = self.conv3_1(x)
        x = self.conv3_2(x)
        x = self.conv3_3(x)
        x = self.max_pool3(x)

        x = self.conv4_1(x)
        x = self.conv4_2(x)
        x = self.conv4_3(x)
        x = self.max_pool4(x)

        x = self.conv5_1(x)
        x = self.conv5_2(x)
        x = self.conv5_3(x)
        x = self.max_pool5(x)

        x = self.flat(x)
        x = self.fc1(x)
        x = self.relu(x)

        x = self.fc2(x)
        x = self.relu(x)

        out = self.fc3(x)
        print(out.shape)
        return out


class Vgg16_Add(nn.Module):
    def __init__(self, class_num=10):
        super().__init__()
        self.class_num = class_num

        self.conv1_1 = Conv(3, 64, 3, 1, 1)
        self.conv1_2 = Conv(64, 64, 3, 1, 1)
        self.max_pool1 = MaxPool(2, 2)

        self.conv2_1 = Conv(64, 128, 3, 1, 1)
        self.conv2_2 = Conv(128, 128, 3, 1, 1)
        self.max_pool2 = MaxPool(2, 2)

        self.conv3_1 = Conv(128, 256, 3, 1, 1)
        self.conv3_2 = Conv(256, 256, 3, 1, 1)
        self.conv3_3 = Conv(256, 256, 3, 1, 1)
        self.max_pool3 = MaxPool(2, 2)

        self.conv4_1 = Conv(256, 512, 3, 1, 1)
        self.conv4_2 = Conv(512, 512, 3, 1, 1)
        self.conv4_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool4 = MaxPool(2, 2)

        self.conv5_1 = Conv(512, 512, 3, 1, 1)
        self.conv5_2 = Conv(512, 512, 3, 1, 1)
        self.conv5_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool5 = MaxPool(2, 2)  # 这里的输出 7x7x512

        self.conv6_1 = Conv(512, 512, 3, 1, 1)
        self.conv6_2 = Conv(512, 512, 3, 1, 1)
        self.conv6_3 = Conv(512, 512, 3, 1, 1)

        self.fc1 = nn.Linear(7 * 7 * 512, 4096)
        self.fc2 = nn.Linear(4096, 4096)
        self.fc3 = nn.Linear(4096, self.class_num)

        self.flat = nn.Flatten()
        self.relu = nn.ReLU()

    def forward(self, x):
        x = self.conv1_1(x)  #
        x = self.conv1_2(x)  #
        x = self.max_pool1(x)  # 1x64x112x112

        x = self.conv2_1(x)
        x = self.conv2_2(x)
        x = self.max_pool2(x)

        x = self.conv3_1(x)
        x = self.conv3_2(x)
        x = self.conv3_3(x)
        x = self.max_pool3(x)

        x = self.conv4_1(x)
        x = self.conv4_2(x)
        x = self.conv4_3(x)
        x = self.max_pool4(x)

        x = self.conv5_1(x)
        x = self.conv5_2(x)
        x = self.conv5_3(x)
        x = self.max_pool5(x)  # 512x7x7

        x = self.conv6_1(x)  # 加一层有效，加3层有效，要靠试验。感觉欠拟合
        x = self.conv6_2(x)
        x = self.conv6_3(x)

        x = self.flat(x)
        x = self.fc1(x)
        x = self.relu(x)

        x = self.fc2(x)
        x = self.relu(x)

        out = self.fc3(x)
        return out


class Vgg16_Sub(nn.Module):
    def __init__(self, class_num=10):
        super().__init__()
        self.class_num = class_num

        self.conv1_1 = Conv(3, 64, 3, 1, 1)
        self.conv1_2 = Conv(64, 64, 3, 1, 1)
        self.max_pool1 = MaxPool(2, 2)

        self.conv2_1 = Conv(64, 128, 3, 1, 1)
        self.conv2_2 = Conv(128, 128, 3, 1, 1)
        self.max_pool2 = MaxPool(2, 2)

        self.conv3_1 = Conv(128, 256, 3, 1, 1)
        self.conv3_2 = Conv(256, 256, 3, 1, 1)
        self.conv3_3 = Conv(256, 256, 3, 1, 1)
        self.max_pool3 = MaxPool(2, 2)

        self.conv4_1 = Conv(256, 512, 3, 1, 1)
        self.conv4_2 = Conv(512, 512, 3, 1, 1)
        self.conv4_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool4 = MaxPool(2, 2)

        self.conv4_1x1 = Conv(512, 256, 1, 1)
        # self.conv5_1 = Conv(512, 512, 3, 1, 1)
        # self.conv5_2 = Conv(512, 512, 3, 1, 1)
        # self.conv5_3 = Conv(512, 512, 3, 1, 1)
        # self.max_pool5 = MaxPool(2, 2)  # 这里的输出 7x7x512

        self.fc1 = nn.Linear(14 * 14 * 256, 4096)
        self.fc2 = nn.Linear(4096, 4096)
        self.fc3 = nn.Linear(4096, self.class_num)

        self.flat = nn.Flatten()
        self.relu = nn.ReLU()

    def forward(self, x):
        x = self.conv1_1(x)  #
        x = self.conv1_2(x)  #
        x = self.max_pool1(x)  # 1x64x112x112

        x = self.conv2_1(x)
        x = self.conv2_2(x)
        x = self.max_pool2(x)

        x = self.conv3_1(x)
        x = self.conv3_2(x)
        x = self.conv3_3(x)
        x = self.max_pool3(x)

        x = self.conv4_1(x)
        x = self.conv4_2(x)
        x = self.conv4_3(x)
        x = self.max_pool4(x)  # 256 x 14 x 14

        x = self.conv4_1x1(x)  # 增加1个1x1卷积，用来减少进入全连接的参数量
        # x = self.conv5_1(x)
        # x = self.conv5_2(x)
        # x = self.conv5_3(x)
        # x = self.max_pool5(x) # 512x7x7

        x = self.flat(x)
        x = self.fc1(x)
        x = self.relu(x)

        x = self.fc2(x)
        x = self.relu(x)

        out = self.fc3(x)
        return out


class Vgg16NOTFC(nn.Module):
    def __init__(self, class_num=10):
        super().__init__()
        self.class_num = class_num

        self.conv1_1 = Conv(3, 64, 3, 1, 1)
        self.conv1_2 = Conv(64, 64, 3, 1, 1)
        self.max_pool1 = MaxPool(2, 2)

        self.conv2_1 = Conv(64, 128, 3, 1, 1)
        self.conv2_2 = Conv(128, 128, 3, 1, 1)
        self.max_pool2 = MaxPool(2, 2)

        self.conv3_1 = Conv(128, 256, 3, 1, 1)
        self.conv3_2 = Conv(256, 256, 3, 1, 1)
        self.conv3_3 = Conv(256, 256, 3, 1, 1)
        self.max_pool3 = MaxPool(2, 2)

        self.conv4_1 = Conv(256, 512, 3, 1, 1)
        self.conv4_2 = Conv(512, 512, 3, 1, 1)
        self.conv4_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool4 = MaxPool(2, 2)

        self.conv5_1 = Conv(512, 512, 3, 1, 1)
        self.conv5_2 = Conv(512, 512, 3, 1, 1)
        self.conv5_3 = Conv(512, 512, 3, 1, 1)
        self.max_pool5 = MaxPool(2, 2)  # 这里的输出

        self.global_pool = nn.AdaptiveAvgPool2d(1)  # 一般说来就在7x7左右
        self.global_conv1 = Conv(512, 256, 1, 1)
        self.global_conv2 = Conv(256, 128, 1, 1)
        # 最后一层的输出，是否需要relu
        # self.out_conv3 = Conv(128, self.class_num, 1, 1, is_act=False)
        self.out_conv3 = nn.Conv2d(128, self.class_num, 1, 1)

    def forward(self, x):
        x = self.conv1_1(x)  #
        x = self.conv1_2(x)  #
        x = self.max_pool1(x)  # 1x64x112x112

        x = self.conv2_1(x)
        x = self.conv2_2(x)
        x = self.max_pool2(x)

        x = self.conv3_1(x)
        x = self.conv3_2(x)
        x = self.conv3_3(x)
        x = self.max_pool3(x)

        x = self.conv4_1(x)
        x = self.conv4_2(x)
        x = self.conv4_3(x)
        x = self.max_pool4(x)

        x = self.conv5_1(x)
        x = self.conv5_2(x)
        x = self.conv5_3(x)
        x = self.max_pool5(x)

        x = self.global_pool(x)
        x = self.global_conv1(x)

        x = self.global_conv2(x)
        out = self.out_conv3(x)  # 1x128x1x1 , 1, 10
        out = torch.reshape(out, (out.shape[0], -1))  # torch.xx代表一个数学运算

        return out


if __name__ == "__main__":
    device = "cuda:0" if torch.cuda.is_available() else "cpu"
    model = Vgg16NOTFC(class_num=10).to(device)
    # model = Vgg16(class_num=10).to(device)
    print(model)
    test_x = torch.randn([1, 3, 224, 224]).to(device)
    # 模拟前向传播
    out = model(test_x)
    print(out.shape)
