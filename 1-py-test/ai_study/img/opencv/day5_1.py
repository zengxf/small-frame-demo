import cv2
import random
import numpy as np
import time
def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)

"""
几何变换
1. 放大，或者缩小
    resize, 会有一点挤压变化
2. 旋转、平移
"""
img = cv2.imread(r'F:\data_source\dongqi\0511_pt_2\images\train\0da4eb90-ab54-4a2b-8407-59b0704ec8ef.jpg')
# img, height, width
# cv2.INTER_NEAREST，最近邻插值法, 复制最近的像素。速度快。
# cv2.INTER_LINEAR，三线性插值法, 过渡会平滑一些。慢一点。

# 作业：1， 3， 4
# 1.不等比例的数据增强
# 好处，是让目标roi拥有多样性
# 宽高比，1:3, 3:1, 5:1, 1:5, 2:1, 1:2。如果需要更大的，就要分析roi的特点
# 固定比例原因，是数据增强时，需要同步改变box

# t1 = time.time()
# img = cv2.resize(img,[2024,2024],cv2.INTER_NEAREST)
# t2 = time.time()
# print(t2-t1)
#                     # width, height
# img = cv2.resize(img,[2024,2024],cv2.INTER_LINEAR) # 默认使用INTER_LINEAR 三线性插值法
# print(time.time()-t2)

# 2. height, width等比例上采样，或者下采样

# up = cv2.pyrUp(img.copy()) # 上采样2倍
# down = cv2.pyrDown(img.copy())

# 3. 神经网络的训练图像，最好是一个正方形【前处理的一个环节】
#    1920x1080 长方形，强制resize图像会有一点失真
#    letterbox,目标检测中这个函数的名称叫letterbox，如何不失真的将一个长方形，变成一个正方形
#    model_size = (640,640)
#    old_size = (1920,1080)
#    ratio = min(640/1920, 640/1080)
#    1080*ratio = 356, 1920*ratio = 633
#    640-633 = 7/2 = 3, 640 - 356 = 284/2 = 142 这里是填多少个像素

#    目标检测，填充像素值：114，分割：0 最保险的方法
#    letterbox(old_img, boxes)
#    return 640x640的图, ratio, padw(3), padh(142), boxes

# 4. 神经网络，训练的时候是640x640的图，得到的预测boxes(是基于640x640的)【后处理的一个环节】
#   form_pre_result_to_origin_boxes(pre_boxes)
#   cx*640 - padw, cy*640 - padh, 得到在原图中的cx, cy
#   w * 640, h * 640


# 5. 读任意尺寸的4张图，4个box，拼成一张图(mosic增强)
"""
    减少了训练的batch（时间），减少了计算梯度的次数，并同时增加了目标的数量 +8%
    1. 选4张，-->letterbox[512x512]
    2. 新建zero图像，1024x1024
    3. 0-512,第1张，box不用变
       512-1024,第2张，box中x+512, y不变
       ....以此类推
"""
# 6.甜甜圈，把目标框在非0像素区域复制2-10份
#         尝试，只能在环形区域上（面试要讲的方案）
#           找到合适的轮廓2个，在一个新图中drawcounter绘出来255，绘一个mask图
#           mask中原来的box位置设置为0，新的位置，只要不为0的位置就行,xy
#           xy坐标（已知hw)，就可以绘在原图中



# cv2.imwrite('up.jpg',up)
# cv2.imwrite('down.jpg',down)

# show(up,'up')
# show(down,'down')
