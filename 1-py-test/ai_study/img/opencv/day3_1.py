import cv2
import random
import numpy as np

"""
1. 灰度图-》平滑-》形态学

xy：坐标信息
xy对应的RGB值（灰度值）：特征值

特征：
opencv的优势，很够很好的去解释的几何特征和语义特征。
几何特征，XY坐标进行的一个组合，而且组合满足某种几何规则，宽高比，周长，弧长，面积，角度。。。
语义特征，几何形状对应的特征值

深度学习：
有大量的语义特征


高通滤波（边缘检测）【灰度图，一般是二值化后图】：
sobel算子【算子，某种图形学识别的计算方法】

图像梯度的计算，拿到图像的物体的边缘，提供一些几何形状

边缘增强（锐化增强），强化了梯度，对于目标的检出，是有益的

"""


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)


path = r"D:\zhigu-ai-27-note\untitled2\opencv32\1.png"
img = cv2.imread(path, 1)
img_bgr = img.copy()

img = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
img = cv2.GaussianBlur(img, (3, 3), 0)
imgOld = img.copy()
# # 选做
# ret, ths1_img = cv2.threshold(img, 40, 255, cv2.THRESH_BINARY)
#
# # 选做
# kernel = np.ones((3, 3), np.uint8)
# erode_img = cv2.erode(ths1_img, kernel, iterations=2)
# dilate_img = cv2.dilate(erode_img, kernel, iterations=2)

x = cv2.Sobel(img, -1, 1, 0)
show(x,'x1')
x = cv2.convertScaleAbs(x) # uint8用的abs
show(x,'x2')

y = cv2.Sobel(img, -1, 0, 1)
show(y,'y1')

y = cv2.convertScaleAbs(y) # uint8用的abs
show(y,'y2')

addWeights = cv2.addWeighted(x,0.5, y, 0.5, 0)
show(addWeights,'add')

bian_yuan = cv2.addWeighted(imgOld,0.5, addWeights, 0.5, 0)
show(bian_yuan, ' bian_yuan')

addWeights_bgr = cv2.cvtColor(addWeights,cv2.COLOR_GRAY2BGR)
img_bgr_bianyuan = cv2.addWeighted(img_bgr,0.6, addWeights_bgr,0.4, 0)
show(img_bgr_bianyuan,'img_bgr_bianyuan')


scharr_img_x = cv2.convertScaleAbs(cv2.Scharr(imgOld,-1,1, 0))
scharr_img_y = cv2.convertScaleAbs(cv2.Scharr(imgOld,-1,0, 1))
addWeights_scharr = cv2.addWeighted(scharr_img_x,0.5,scharr_img_y,0.5, 0)
show(addWeights_scharr,'addWeights_scharr')

