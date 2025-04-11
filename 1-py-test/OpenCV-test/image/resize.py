"""
调整大小与旋转
"""

import cv2
import read_img

# 读取测试图片
image = read_img.test_img()
cv2.imshow('img-src', image)  # 显示原图

resized = cv2.resize(image, (300, 300))  # 缩放
cv2.imshow('resized', resized)

(h, w) = image.shape[:2]
matrix = cv2.getRotationMatrix2D((w // 2, h // 2), 45, 1.0)  # 旋转45度
rotated = cv2.warpAffine(image, matrix, (w, h))
cv2.imshow('Rotated', rotated)

cv2.waitKey(0)
