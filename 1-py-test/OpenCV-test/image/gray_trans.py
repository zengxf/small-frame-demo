"""
灰度转换与边缘检测
"""

import cv2
import read_img

# 读取测试图片
image = read_img.test_img()
cv2.imshow('img-src', image)  # 显示原图

# 转为灰度图
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
cv2.imshow('gray-img', gray)

# Canny 边缘检测
edges = cv2.Canny(gray, 100, 200)
cv2.imshow('edge-img', edges)

# 等待按键后关闭所有窗口
cv2.waitKey(0)
# cv2.destroyAllWindows()   # 可以不用
