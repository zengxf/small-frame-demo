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


# 继承torch.dataset 这个库
class MyDataSet(Dataset):
    def __init__(self, path, device, is_train=True):
        """用来做一些初始化"""
        self.all_imgs = os.listdir(path)
        random.shuffle(self.all_imgs)
        cout_all = len(self.all_imgs)
        train_num = int(cout_all * 0.8)
        if is_train:
            self.all_imgs = self.all_imgs[0:train_num]
        else:
            self.all_imgs = self.all_imgs[train_num:cout_all]
        self.root = path
        self.device = device

    def _get_label_from_image_name(self, file_name):
        # 0_0.png, ["0","0.png"]
        label = int(file_name.split("_")[0])
        return torch.tensor(label, dtype=torch.float32).to(self.device)

    def _get_image_bgr(self, file_name):
        # D:\root\1.jpg
        # D:/root/1.jpg
        # D:\root/1.jpg
        image = cv2.imread(f"{self.root}\\{file_name}")
        gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        # 加一个letterbox的尺寸统一化
        resize_gray_image = cv2.resize(gray_image, (28, 28))
        norm_gray_image = resize_gray_image / 255.0
        return torch.tensor(norm_gray_image, dtype=torch.float32).to(self.device)

    def __getitem__(self, item):
        """根据整个数据集中的数量，比如100个
        item，pytorch会随机生成一个数字[1,100]之中，根据这个下标去返回训练数据
        x,y
        """
        file_name = self.all_imgs[item]
        gray_img = self._get_image_bgr(file_name)
        label_index = self._get_label_from_image_name(file_name)
        return gray_img, label_index

    def __len__(self):
        """返回整个数据集的数量"""
        return len(self.all_imgs)


""""
 for x, y in     DataLoader(MyDataSet):
      pre_y = model(x)
      total_loss = loss(y,pre_y)
      total_loss.backward()
      

"""

if __name__ == "__main__":
    path = r"../training_img"
    device = "cuda:0" if torch.cuda.is_available() else "cpu"
    train_dataset = MyDataSet(path, device)
    batch_size = 2
    train_dataloader = torch.utils.data.DataLoader(
        dataset=train_dataset,
        batch_size=batch_size,
        shuffle=True
    )

    val_dataset = MyDataSet(path, device,is_train=False)
    val_dataloader = torch.utils.data.DataLoader(
        dataset=val_dataset,
        batch_size=batch_size,
        shuffle=False
    )

    for x, y in val_dataloader:
        print(x.shape)
        print(y.shape)
