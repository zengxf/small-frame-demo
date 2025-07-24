import torch
import torchvision
from tqdm import tqdm
import cv2
# 供dataset和dataloader使用的
from torch.utils.data.dataset import Dataset
from torch.utils.data import DataLoader
# 创建一个自己的dataset
import os
import random
from torchvision import transforms

"""
root 主目录
train
     分类的名称
        ....jpg
        ....jpg
        ....jpg
     分类的名称
        ....jpg
        ....jpg
        ....jpg
     分类的名称
        ....jpg
        ....jpg
        ....jpg
val
    
"""

transforms = transforms.Compose(
    [
        transforms.Resize([224, 224]),
        transforms.ToTensor(),
        transforms.Grayscale()
    ]
)
"""
先划分训练集和验证集
"""
train_dataset = torchvision.datasets.ImageFolder(
    root=r"D:\DLAI\dataSet\flower_photos",
    transform=transforms
) # 本身是不绑定在gpu上的
test_dataset = torchvision.datasets.ImageFolder(
    root=r"D:\DLAI\dataSet\flower_photos",
    transform=transforms
)
device = "cuda:0" if torch.cuda.is_available() else "cpu"
for x, y in train_dataset:
    x = x.to(device)
    y = y.to(device)
    print(x.shape)
    print(y.shape)