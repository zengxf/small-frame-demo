"""
读取图片
"""

import cv2

path = 'D:/Data/Test/img/52.png'


def test_img():
    # 读取图像（支持 JPG、PNG 等格式）
    image = cv2.imread(path)
    return image
