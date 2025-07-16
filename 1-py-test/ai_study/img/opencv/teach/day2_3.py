import cv2
import random
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)


"""
1. 如何拿模糊来做深度学习中的数据增强。
    一种方法，故意制造模糊的图像，因为模糊会保留主要特征，强化深度学习的特征提取能力
    另一种方法，就是将模糊的图与原图进行混合，模糊的尺度要大一些
    
2. mixup，图像混合
   A图0.5, B0.5
   提高检出率，提高了误检率


涨点：涨，准确率，精度
"""


# 随机3,5,7,9,11尺度的模糊图片，随机 中值、均值、高斯

def rnd_blur(image):
    k_size = random.choice([25, 35])
    ratio = random.uniform(0, 1)
    blur_img = None
    if ratio < 0.3:
        blur_img = cv2.medianBlur(image, k_size)
    if ratio >= 0.3 and ratio < 0.6:
        blur_img = cv2.blur(image, (k_size, k_size))
    if ratio >= 0.6:
        blur_img = cv2.GaussianBlur(image, (k_size, k_size), 0)
    return blur_img


def mixup(img1, img2):
    h, w = img1.shape[:2]
    img2 = cv2.resize(img2, [w, h])  # 大小调整
    last_img = cv2.addWeighted(img2, 0.5, img1, 0.5, 0)
    return last_img


# 将模糊的图与原图进行mixup
def blur_mixup(img1):
    img2 = rnd_blur(img1)
    ratio = random.uniform(0.3, 0.7)
    last_img = cv2.addWeighted(img2, ratio, img1, 1 - ratio, 0)
    return last_img


if __name__ == "__main__":
    path = r"D:\zhigu-ai-27-note\untitled2\opencv32\lenaNoise.png"
    img1 = cv2.imread(path)
    bgr_blur = rnd_blur(img1)
    show(bgr_blur, 'bgr_blur')

    # img2 = cv2.imread("./imgs/20230925144601.jpg")

    last_img = blur_mixup(img1)

    show(last_img,'l')

    # h, w = img1.shape[:2]
    # img2 = cv2.resize(img2, [w, h])  # 大小调整

    # last_img = (img2 * 0.5 + img1 * 0.5).astype(np.uint8)
    # show(last_img,'last_img')
    # last_img = cv2.addWeighted(img2, 0.5, img1, 0.5, 0)
    # show(last_img, 'last2')
