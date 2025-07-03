import cv2
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)


"""
画点，画线，画矩形，画圆，画多边形
"""

zero_img = np.zeros([512, 512, 3], dtype=np.uint8)
# 画点 (画小圆)
cv2.circle(zero_img, (100, 100), 2, [0, 0, 255], 2, 1)
# 画圆
# center: (200, 200) 坐标, radius: 220半径，color: [0, 0, 255] 线条颜色值， 2 和 1 决定的线条的粗细
cv2.circle(zero_img, (200, 200), 20, [0, 0, 255], 2, 1)
# 画线 需要 2 个坐标
cv2.line(zero_img, (10, 10), (200, 200), [255, 0, 255], 2, 1)

# 画矩形
#
# for x in 标签目录
#     将归一化的坐标，转成 left_x, left_y, w, h
#     把这个框画上去
#     cv.waitkey(0) == ord('q') 如果用户按的是q键
#        shutil.move 移动走

cv2.rectangle(zero_img, [10, 10, 20, 20], [255, 0, 255], 2, 1)
# left_x,left_y, right_x, right_y

# 多边形
# 对一组坐标进行连线
#  N x1 x2  的一个 shape
points = np.array([[10, 5], [20, 30], [70, 20], [50, 10], [100, 300]], np.int32)
points = points.reshape((-1, 1, 2))
isClosed = False
zero_img = cv2.polylines(zero_img, [points], False, [255, 255, 255], 2)
# zero_img = cv2.polylines(zero_img, [points], True, (255, 0, 0), 2, 1)

show(zero_img, 'img')

cv2.waitKey(0)
