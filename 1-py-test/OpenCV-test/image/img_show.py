"""
图像读取与显示
"""

import cv2
import read_img

# from read_img import test_img
# image = test_img()

# 读取测试图片
image = read_img.test_img()

# 显示图像窗口
cv2.imshow('OpenCV-test-Image', image)

# 等待按键后关闭窗口
cv2.waitKey(0)
# cv2.destroyAllWindows()   # 可以不用
