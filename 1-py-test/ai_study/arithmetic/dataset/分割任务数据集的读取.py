from torch.utils.data import Dataset, DataLoader
import cv2
import numpy as np
import torch
from PIL import Image
import os
import torchvision


class MaskDataSet(Dataset):
    def __init__(self, image_path, mask_path, device="cpu", size=(224, 224), transform=None):
        super(MaskDataSet, self).__init__()
        self.device = device
        self.size = size
        self.images = [f for f in os.listdir(image_path) if f.endswith('.png') or f.endswith('.jpg')]
        self.masks = [f for f in os.listdir(mask_path) if f.endswith('.png') or f.endswith('.jpg')]
        # image, mask图，应该一一对应。开源数据，有一些是不对应的。
        self.image_path = image_path
        self.mask_path = mask_path
        self.transform = transform
        self.device = device

    def __len__(self):
        return len(self.images)

    def __getitem__(self, item):
        # 原图和mask图要对应
        img_name = self.images[item]
        orgin_img_path = os.path.join(self.image_path, img_name)

        mask_img_path = os.path.join(self.mask_path, img_name.replace('.jpg', '.png'))
        if not os.path.exists(mask_img_path):
            raise (f"{mask_img_path} not found")
        # 分割标签如果是255，也需要归一化。 y值要有类别
        orginImage = cv2.imread(orgin_img_path) / 255.0 # 读原图归一化
        maskImage = cv2.imread(mask_img_path, 0)
        maskImage = cv2.cvtColor(maskImage, cv2.COLOR_GRAY2BGR) / 255.0
        # 如果是一个类别，全白，需要归一化。如果是多个类别，全黑图，不能归一化。如果看到彩色图，用彩色对应值域替换成对应的类别值
        orginImage = cv2.resize(orginImage, self.size)
        maskImage = cv2.resize(maskImage, self.size)
        # hwc, chw
        orginImage = torch.tensor(orginImage).transpose(0, -1)

        # hw ->1hw
        maskImage = torch.tensor(maskImage).transpose(0, -1)  # .unsqueeze(0)

        return orginImage.to(self.device), maskImage.to(self.device)


if __name__ == "__main__":
    image_path = r"F:\data_source\3D_print_data\mask_data\images"
    mask_path = r"F:\data_source\3D_print_data\mask_data\masks"
    train_dataset = MaskDataSet(image_path, mask_path)

    train_loader = torch.utils.data.DataLoader(
        train_dataset,
        batch_size=2,
        shuffle=True,
        num_workers=1  # 必须写__name__ == "__main__"
    )

    for x1, y1 in train_loader:
        # model
        pass
