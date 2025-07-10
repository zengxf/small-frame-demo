import cv2
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # cv2.waitKey(0)


old = cv2.imread(r"D:/Data/Test/img/wiki_test_1.jpg", 1)
old = cv2.imread(r"D:/Data/Test/img/ye2.jpg", 1)
old_copy = old.copy()
# show(old, 'src_img')

# # 1. 粗放派
# old1 = (old * 1.2).astype(np.uint8)  # scale, 随机 0.5-1.3
# show(old1, 'd1')
# old1 = ((old / 255.0 * 1.2) * 255).astype(np.uint8)  # 跟上面一样
# show(old1, 'd2')

# # 2. gamma 光线调整 gamma 值 (大于 1 亮化，小于 1 暗化)
# gamma = 0.8  # 0.5 - 1.5 之间随机
# # 归一化到 [0, 1] 之间
# image_normalized = old_copy / 255.0
# # 使用 gamma 校正公式：output = input ^ (1 / gamma)
# gamma_corrected = np.power(image_normalized, 1 / gamma)
# # 将调整后的图像还原为 [0, 255] 范围
# gamma_corrected = np.uint8(gamma_corrected * 255)
# show(gamma_corrected, 'gamma_corrected')

# 3. 直方图均衡化
#    直方图：统计每一个像素出现的次数。灰度值的密度分布
import matplotlib.pyplot as plt

# 返回的是整个图像灰度值的分布情况。
gray_img = cv2.cvtColor(old, cv2.COLOR_BGR2GRAY)

# # 用来评估某个数据集的整体灰度值的质量
# his_img = cv2.calcHist([gray_img], [0], None, [256], [0, 255])
# plt.plot(his_img, color="b")
# plt.show()

# 灰度图
"""
直方图均衡化
会增加图像的对比度。但是有可能有些区域会导致全局过亮。
一般说来，增加图像的对比度，有可能会涨点准确率
"""
equ_img = cv2.equalizeHist(gray_img)  # 从一个直方图的累积分布到一个接近正态分布的一个调整。
show(equ_img, 'e')

# # bgr 的调整
# b, g, r = cv2.split(old_copy)
# equ_b = cv2.equalizeHist(b)
# equ_g = cv2.equalizeHist(g)
# equ_r = cv2.equalizeHist(r)
# equ_img = cv2.merge([equ_b, equ_g, equ_r])
# show(equ_img, 'equ_img')

"""
区域直方图（运算慢，效果好）：
"""
clahe = cv2.createCLAHE()
cl1_gray = clahe.apply(gray_img)
show(cl1_gray, 'cl1_gray')

# b, g, r = cv2.split(old_copy)
# cl1_b = clahe.apply(b)
# cl1_g = clahe.apply(g)
# cl1_r = clahe.apply(r)
# equ_img = cv2.merge([cl1_b, cl1_g, cl1_r])
# show(equ_img, 'cl1_equ_img')

cv2.waitKey(0)