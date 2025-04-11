"""
图像模糊处理-高斯模糊
"""

import cv2
import read_img

# 读取测试图片
image = read_img.test_img()
cv2.imshow('img-src', image)  # 显示原图

# 高斯模糊
blurred = cv2.GaussianBlur(image, (5, 5), 0)
cv2.imshow('Blurred', blurred)

cv2.waitKey(0)
