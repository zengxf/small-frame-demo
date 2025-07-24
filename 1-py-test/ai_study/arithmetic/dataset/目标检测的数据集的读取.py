import cv2
import numpy as np
import torch
from PIL import Image
import os
import torchvision
from torch.utils.data import Dataset, DataLoader

"""
目标检测的
image
    train
        ....jpg
    val
        ....jpg
labels
    train
        ....txt
            1.txt --2个box
                label内容:
                    image_idex, label,cx,cy,w,h
                    image_idex, label,cx,cy,w,h
            2.txt --1个box
                label内容:
                    image_idex, label,cx,cy,w,h
    val
        ....txt
"""


# 输入的 x:image  2张图片 2 x (640x640x3)
# 输出   y: label, 3x6 无法获取box对应的image是那一张。需要对box再次处理，使用第1个index的位置，是那一张图片

def voc2yolo(voc_boxes, ih, iw):
    if isinstance(voc_boxes, list):
        voc_boxes = np.array(voc_boxes)
    yolo_boxes = voc_boxes.astype(float).copy()
    yolo_boxes[..., 1] = (voc_boxes[..., 1] + voc_boxes[..., 3]) / 2 / iw
    yolo_boxes[..., 2] = (voc_boxes[..., 2] + voc_boxes[..., 4]) / 2 / ih
    yolo_boxes[..., 3] = (voc_boxes[..., 3] - voc_boxes[..., 1]) / iw
    yolo_boxes[..., 4] = (voc_boxes[..., 4] - voc_boxes[..., 2]) / ih
    # 将结果转为浮点数，保留6位小数
    yolo_boxes = np.around(yolo_boxes, decimals=6)
    return yolo_boxes


# yolo转成voc
def yolo2voc(yolo_boxes, ih, iw):
    if isinstance(yolo_boxes, list):
        yolo_boxes = np.array(yolo_boxes)
    voc_boxes = yolo_boxes.copy()
    voc_boxes[..., 1] = yolo_boxes[..., 1] * iw - yolo_boxes[..., 3] * iw / 2
    voc_boxes[..., 2] = yolo_boxes[..., 2] * ih - yolo_boxes[..., 4] * ih / 2
    voc_boxes[..., 3] = yolo_boxes[..., 1] * iw + yolo_boxes[..., 3] * iw / 2
    voc_boxes[..., 4] = yolo_boxes[..., 2] * ih + yolo_boxes[..., 4] * ih / 2
    # 将坐标转换为整数
    voc_boxes = voc_boxes.astype(int)
    return voc_boxes


def letterbox(image, size=(640, 640), boxes=None):
    ih, iw = image.shape[:2]
    w, h = size

    if ih == h and iw == w:
        return image, boxes, 1, 0, 0, 0, 0  # image, boxes, ratio, padT, padB, padL, padR

    ratio = min(w / iw, h / ih)

    new_w = int(iw * ratio)
    new_h = int(ih * ratio)

    dw = (w - new_w) // 2
    dh = (h - new_h) // 2

    scale_img = cv2.resize(image, [new_w, new_h])

    padded_image = cv2.copyMakeBorder(scale_img, dh, h - dh - new_h, dw, w - dw - new_w, cv2.BORDER_CONSTANT,
                                      value=[114, 114, 114])
    voc_boxes = yolo2voc(boxes, ih, iw)
    padded_boxes = voc_boxes.copy()
    padded_boxes[:, 1] = voc_boxes[:, 1] * ratio + dw
    padded_boxes[:, 2] = voc_boxes[:, 2] * ratio + dh
    padded_boxes[:, 3] = voc_boxes[:, 3] * ratio + dw
    padded_boxes[:, 4] = voc_boxes[:, 4] * ratio + dh
    padded_boxes = voc2yolo(padded_boxes, h, w)
    return padded_image, padded_boxes, ratio, dh, h - dh - new_h, dw, w - dw - new_w


class BoxDataSet(Dataset):
    def __init__(self, root_path, device, size=(640, 640), is_train=True):
        """一般是根据图片去找label"""

        name = "train" if is_train else "val"

        self.imgs_dir = f"{root_path}\\images\\{name}"
        self.labels_dir = f"{root_path}\\labels\\{name}"
        self.all_imgs = os.listdir(self.imgs_dir)
        self.size = size
        self.device = device

    def _get_imgs_and_labels(self, file_name):
        """
        读图片，归一化，转chw，还要变tensor，同时还要letterbox，让它尺寸统一，box需要调整
        :return:
        """
        bgr_img = cv2.imread(f"{self.imgs_dir}\\{file_name}")  # xx.xx.jpg

        #
        if len(file_name.split('.')) > 2:
            raise Exception("文件名存在不合法的情况")
        file_jpg_name = file_name.split('.')[0]
        label_name = f"{self.labels_dir}\\{file_jpg_name}.txt"
        if not os.path.exists(label_name):
            """
            如果有图片，但是没有打标签，就会将这张图片当前背景；神经网络，会努力将这张图片当成不是目标。
            测试的时候误检：不打标签，将这些图片拿来训练。就会减少误检。
            """
            boxes = [0] * 5
        else:
            # 这里就调letterbox
            boxes = self._get_label(label_name)
        boxes_numpy = np.array(boxes, dtype=np.float32)
        l_img, l_boxes, _, _, _, _, _ = letterbox(bgr_img, size=self.size, boxes=boxes_numpy)
        l_rgb_img = cv2.cvtColor(l_img, cv2.COLOR_BGR2RGB)
        l_rgb_img = l_rgb_img / 255.0
        l_rgb_img = np.transpose(l_rgb_img, [2, 0, 1])  # 0,1,2, hwc
        # l_boxes, Nx5
        l_rgb_img = torch.tensor(l_rgb_img, dtype=torch.float32).to(self.device)
        l_boxes = torch.tensor(l_boxes, dtype=torch.float32).to(self.device)

        # l_rgb_img = torch.tensor(l_rgb_img, dtype=torch.float32)#.to(self.device)
        # l_boxes = torch.tensor(l_boxes, dtype=torch.float32)#.to(self.device)
        return l_rgb_img, l_boxes

    def _get_label(self, label_file_name):
        with open(label_file_name, encoding='utf-8') as f:
            ctn = f.readlines()
            # 需要将字符串转成float
            # label,cx,cy,w,h
            # Nx6
            result = []
            for row in ctn:
                float_row = [0] + [float(x.strip()) for x in row.split(' ')]
                result.append(float_row)
            return result

    def __len__(self):
        return len(self.all_imgs)

    def __getitem__(self, item):
        image_name = self.all_imgs[item]
        return self._get_imgs_and_labels(image_name)

    @staticmethod
    def collate_fn(batch):  # batch,[[img,[box]],[img,[box]]] pytorch这个框架控制传入的时候
        """
        batch必须是tensor
        :return: zip把image和box 分别放在一起
        """
        img, box = zip(*batch) # batch, [img, box], [img, box] ->zip, [[img],[img]], [box,box]]
        for i, l in enumerate(box): # 0, box1; 1, box2 -> box2 2x6
            l[..., 0] = i
        img = torch.stack(img, 0)  # 维度都是一样. 2x 3x640x640
        last_box = torch.cat(box, 0)  # 只能某一个维度不一样。拼接(相加,cat,add)
        return img, last_box # N x 6


if __name__ == "__main__":
    path = r"F:\2025\5_fishingdetection\data"
    device = "cuda:0" if torch.cuda.is_available() else "cpu"
    train_dataset = BoxDataSet(root_path=path, is_train=True, device=device)  # 创建一个 dataset
    train_dataloader = DataLoader(
        dataset=train_dataset,
        batch_size=2,
        shuffle=True,
        collate_fn=BoxDataSet.collate_fn
    )
    for image, label in train_dataloader:
        pass
