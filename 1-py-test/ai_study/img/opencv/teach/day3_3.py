import cv2
import random
import numpy as np

def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    dd = cv2.waitKey(0)
    if dd == ord("m"):
        print('move')
    elif dd == ord("d"):
        print("remove")

"""
画点，画线，画矩形，画圆，画多边形


"""
zero_img = np.zeros([512,512,3],dtype=np.uint8)
# 画点
#                    center, 半径， 颜色值，2和1决定的线条的粗细
cv2.circle(zero_img,(100,100),2,[0,0,255],2,1)
# 画圆
cv2.circle(zero_img,(200,200),20,[0,0,255],2,1)

# 画线 需要2个坐标
cv2.line(zero_img,(10,10),(200,200),[255,0,255],2,1)

# 画矩形
# left_x, left_y, w, h
# 神经网络的预测出来值，cx,cy,w,h   - > cx-w/2, cy-h/2 未归一化的时候
#          归一化   cx,cy,w, h  -> (cx - w/2)*old_w, (cx - w/2)*old_h, w *old_w, h * old_h
# yolo 打的标签是, label, cx, cy, w, h

# 任务1：把txt中的内容还原到原图像中-》 实现标签检查
# 任务2：把labelme中的json坐标还原到原图-》实现标签检查
# 任务3：实现sobel,拉普拉斯与原图addweights，锐化增强
#
# for x in 标签目录
#     将归一化的坐标，转成left_x, left_y, w, h
#     把这个框画上去
#     cv.waitkey(0) == ord('q') 如果用户按的是q键
#        shutil.move 移动走

cv2.rectangle(zero_img,[10,10,20,20],[255,0,255],2,1)
# left_x,left_y, right_x, right_y

# 多边形
# pip install labelme

#  N x1 x2  的一个shape
points = np.array([[10, 5], [20, 30], [70, 20], [50, 10], [100, 300]], np.int32)
points = points.reshape((-1, 1, 2))
isClosed = False
zero_img = cv2.polylines(zero_img,[points],False,[255,255,255],2)
# zero_img = cv2.polylines(zero_img, [points], isClosed, (255, 0, 0), 2,1)

show(zero_img)














