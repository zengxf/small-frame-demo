import torch.nn as nn
import torch
import torchvision
from torchvision import transforms
import logging

isDebug = False

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    filename='%(asctime)s.txt'
)
logger = logging.getLogger(__name__)


# 自定义算子
class InceptionV1(nn.Module):
    def __init__(self,
                 in_channel,
                 reduce_1x1_outch,
                 reduce_3x3_outch,
                 outch_3x3,
                 reduce_5x5_outch,
                 outch_5x5,
                 pool_proj,
                 ):
        super().__init__()
        # 1x1卷积
        self.reduce_1x1_outch = nn.Conv2d(
            in_channel, reduce_1x1_outch,
            kernel_size=(1, 1),
            stride=(1, 1)
        )

        self.reduce_3x3_outch = nn.Conv2d(
            in_channel, reduce_3x3_outch,
            kernel_size=(1, 1),
            stride=(1, 1)
        )
        self.outch_3x3 = nn.Conv2d(
            reduce_3x3_outch, outch_3x3,
            kernel_size=(3, 3),
            stride=(1, 1),
            padding=1
        )

        self.reduce_5x5_outch = nn.Conv2d(
            in_channel, reduce_5x5_outch,
            kernel_size=(1, 1),
            stride=(1, 1)
        )
        self.outch_5x5 = nn.Conv2d(
            reduce_5x5_outch, outch_5x5,
            kernel_size=(5, 5),
            stride=(1, 1),
            padding=2
        )

        self.pool_proj_maxpool = nn.MaxPool2d(
            kernel_size=(3, 3),
            stride=(1, 1)
        )
        self.pool_proj = nn.Conv2d(
            in_channel, pool_proj,
            kernel_size=(1, 1),
            stride=(1, 1),
            padding=1
        )
        self.relu = nn.ReLU()

    def forward(self, x):
        x1 = self.relu(self.reduce_1x1_outch(x))

        x2 = self.relu(self.reduce_3x3_outch(x))
        x2 = self.relu(self.outch_3x3(x2))  # 1x128x28x28

        x3 = self.relu(self.reduce_5x5_outch(x))
        x3 = self.relu(self.outch_5x5(x3))  # 1x32x26x26

        x4 = self.pool_proj_maxpool(x)
        x4 = self.relu(self.pool_proj(x4))  # 1x32x28x28
        # b,c,h,w
        return torch.concat([x1, x2, x3, x4], dim=1)


if isDebug:
    x = torch.randn(192, 28, 28)  # 模拟的上一层的输入
    net = InceptionV1(192, 64, 96, 128, 16, 32, 32)  # 调model
    net(x)  # 前向传播


class AugHead(nn.Module):
    def __init__(self, in_ch, out_1x1, class_num):
        super().__init__()
        self.avgpool = nn.AvgPool2d(kernel_size=5, stride=3)
        self.conv1x1 = nn.Conv2d(in_ch, out_1x1, kernel_size=(1, 1))
        self.fc1 = nn.Linear(2048, 1024)  # 4096,2048,1024,512,256,128
        self.fc2 = nn.Linear(1024, class_num)
        self.relu = nn.ReLU()
        self.sigmoid = nn.Sigmoid()
        self.flat = nn.Flatten()

    def forward(self, x):  # 1x512x14x14
        x = self.avgpool(x)
        x = self.conv1x1(x)
        x = self.relu(x)

        x = self.flat(x)  # 1x2048
        x = self.fc1(x)
        x = self.relu(x)

        x = self.fc2(x)
        # x = self.sigmoid(x)

        return x
        # return self.sigmoid(self.fc2(self.relu(self.fc1(self.relu(self.conv1x1(self.avgpool(x)))))))


if isDebug:
    x = torch.randn(192, 28, 28)  # 模拟的上一层的输入
    net = AugHead(192, 64, 5)  # 调model
    net(x)  # 前向传播


class GoogLeNetV1(nn.Module):
    def __init__(self, in_ch=3, num_class=10):
        super().__init__()

        self.num_class = num_class

        self.conv1 = nn.Conv2d(in_ch, 64, kernel_size=(7, 7), stride=(2, 2), padding=3)
        self.maxpool1 = nn.MaxPool2d(kernel_size=(3, 3), stride=(2, 2), padding=1)

        self.conv2 = nn.Conv2d(64, 192, kernel_size=(3, 3), stride=(1, 1), padding=1)
        self.maxpool2 = nn.MaxPool2d(kernel_size=(3, 3), stride=(2, 2), padding=1)

        self.inception3a = InceptionV1(192, 64, 96, 128, 16, 32, 32)
        self.inception3b = InceptionV1(256, 128, 128, 192, 32, 96, 64)
        self.maxpool3 = nn.MaxPool2d(kernel_size=(3, 3), stride=(2, 2), padding=1)

        self.inception4a = InceptionV1(480, 192, 96, 208, 16, 48, 64)
        self.inception4b = InceptionV1(512, 160, 112, 224, 24, 64, 64)
        self.inception4c = InceptionV1(512, 128, 128, 256, 24, 64, 64)
        self.inception4d = InceptionV1(512, 112, 144, 288, 32, 64, 64)
        self.inception4e = InceptionV1(528, 256, 160, 320, 32, 128, 128)
        self.maxpool4 = nn.MaxPool2d(kernel_size=(3, 3), stride=(2, 2), padding=1)

        self.inception5a = InceptionV1(832, 256, 160, 320, 32, 128, 128)
        self.inception5b = InceptionV1(832, 384, 192, 384, 48, 128, 128)
        self.poolavg = nn.AvgPool2d(kernel_size=(7, 7), stride=(1, 1))

        self.dropout = nn.Dropout(0.5)
        self.fc1 = nn.Linear(1024, self.num_class)
        self.relu = nn.ReLU()
        self.flat = nn.Flatten()
        self.sigmoid = nn.Sigmoid()

        self.head1 = AugHead(512, 128, self.num_class)
        self.head2 = AugHead(528, 128, self.num_class)

    def forward(self, x):
        x = self.relu(self.conv1(x))
        x = self.maxpool1(x)

        x = self.relu(self.conv2(x))
        x = self.maxpool2(x)

        x = self.inception3a(x)
        x = self.inception3b(x)
        x = self.maxpool3(x)

        x = self.inception4a(x)

        out1 = self.head1(x)

        x = self.inception4b(x)

        x = self.inception4c(x)
        x = self.inception4d(x)

        out2 = self.head2(x)

        x = self.inception4e(x)
        x = self.maxpool4(x)

        x = self.inception5a(x)
        x = self.inception5b(x)
        x = self.poolavg(x)  # 1x1024x1x1

        x = self.flat(x)
        x = self.dropout(x)  # b x 1024
        # x = self.sigmoid(self.fc1(x))
        x = self.fc1(x)
        return x, out2, out1


if __name__ == "__main__":
    """
    1. 了解每一种数据的数据量
       难点1： 数据不平衡
             删除一些OK的数据
             
       CX， PW 需要做数据增强
       
       难点2：图像质量不高（成像条件较差）、图像有过曝的情况
       难点3：有焊点与焊点不明显，只有些许面积上的差异
       难点4：引线未压扁，需要有深度信息，2D图像较难知识到深度信息
       难点5：焊渣，焊点污渍较重，影响成像效果
       难点6：过焊，凹进去或者凸出来，深度信息不明显，并且与焊点污渍相似
       
        CX: 31
        HDZ: 339
        OK: 4686
        PW: 88
        XH1: 564
        XH2: 376
        XH3: 701
    
    2. 划分训练集和验证集（测试集）
    
    3. 用pytorch ImageFolder去读图片
         先构建dataset，再构建dataloader
         
    4. 训练，拿一个基线
    
    5. 做好每次实验的数据记录
    
    任务：
    1. 先拿基线【默认参数】数据，关注acc。对比vgg、googlenetv1的效果
    2. 解决数据不平衡的问题，对比acc
    3. 数据增强、学习率的变化的影响
    4. 每次50/100结果对比
         
    """
    # rnd = torch.randn(1, 3, 224, 224)  # bchw
    # #
    # net = GoogleNetV1(3,10)
    # result = net(rnd)

    # rnd = torch.randn(1,3,224,224)
    device = "cuda:0"
    device = "cpu"

    net = GoogLeNetV1(in_ch=3, num_class=7).to(device)

    transforms = transforms.Compose(
        [
            transforms.Resize([224, 224]),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
        ]
    )

    train_dataset = torchvision.datasets.ImageFolder(
        root=r"D:\Data\Test\img\flower_photos\_mini\train",
        transform=transforms
    )
    test_dataset = torchvision.datasets.ImageFolder(
        root=r"D:\Data\Test\img\flower_photos\_mini\val",
        transform=transforms
    )
    batchSize = 10
    train_loader = torch.utils.data.DataLoader(
        train_dataset,
        batch_size=batchSize,
        shuffle=True
    )  # 进行小批量梯度下降
    val_loader = torch.utils.data.DataLoader(
        test_dataset,
        batch_size=batchSize,
        shuffle=False
    )

    # 3. 设置优化器
    optim = torch.optim.Adam(net.parameters(), lr=1e-4)

    # 4. 设置损失函数
    criterion = nn.CrossEntropyLoss()

    EPOCHS = 2  # epoch的次数，一共要训练多个轮

    # 5. 循环dataloader数据
    for epoch in range(EPOCHS):
        net.train(True)
        # 6. 遍历生成器中的train_loader来进行训练
        for input_x, label in train_loader:
            input_x = input_x.to(device)
            label = label.to(device)
            # 前向传播 x,out2,out1
            pre1, pre2, pre3 = net(input_x)

            loss1 = criterion(pre1, label)
            loss2 = criterion(pre2, label)
            loss3 = criterion(pre3, label)

            total_loss = loss1 + 0.3 * loss2 + 0.3 * loss3
            pred = torch.argmax(pre1, dim=1)
            accuracy = torch.sum(pred == label) / label.shape[0]
            desc = "train.[{}/{}] loss: {:.4f}, Acc: {:.2f}".format(
                epoch, epoch, total_loss.item(), accuracy.item()
            )
            logger.info(desc)
            logger.error(desc)

            total_loss.backward()
            # 根据优化器进行w的更新。梯度下降
            optim.step()

        # print(desc)

        net.eval()
        for input_x, label in val_loader:
            input_x = input_x.to(device)
            label = label.to(device)
            # 前向传播 x,out2,out1
            pre1, pre2, pre3 = net(input_x)

            loss1 = criterion(pre1, label)

        pred = torch.argmax(pre1, dim=1)
        accuracy = torch.sum(pred == label) / label.shape[0]
        desc = "val.[{}/{}] loss: {:.4f}, Acc: {:.2f}".format(
            epoch, epoch, loss1.item(), accuracy.item()
        )
        print(desc)
        torch.save(net, f"{epoch}.pth")
