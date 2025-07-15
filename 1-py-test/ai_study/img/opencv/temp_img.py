import cv2
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # cv2.waitKey(0)


target = cv2.imread(r"D:/Data/Test/img/lenaNoise2.png", 0)
# target = cv2.resize(target, [512, 512])  # 原图扣的，不用 resize
target = cv2.GaussianBlur(target, [3, 3], 0)
# target = (target * 0.8).astype(np.uint8)  # 原图扣的，不用改通道值

# 读取模板图片
template1 = cv2.imread(r"D:/Data/Test/img/template1.png", 0)
template1 = cv2.GaussianBlur(template1, [3, 3], 0)

template2 = cv2.imread(r"D:/Data/Test/img/template2.png", 0)
template2 = cv2.GaussianBlur(template2, [3, 3], 0)

template3 = cv2.imread(r"D:/Data/Test/img/template3.png", 0)
template3 = cv2.GaussianBlur(template3, [3, 3], 0)

for (i, template) in enumerate([template1, template2, template3]):
    t_height, t_width = template.shape[:2]
    # 执行模板匹配，采用的匹配方式 cv2.TM_SQDIFF_NORMED
    result = cv2.matchTemplate(target, template, cv2.TM_SQDIFF_NORMED)
    # 归一化处理
    cv2.normalize(result, result, 0, 1, cv2.NORM_MINMAX, -1)
    # 寻找矩阵（一维数组当做向量，用 Mat 定义）中的最大值和最小值的匹配结果及其位置
    min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)
    # 0.01 超参数
    if min_val < 0.01:
        last_img = cv2.rectangle(target, min_loc, (min_loc[0] + t_width, min_loc[1] + t_height), (0, 0, 225), 2)

    last_img = cv2.resize(last_img, [512, 512])
    show(last_img, 'last_img' + str(i + 1))

cv2.waitKey(0)
