import cv2
import random
import numpy as np

def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)

o = cv2.imread("./imgs/yuan.png",1)

co = o.copy()
gray = cv2.cvtColor(co, cv2.COLOR_BGR2GRAY)
ret, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
# 一般为cv2.RETR_TREE
#   binary:最好是一个二值化图
#   method:
#   cv2.RETR_TREE，树型结果 *****
#   cv2.RETR_EXTERNAL，最外层的轮廓 *****

#   cv2.RETR_LIST，不管层级结构
#   cv2.RETR_CCOMP，只发现外层和内层轮廓

#   cv2.CHAIN_APPROX_NONE，所有轮廓坐标点全部存储
#   cv2.CHAIN_APPROX_SIMPLE，简化坐标点进行存储

# 轮廓的顺序是没有规则的

# contours，发现轮廓的所有坐标点
# Nx1x2的坐标点
# hierarchy，轮廓的层级结构，一般不关心. 了解

"""
    为什么我们需要获取轮廓的一些性质呢？
    几何属性的规则：
    1. 这个物体的面积、宽高比、弧长、重心（质心）、角度、外接矩形、最小外接旋转矩形、最小外接圆、最小外接椭圆
    2. 宽高比、轮廓面积与边界矩形面积的比、轮廓中最大值和最小值的位置，以及对应像素值、平均颜色及平均灰度、极点
    去识别某个物体是否满足几何规则。
    
    轮廓检测是opencv当中去识别目标的一个较核心方法。通过几何规则来限定的。带来可解释性。
    
    # 
"""
contours, hierarchy = cv2.findContours(binary, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
for i, ctn in enumerate(contours):
    #  绘第几个轮廓
    co = cv2.drawContours(co, contours, i, (0, 0, 255), 5)
    # 过滤坐标点比较少的轮廓
    # 但是轮廓的数量并不决定，轮廓的面积, 不等于外接矩形的面积
    area = cv2.contourArea(contours[i])
    # 得到轮廓的外接矩形 leftx, lefty, w,h
    left_x, left_y, w, h = cv2.boundingRect(contours[i])
    co = cv2.rectangle(co, (left_x,left_y), (left_x + w, left_y + h), (0, 0, 255), 2)
    print("外接矩形面积：", w*h)
    print('面积：',area)
    area_arc = cv2.arcLength(contours[i], True)
    print('轮廓弧长：',area_arc)
    if area < 500:
        if contours[i].shape[0] < 10:
            co = cv2.drawContours(co, contours, i, (255, 0, 0), 50)
show(co,str(i))