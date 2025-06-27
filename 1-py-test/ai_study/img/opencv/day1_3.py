import cv2
import random
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)


"""
1。如何使用opencv去做检测任务
    二值化：将一个BGR图像转换成灰度图，并且只有2-3种值。将图像的每个像素进行2分图
    将图像的梯度到最大。目标区域应该是白色的
    1.1 自定义的方法
    1.2 调opencv中的函数
"""
"""
conf_ths：超参数。人为设定的。
"""

"""
可以根据自己的需要，将图像划分成不同重点的区域。
自定义二值化
"""


def custom_thresholds(gray_img, min_conf_ths, max_conf_ths):
    h, w = gray_img.shape[:2]
    for i in range(h):
        for j in range(w):
            px = gray_img[i, j]  # 取某个坐标点的像素值
            if px < min_conf_ths:
                gray_img[i, j] = 0
            elif px >= min_conf_ths and px < max_conf_ths:
                gray_img[i, j] = 128
            else:
                gray_img[i, j] = 255
    return gray_img


path = r"D:\zhigu-ai-27-note\untitled2\opencv32\1.png"
img = cv2.imread(path, 0)

img_old = img.copy()  # 深copy

show(img_old, 'old')
# img2thresholds = custom_thresholds(img, 40, 150)
# show(img2thresholds, 'img2thresholds1')
# # 黑白交换
# img2thresholds = cv2.bitwise_not(img2thresholds, img2thresholds)
# show(img2thresholds, 'img2thresholds2')
"""
cv2.THRESH_BINARY ,超过127的255，否则为0
cv2.THRESH_BINARY_INV, 超过127的为0，否则为255
cv2.THRESH_TRUNC，超过127的为127，否则不变
cv2.THRESH_TOZERO, 小于127的为0，否则不变
cv2.THRESH_TOZERO_INV, 小于127的为不变，否则为0 
"""
ret, ths1_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_BINARY)
show(ths1_img, 'ths1_img')

ret, ths2_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_BINARY_INV)
show(ths2_img, 'ths2_img')

ret, ths3_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_TRUNC)
show(ths3_img, 'ths3_img')

ret, ths4_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_TOZERO)
show(ths4_img, 'ths4_img')

ret, ths5_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_TOZERO_INV)
show(ths5_img, 'ths5_img')

# 自适应二值化
# ksize
adaada_img = cv2.adaptiveThreshold(img_old.copy(), 127, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 0)
show(adaada_img,'adaada_img')
