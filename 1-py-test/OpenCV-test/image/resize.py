"""
调整大小与旋转
"""

import cv2
import read_img

# 读取测试图片
image = read_img.test_img()
cv2.imshow('img-src', image)  # 显示原图

# 缩放
resized = cv2.resize(image, (300, 300))
cv2.imshow('resized', resized)

# 读取长宽
(h, w) = image.shape[:2]
print('h', h, 'w', w)

# 旋转45度
matrix = cv2.getRotationMatrix2D((w // 2, h // 2), 45, 1.0)
rotated = cv2.warpAffine(image, matrix, (w, h))
cv2.imshow('Rotated', rotated)

key = cv2.waitKey(0)
print('key:', key)
