import cv2


def show(img, title=''):
    cv2.imshow(title, img)
    # cv2.waitKey(0)

# 自定义二值化 (当前是 3 个值)
def custom_thresholds(gray_img, min_conf_ths, max_conf_ths):
    h, w = gray_img.shape[:2]
    for i in range(h):
        for j in range(w):
            px = gray_img[i, j]  # 取某个坐标点的像素值
            if px < min_conf_ths:
                gray_img[i, j] = 0
            elif px >= min_conf_ths and px < max_conf_ths:
                gray_img[i, j] = 128
            else:
                gray_img[i, j] = 255
    return gray_img


# path = 'D:/Data/Test/img/52.png'
path = r'./imgs/1.png'
img = cv2.imread(path, 0)  # 灰度读
show(img, 'src-img')

img_old = img.copy()  # 深 copy
# show(img_old, 'old')

# # 自定义二值化
# img2thresholds = custom_thresholds(img, 40, 150)
# show(img2thresholds, 'img2thresholds')

"""
cv2.THRESH_BINARY, 超过 127 变为 255，否则为 0
cv2.THRESH_BINARY_INV, 超过 127 的为 0，否则为 255
cv2.THRESH_TRUNC，超过 127 的为 127，否则不变
cv2.THRESH_TOZERO, 小于 127 的为 0，否则不变
cv2.THRESH_TOZERO_INV, 小于 127 的不变，否则为 0 
"""
ret, ths1_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_BINARY)
show(ths1_img, 'ths1_img')

ret, ths2_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_BINARY_INV)
show(ths2_img, 'ths2_img')

ret, ths3_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_TRUNC)
show(ths3_img, 'ths3_img')

ret, ths4_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_TOZERO)
show(ths4_img, 'ths4_img')

ret, ths5_img = cv2.threshold(img_old.copy(), 127, 255, cv2.THRESH_TOZERO_INV)
show(ths5_img, 'ths5_img')

# 自适应二值化 (没什么用)
# ksize 画格子，下面是 11 * 11，就是每个格子的宽高都是 11，
ada_img = cv2.adaptiveThreshold(img_old.copy(), 127, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 0)
show(ada_img, 'ada_img')

cv2.waitKey(0)
