import torch
import torch.nn as nn
import torch.nn.functional as F
import numpy as np


class ResBlock(nn.Module):
    def __init__(self, in_channels, out_channels):
        super(ResBlock, self).__init__()
        self.conv1 = nn.Conv2d(in_channels, out_channels, kernel_size=3, padding=1)
        self.bn1 = nn.BatchNorm2d(out_channels)
        self.relu = nn.ReLU(inplace=True)
        self.conv2 = nn.Conv2d(out_channels, out_channels, kernel_size=3, padding=1)
        self.bn2 = nn.BatchNorm2d(out_channels)

        # 残差连接（输入和输出的通道数相同）
        self.shortcut = nn.Conv2d(in_channels, out_channels,
                                  kernel_size=1) if in_channels != out_channels else nn.Identity()

    def forward(self, x):
        identity = self.shortcut(x)
        out = self.conv1(x)
        out = self.bn1(out)
        out = self.relu(out)
        out = self.conv2(out)
        out = self.bn2(out)
        out += identity  # 残差连接
        out = self.relu(out)
        return out


class ResUNetSegmentation(nn.Module):
    def __init__(self):
        super(ResUNetSegmentation, self).__init__()

        # 编码器 (下采样)
        self.enc1 = ResBlock(3, 64)
        self.enc2 = ResBlock(64, 128)
        self.enc3 = ResBlock(128, 256)
        self.enc4 = ResBlock(256, 512)

        self.pool = nn.MaxPool2d(2)

        # 解码器 (上采样)
        self.upconv4 = nn.ConvTranspose2d(512, 256, kernel_size=2, stride=2)
        self.dec4 = ResBlock(512, 256)

        self.upconv3 = nn.ConvTranspose2d(256, 128, kernel_size=2, stride=2)
        self.dec3 = ResBlock(256, 128)

        self.upconv2 = nn.ConvTranspose2d(128, 64, kernel_size=2, stride=2)
        self.dec2 = ResBlock(128, 64)

        # 最终输出层 - 分割掩码
        self.final_conv = nn.Conv2d(64, 1, kernel_size=1)

    def forward(self, x):
        # 编码器
        enc1 = self.enc1(x)  # [B, 64, 480, 640]
        enc2 = self.enc2(self.pool(enc1))  # [B, 128, 240, 320]
        enc3 = self.enc3(self.pool(enc2))  # [B, 256, 120, 160]
        enc4 = self.enc4(self.pool(enc3))  # [B, 512, 60, 80]

        # 解码器
        dec4 = self.upconv4(enc4)  # [B, 256, 120, 160]
        dec4 = torch.cat((dec4, enc3), dim=1)
        dec4 = self.dec4(dec4)

        dec3 = self.upconv3(dec4)  # [B, 128, 240, 320]
        dec3 = torch.cat((dec3, enc2), dim=1)
        dec3 = self.dec3(dec3)

        dec2 = self.upconv2(dec3)  # [B, 64, 480, 640]
        dec2 = torch.cat((dec2, enc1), dim=1)
        dec2 = self.dec2(dec2)

        # 最终输出
        out = self.final_conv(dec2)  # [B, 1, 480, 640]
        return out


if __name__ == "__main__":
    pass
    model = ResUNetSegmentation()
    x = torch.randn(2, 1, 480, 640)
    result = model(x)
    print(result.shape)
