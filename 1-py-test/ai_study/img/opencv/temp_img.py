import cv2
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)


path = r"./imgs/lenaNoise.png"
path = r"D:/Data/Test/img/qianming.png"
img = cv2.imread(path, 0)
# show(img, 'src_img')

# - -

# 高斯
gas_blur = cv2.GaussianBlur(img, (5, 5), 0)
concat_gas_blur = np.hstack([img, gas_blur])
show(concat_gas_blur, 'concat_gas_blur')

# - -

# 黑白 (转黑白)
# ret, ths1_img = cv2.threshold(gas_blur, 30, 255, cv2.THRESH_BINARY)
ret, ths1_img = cv2.threshold(gas_blur, 127, 255, cv2.THRESH_BINARY)
# show(ths1_img, 'ths1_img')
concat_ths1_img = np.hstack([img, ths1_img])
show(concat_ths1_img, 'concat_ths1_img')

# - -

# 腐蚀
yuan = np.array([
    [0, 0, 1, 0, 0],
    [0, 1, 1, 1, 0],
    [1, 1, 1, 1, 1],
    [0, 1, 1, 1, 0],
    [0, 0, 1, 0, 0],
], dtype=np.uint8)
erosion = cv2.erode(ths1_img, yuan, iterations=1)  # 腐蚀
concat_erosion = np.hstack([img, erosion])
show(concat_erosion, 'concat_erosion 5')

kernel = np.ones((3, 3), np.uint8)
erosion = cv2.erode(ths1_img, kernel, iterations=2)  # 腐蚀
concat_erosion = np.hstack([img, erosion])
show(concat_erosion, 'concat_erosion 3')

# - -

# 膨胀
dilate_img = cv2.dilate(ths1_img, kernel, iterations=1)  # 膨胀
concat_dilate_img = np.hstack([img, dilate_img])
show(concat_dilate_img, 'concat_dilate_img')

# - -

# 黑白 - 腐蚀  = 得到边缘，或者空心效果
sub_erosion_img = np.abs(ths1_img - erosion)
concat_sub_erosion_img = np.hstack([img, sub_erosion_img])
show(concat_sub_erosion_img, 'ths1 sub erosion')

# - -

# 黑白 - 膨胀 = 有可能是空心效果
sub_dilate_img = np.abs(ths1_img - dilate_img)
concat_sub_dilate_img = np.hstack([img, sub_dilate_img])
show(concat_sub_dilate_img, '(concat 1) ths1 sub dilate')
concat_sub_dilate_img = np.hstack([ths1_img, dilate_img, sub_dilate_img])
show(concat_sub_dilate_img, '(concat 2) ths1 sub dilate')

# - -

# 膨胀 - 腐蚀 = 或者空心效果。边缘
show(np.hstack([img, np.abs(dilate_img - erosion)]), 'dilate sub erosion')

cv2.waitKey(0)