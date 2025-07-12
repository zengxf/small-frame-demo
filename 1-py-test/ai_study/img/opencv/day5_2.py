import cv2
import random
import numpy as np
import time
def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)

img = cv2.imread('./imgs/20230925144601.jpg')
h, w = img.shape[:2]
"""
特征：大小，形状，颜色（HSV）
空间上: 改变的xy所在的位置
数量上: copy_paste复制粘贴
光线上、对比度上：gamma光线调整、直方图

图像平移/旋转/翻转：图像增强一个策略。对目标roi区域进行了空间的变化 +0.5%-1.0%
仿射变换： 模拟视角的变换的时候在用
透视变换：先用分割算法-》透视变换（票证，把斜的扶正）
        
"""
tx = -30
ty = -50
moving_matrix = np.float64(
    [[1, 0, tx],
     [0, 1, ty]]
)
# 原图，平移矩阵，平移后图像的大小
matrix_img = cv2.warpAffine(img.copy(),moving_matrix,(int(w*1.0), int(h*1.0)))

show(matrix_img,'d')

# 设定旋转点的位置，角度，裁剪比例
# 会自动得一个旋转矩阵
# 推荐值：-15度到15度。
# 图像的旋转
M=cv2.getRotationMatrix2D((w,0),7,0.8)
rota_img = cv2.warpAffine(img.copy(),M,(int(w*1.0), int(h*1.0)))
show(rota_img,'x')

# 图像的翻转
flip_img = cv2.flip(img.copy(),0) # 0是上下，1就是左右
show(flip_img,'f')

# 图像仿射变换。模拟视角的变换的时候在用
# 仿射变huan 和透视变换 会引起特征的变化。空间变化。有可能导致完全失真
pts1=np.float32([[50,50],[200,50],[50,200]]) # 原有的3个点
pts2=np.float32([[10,100],[200,50],[100,250]]) # 希望图像畸变到新的3个点
M=cv2.getAffineTransform(pts1,pts2) # 仿身矩阵
dst=cv2.warpAffine(img.copy(),M,(int(w*1.0), int(h*1.0)))
show(dst,'fangshe')

# 模拟出空间的距离、角度、方向上的变化
# 票证识别
# 书本

pts1 = np.float32([[56, 65], [368, 52], [28, 387], [389, 390]])
pts2 = np.float32([[0, 0], [300, 0], [0, 300], [300, 300]])
M = cv2.getPerspectiveTransform(pts1, pts2) # 3x3
dst = cv2.warpPerspective(img.copy(), M, (300, 300))

show(dst,'toushi')