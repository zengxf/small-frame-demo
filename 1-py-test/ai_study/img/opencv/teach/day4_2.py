import cv2
import random
import numpy as np


"""
1. 直方图分布评价的意义
2. 直方图均衡化，分gray，或者bgr图。能够加强对比度（原图像的高亮度值区域不能过多，会掉点）。一般模型训练会涨点
   区域直方图，会有所缓解直方图过亮问题。

"""
def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)

"""
opencv提供一些方法，将图像的质量提高。
太暗了，太亮了
"""
old = cv2.imread("./imgs/20230925144601.jpg",1)
old_copy = old.copy()

# 1. 粗放派
# old = ((old/255.0 * 1.2)*255).astype(np.uint8) # scale,随机 0.5-1.3
# show(old,'d')

# 2. gamma光线调整 gamma 值 (大于 1 亮化，小于 1 暗化)
# gamma = 0.5 # 0.5 - 1.5之间随机
# # 归一化到 [0, 1] 之间
# image_normalized = old_copy / 255.0
# # 使用 gamma 校正公式：output = input ^ (1/gamma)
# gamma_corrected = np.power(image_normalized, 1/gamma)
# # 将调整后的图像还原为 [0, 255] 范围
# gamma_corrected = np.uint8(gamma_corrected * 255)
# show(gamma_corrected,'gamma_corrected')

# 3. 直方图均衡化
#    直方图：统计每一个像素出现的次数。灰度值的密度分布
import matplotlib
import matplotlib.pyplot as plt

# 返回的是整个图像灰度值的分布情况。
gray_img = cv2.cvtColor(old,cv2.COLOR_BGR2GRAY)

# # 用来评估某个数据集的整体灰度值的质量,整个文件目录见day4_3
# his_img = cv2.calcHist([gray_img], [0], None, [256], [0, 255])
# plt.plot(his_img, color="b")
# plt.show()

# 灰度图
"""
会增加图像的对比度。但是有可能有些区域会导致全局过亮。
一般说来，增加图像的对比度，有可能会涨点


"""
equ_img = cv2.equalizeHist(gray_img) # 从一个直方图的累积分布到一个接近正态分布的一个调整。'
show(equ_img,'e')


# bgr的调整
# b,g, r = cv2.split(old_copy)
# equ_imgb = cv2.equalizeHist(b)
# equ_imgg = cv2.equalizeHist(g)
# equ_imgr = cv2.equalizeHist(r)
# equ_img = cv2.merge([equ_imgb,equ_imgg,equ_imgr])
# show(equ_img,'equ_img')


"""
区域直方图（运算慢，效果好）：
    
"""
clahe = cv2.createCLAHE()
cl1_b = clahe.apply(gray_img)
# cl1_g = clahe.apply(g)
# cl1_r = clahe.apply(r)
# equ_img = cv2.merge([cl1_b,cl1_g,cl1_r])
show(cl1_b,'cl1')











