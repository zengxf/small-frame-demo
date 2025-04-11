"""
基础测试
"""

import cv2

# 读取图像（支持 JPG、PNG 等格式）
image = cv2.imread('D:/Data/Test/img/52.png')

# 显示图像窗口
cv2.imshow('OpenCV-test-Image', image)

# 等待按键后关闭窗口
cv2.waitKey(0)
# cv2.destroyAllWindows()   # 可以不用
