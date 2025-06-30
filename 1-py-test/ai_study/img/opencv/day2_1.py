import cv2
import random
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)


"""
面试问题，图像领域有那几种滤波。
    低通滤波：中值，均值，高斯，方框，双边， 平滑图像，会造成模糊情况，模糊之后主要特征会保留


2。图像平滑-->去噪声的作用
    滤波，均值，中值，高斯
    卷积的概念：局部扫描，区域感知
    1. 卷积核的设置
    2. 步长s=1, s=2
    3. 卷积核在原图中对应的区域，做点积，从左到右，从上到下按步长进行滑动
    4. 如果不足时，就补0
        
    卷积核，一般是奇数，是个正方形
    
    input(imput hw), k(ksize), s(stride), p(pad) ->得到的图像是多大，特征值（像素值）
    
    灰度图
    高斯 ，可能会循环几次
    根据业务特点，选择均值/中值 ，可能会循环几次
    高斯，可能会循环几次
    
1。7个优化算法的名字？

2。sgd和adam的区别和特点？******
    adam自适应历史梯度和动量
    sgd随机基础的梯度下降
    
3。先用adam（收敛快），后用sgd(利用它的震荡有可能逃出局部最优。方向向下.)  运气好,+0.5%

4。批量梯度下降，随机梯度下降，小批量梯度下降的区别？
    全量(all data)：优点，快。缺点，可能越过最优值，内存占用大
    
    随机(one data)：优点，方向向下，会接近最优化，内存占用小。缺点，收敛慢。   epoch要很大。
    
    小批量（min-batch)：结合 全量和随机。
    
    
"""
path = r"D:\zhigu-ai-27-note\untitled2\opencv32\lenaNoise.png"
img = cv2.imread(path, 0)

# blur 均值模糊: 取卷积核对应在原图中的中间值
# ksize: cv2.typing.Size, 要传2个值ksize = (w, h)
blur_img = cv2.blur(img,(5,5))
concat_img = np.hstack([blur_img, img])
# show(concat_img)
cv2.imwrite('2.jpg',concat_img)


ksize = 5
# medianBlur中值，椒盐噪声，良好。
# 一定会丢一些特征，希望丢不重要的特征
# kszie越大，越会模糊，只会留下主要的特征
# ksize: int ,只传一个值
mean_blur_img = cv2.medianBlur(img, ksize)
concat_img = np.hstack([mean_blur_img, img])
cv2.imwrite('1.jpg',concat_img)


# 高斯模糊
gao_blur_img = cv2.GaussianBlur(img,(5,5),0)

concat_img = np.hstack([gao_blur_img, img])
cv2.imwrite('3.jpg',concat_img)

# 双边滤波
# 两个物体中间有灰度值过度的情况，使用它，较佳
# 50 - 130之间
blur = cv2.bilateralFilter(img,9,75,75)
concat_img = np.hstack([blur, img])
cv2.imwrite('4.jpg',concat_img)

# 方框滤波
# ddepth: int ，深度图。2D，都是-1
box = cv2.boxFilter(img,-1,(5,5),normalize=True)
concat_img = np.hstack([box, img])
cv2.imwrite('5.jpg',concat_img)