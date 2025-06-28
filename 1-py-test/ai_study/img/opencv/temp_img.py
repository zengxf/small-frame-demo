import cv2
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)  # 展示图片出来。img 要为 NumPy 格式


path = 'D:/Data/Test/img/52.png'
# 0 代表灰度图；1 代表是 BGR 图。
img = cv2.imread(path, 1)  # 读出来是 NumPy 格式
show(img, 'src-img')  # 原图，中文显示有乱码

# img[h1:h2, w1:w2, :] 切图，相当于裁剪 (cut_out)
cat = img[10:100, 10:100, :]  # 默认切片生成视图
print(f"img.shape: {img.shape}, cat.shape: {cat.shape}")

# cat_c = cat.copy()  # 使用 copy 才不会相互影响
# cat_c[...] = (0, 0, 0)

# cat[...] = (255, 0, 0)  # (显式赋值) 设置 BGR 值，相当于 cat[x][y] = (255, 0, 0)
# # cat[...] = (255, 0, 0, 3)  # Err: 无法将输入数组从形状 (4,) 广播到形状 (90,90,3)
# # cat[...] = 0  # (标量广播) 相当于设值 (0, 0, 0)

# cat[...] *= 0.4  # Err: output from dtype('float64') to dtype('uint8')
cat[...] = cat * 0.4  # 对视图区域直接赋值 (乘随机数)
# cat[...] = cat.astype(np.uint8)  # 小数没关系，可以不用转整数

# img[10:100, 10:100, :] = (0, 0, 0)  # 相当于上面 cat = 和 cat[...] =
show(img, 'cat-img')

cv2.waitKey(0)