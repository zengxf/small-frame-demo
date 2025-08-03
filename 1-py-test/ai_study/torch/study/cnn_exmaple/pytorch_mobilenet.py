import torch
import torchvision
from torchvision import transforms
from torch.utils.data import DataLoader
from torchvision import datasets

"""
pretrained = True，会自动下载在imagenet上训练过的权重做为你的数据的初始

"""
# 模型结构
model = torchvision.models.mobilenet_v2(pretrained=False)
# 把权重加载到模型上面
model.load_state_dict(torch.load('./mobilenet_v2-b0353104.pth'))
print(model)

