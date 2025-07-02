import cv2
import numpy as np
import random

def show(img, title=''):
    cv2.imshow(title, img)

# 随机模糊
def rnd_blur(image):
    k_size = random.choice([25, 35])
    ratio = random.uniform(0, 1)
    print(f'rnd_blur, k_size: {k_size}, ratio: {ratio}')

    blur_img = None
    if ratio < 0.3:
        blur_img = cv2.medianBlur(image, k_size)
    if ratio >= 0.3 and ratio < 0.6:
        blur_img = cv2.blur(image, (k_size, k_size))
    if ratio >= 0.6:
        blur_img = cv2.GaussianBlur(image, (k_size, k_size), 0)
    return blur_img

# 将 2 张图片进行 混合
def mix(img1, img2):
    h, w = img1.shape[:2]  # 读 img 1 的 h w
    img2 = cv2.resize(img2, [w, h])  # 对 img 2 进行大小调整
    # last_img = (img2 * 0.5 + img1 * 0.5).astype(np.uint8)
    last_img = cv2.addWeighted(img2, 0.5, img1, 0.5, 0)  # 相当于上面的封装
    return last_img

# 先模糊，再将模糊的图与原图进行 混合
def blur_mix(img1):
    img2 = rnd_blur(img1)
    ratio = random.uniform(0.3, 0.7)
    print(f'blur_mix, ratio: {ratio}')

    last_img = cv2.addWeighted(img2, ratio, img1, 1 - ratio, 0)
    return last_img

# path1 = r"./imgs/lenaNoise.png"
path1 = r"D:/Data/Test/img/lenaNoise.png"
# path2 = r"./imgs/ye2.jpg"
path2 = r"D:/Data/Test/img/ye2.jpg"
img = cv2.imread(path1, 1)
img2 = cv2.imread(path2, 1)
# show(img, 'src_img')

# - -

# 随机
rnd_blur_img = rnd_blur(img)
concat_rnd_blur = np.hstack([img, rnd_blur_img])
show(concat_rnd_blur, 'concat_rnd_blur')

# - -

# 两图混合 1
mix_img1 = mix(img, img2)
concat_mix_img = np.hstack([img, mix_img1])
show(concat_mix_img, 'concat_mix_img 1')

mix_img2 = mix(img2, img)
concat_mix_img = np.hstack([img2, mix_img2])
show(concat_mix_img, 'concat_mix_img 2')

# - -

# 模糊混合
blur_mix_img = blur_mix(img)
concat_blur_mix = np.hstack([img, blur_mix_img])
show(concat_blur_mix, 'concat_blur_mix')

cv2.waitKey(0)