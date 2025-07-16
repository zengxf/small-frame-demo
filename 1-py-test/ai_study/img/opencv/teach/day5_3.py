import cv2
import random
import numpy as np
import time
def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)

"""
模板匹配：
先有一个模板。把这个模板从x0,y0从左到右，从上到下滑动，计算每个区域的距离，取距离小的地方(100像素)
if mindis<120: // 120是我们人为阈值
    找到目标
多模板匹配

用bgr图来进行匹配：受图像尺寸、光线、亮度、角度的影响，会误检。
灰度-》平滑-》二值化图-》形态学[几何形状]，再做模板匹配（相对说效果可能最佳）

"""

target = cv2.imread(r"../imgs/lenaNoise.png", 0)
target = cv2.resize(target,[512,512])
target = cv2.GaussianBlur(target,[3,3], 0)
# target = (target*0.5).astype(np.uint8)
# 读取模板图片
template1 = cv2.imread(r"./imgs/temp2.png",0)
template1 = cv2.GaussianBlur(template1,[3,3], 0)

template2 = cv2.imread(r"./imgs/temp.png",0)
template2 = cv2.GaussianBlur(template2,[3,3], 0)

template3 = cv2.imread(r"./imgs/temp3.png",0)
template3 = cv2.GaussianBlur(template2,[3,3], 0)

for template in [template1,template2,template3]:
    theight, twidth = template.shape[:2]
    # # 执行模板匹配，采用的匹配方式cv2.TM_SQDIFF_NORMED
    result = cv2.matchTemplate(target, template, cv2.TM_SQDIFF_NORMED)
    # # 归一化处理
    cv2.normalize(result, result, 0, 1, cv2.NORM_MINMAX, -1)
    # # 寻找矩阵（一维数组当做向量，用Mat定义）中的最大值和最小值的匹配结果及其位置
    min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)
    # 0.01 超参数
    if min_val < 0.01:
        strmin_val = str(min_val)
        last_img = cv2.rectangle(target, min_loc, (min_loc[0] + twidth, min_loc[1] + theight), (0, 0, 225), 2)
    show(last_img)