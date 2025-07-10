import os
import random

import cv2
import matplotlib.pyplot as plt

"""
评估某个数据集的整体灰度值的情况是怎样的？
看图暗不暗

如果训练集和验证集中的灰度分布差异过大。
1。验证集没选好，不能做为裁判
2。训练集中有脏数据（过亮，过暗的数据），人工筛选
"""

root = r"D:/Data/Test/img"
jpg_list = os.listdir(root)
random.shuffle(jpg_list)

imgs_b = []
imgs_g = []
imgs_r = []
for name in jpg_list[:100]:
    if not name.endswith("png"):
        continue
    jpg_name = f"{root}\\{name}"
    img = cv2.imread(jpg_name)
    b, g, r = cv2.split(img)
    imgs_b.append(b)
    imgs_g.append(g)
    imgs_r.append(r)


def calchist_for_gray(imgs, title=''):
    hist = cv2.calcHist(imgs, [0], None, [256], [30, 255])
    plt.plot(hist, color="r")
    # plt.savefig(f"{title}.jpg")
    plt.title(title)
    plt.show()


calchist_for_gray(imgs_b, 'b')
calchist_for_gray(imgs_g, 'g')
calchist_for_gray(imgs_r, 'r')
