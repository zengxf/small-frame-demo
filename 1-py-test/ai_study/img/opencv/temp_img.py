import cv2
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)


# path = r"./imgs/lenaNoise.png"
# path = r"D:/Data/Test/img/lenaNoise.png"
# path = r"./imgs/52.png"
path = r"D:/Data/Test/img/52.png"

# -- -- - -- - -- - -- - -
# img = cv2.imread(path, 1)
# ret, ths1_img = cv2.threshold(img, 40, 255, cv2.THRESH_BINARY)  # 二值化
# kernel = np.ones((3, 3), np.uint8)
# erode_img = cv2.erode(ths1_img, kernel, iterations=2)  # 腐化
# dilate_img = cv2.dilate(erode_img, kernel, iterations=2)  # 膨胀
# show(dilate_img, 'dilate_img')
# -- -- - -- - -- - -- - -

img = cv2.imread(path, 1)
show(img, 'src_img')
img_bgr = img.copy()

img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
img = cv2.GaussianBlur(img, (3, 3), 0)
imgOld = img.copy()
# show(img, 'img cvt gus')

x = cv2.Sobel(img, -1, 1, 0)  # 边缘检测 Sobel 算子
show(x, 'x1')

x = cv2.convertScaleAbs(x)  # uint8 用的 abs
show(x, 'x2')

y = cv2.Sobel(img, -1, 0, 1)
show(y, 'y1')

y = cv2.convertScaleAbs(y)  # uint8 用的 abs
show(y, 'y2')

# 真正使用时要合成下
# 加和不加区别是误差很大，加了之后误差能控制到一个像素，甚至好的时候没有误差
addWeights = cv2.addWeighted(x, 0.5, y, 0.5, 0)
show(addWeights, 'add')

# 相当于把原图的灰度图做边缘增强
bian_yuan = cv2.addWeighted(imgOld, 0.5, addWeights, 0.5, 0)
# show(bian_yuan, ' bian_yuan')

# 相当于把原图做边缘增强
addWeights_bgr = cv2.cvtColor(addWeights, cv2.COLOR_GRAY2BGR)
img_bgr_bianyuan = cv2.addWeighted(img_bgr, 0.6, addWeights_bgr, 0.4, 0)
concat_img_bgr_bianyuan = np.hstack((img_bgr, img_bgr_bianyuan))
# show(concat_img_bgr_bianyuan, 'concat_img_bgr_bianyuan')

# Scharr (相比 Sobel) 边缘强些，失真度高些
scharr_img_x = cv2.convertScaleAbs(cv2.Scharr(imgOld, -1, 1, 0))
scharr_img_y = cv2.convertScaleAbs(cv2.Scharr(imgOld, -1, 0, 1))
addWeights_scharr = cv2.addWeighted(scharr_img_x, 0.5, scharr_img_y, 0.5, 0)
show(addWeights_scharr, 'addWeights_scharr')

cv2.waitKey(0)