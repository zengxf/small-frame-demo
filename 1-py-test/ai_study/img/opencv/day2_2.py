import cv2
import random
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)

"""
opencv用来做目标检测。
1. 灰度图 
2. 低通滤波
3. 二值化
4. 形态学（基础，二值化后的图像)
   膨胀： 填小洞 【把白色的变大，把黑色的变小】
   腐蚀: 对边缘进行腐蚀，虚化【去毛刺】
   
   开运算，闭运算：
   开运算，先腐蚀，再膨胀。因为腐蚀会去边缘。膨胀还原。
   闭运算，先膨胀，再腐蚀。因为膨胀会变大。腐蚀还原。
   
   形态学梯度:
   膨胀 - 原图，或者原图-腐蚀, 膨胀-腐蚀，得有可能得到空心效果，或者边缘像素点

5. 自定义卷积核
    cv2.MORPH_ELLIPSE 椭圆
    cv2.MORPH_RECT 矩形
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
    利用numpy来自定义构建【特征操作时才需要】
    
"""
yuan = np.array([
    [0,0,0,0,0],
    [0,0,0,0,0],
    [1,1,1,1,1],
    [0,0,0,0,0],
    [0,0,0,0,0],
],dtype=np.uint8)

img = cv2.imread('./imgs/qianming.png',0)

blur = cv2.GaussianBlur(img,(5,5),0)

ret, ths1_img = cv2.threshold(blur, 30, 255, cv2.THRESH_BINARY)
# show(ths1_img,'ths')
# 腐蚀
kernel = np.ones((3,3),np.uint8)
erosion = cv2.erode(ths1_img, yuan,iterations = 1)
concat_img = np.hstack([erosion, img])
show(concat_img,'1')

# # 膨胀
# dilate_img = cv2.dilate(ths1_img,kernel,iterations=1)
# concat_img = np.hstack([dilate_img, img])
# show(concat_img,'2')
#
# # 原图 - 腐蚀  = 得到边缘，或者空心效果
#
# old_fushi =  np.abs(ths1_img - erosion)
# show(old_fushi,'old-fushi')
#
# # 原图 - 膨胀 = 有可能是空心效果
#
# old_pengzhang = np.abs(dilate_img - ths1_img)
# show(old_pengzhang,'old_pengzhang')
#
# # 膨胀 - 腐蚀 = 或者空心效果。边缘
# show( np.abs(dilate_img - erosion  ),'kongxing')






