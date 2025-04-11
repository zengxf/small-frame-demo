"""
图像二值化
"""

import cv2
import read_img

# 读取测试图片
image = read_img.test_img()
cv2.imshow('img-src', image)  # 显示原图

# 转为灰度图
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
cv2.imshow('gray-img', gray)

# 阈值分割
ret, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
cv2.imshow('binary-img', binary)

cv2.waitKey(0)
