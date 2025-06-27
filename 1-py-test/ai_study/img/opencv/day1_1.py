"""
在图像领域，常用的库
OpenCV python,c++,几何所有语言都支持（开源的）
80%
1。做为前处理（喂入神经网络之前的处理）
2。后处理（神经网络得到的只是一个概率值，转换成业务数据中的表现）
3。数据再加工（数据增强）

20%
4。也可以用来做机器视觉方面的项目
   opencv做项目
      泛化能力不强，非常容易受光线和角度的影响。
      可解释性强
   神经网络
     泛化能力强
     可解释性不强

Haclon 工业缺陷领域收费（类似,matlab建模）
PILLOW 主要在python领域
1. 图像的xy坐标，是从左上角开始的
   W == X, H == Y
   (H,W)  (20, 10) 高为20，宽为10所在的坐标点

2.每个坐标点有像素值，每一个像素格的值域 0-255之间
    代表光线的，由弱到强，0代表全黑，255代表亮白色。灰度值
    只有一个通道的时候。灰度值

    图像平均值<100, 或者图像的平均值>230。

3. 色彩空间
    RGB,  Red, Gree,Blue
    BGR,  Blue,Gree,Red
    Gray, 代表灰度图

    有3个通道的时候，1个坐标点就有3个通道的值。

    opencv读图是按BGR进行的。PILLOW是按RGB来组织的

    图像的梯度： 两个像素点的变化量。梯度变化小，图像很平滑或者是一个纯色，图像就会模糊。
              在图像中标准差小，图像很平滑或者是一个纯色。

    图像平滑：会变模糊，去噪声

    图像归一化： 将图像的值变到0-1之间。image/255.0

    cv2.imread这个函数，不支持中文目录

    img[h1:h2, w1:w2,:] 切图

作业：
    1。定义一个函数，传入image, cut_size=(10,10), k=10
        实现随机区域中的cut_out，作用可以增强局部感知的能力。增强神经网络，模拟的是人眼对于局部特征的识别

    2。神经网络训练的时候一般都是RGB，但是我们自己写的代码默认是BGR，会出现识别率下降的问题。
        定义一个函数实现rgb-bgr转换。增强的颜色空间的泛化能力。

    3。H——Hue即色相，S——Saturation即饱和度 ， V——Value即色调
       H、S、V值范围分别是[0,180)，[0,255)，[0,255)
       随机比例调整hsv的值，再转回bgr
       hsv(image, h_ratio,s_ratio,v_ratio)

"""
# pip install opencv-python
import cv2
import random
import numpy as np

def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)
    print('hello')
"""
3种方法
第1单词小写，第2个单词大写
驼峰法
imageRead
第1个单缩写，第2个单词不缩小
imRead
imgRead

所有首字母都大写
ImageRead
ImgRead
ImRead

蛇形法
image_read
img_read
im_read

"""

path = r'F:\2025\py_do2025\ClosesSeg\clothes_color_by_person\clothes_color_by_person\bing_images_aquamarine\image_9_65439a43-9548-4f04-b671-c2a4a73244e6.jpg'
img = cv2.imread(path, 1)  # 如果是0代表灰度图。1代表是BGR图。opecv读图，读出来是numpy格式
img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
old_img = img.copy()
r, g, b = cv2.split(img)  # 在某些情况下，整个rgb偏暗或者梯度不明显的时候，可能某个通道会好处
h, w = img.shape[:2]  # np.uint8 值域在0-255之间 无符号int型。-1这叫有符号int
# 展示出来
show(r, 'r')
show(g, 'g')
show(b, 'b')

img[10:100, 10:100, :] = (0, 0, 0)  # cut_out
show(img)

for ih in range(h):
    for iw in range(w):
        px = old_img[ih, iw, :]  # 取某个坐标点所在的像素值
        if (ih >= 10 and ih <= 100) and (iw >= 10 and iw <= 100):
            old_img[ih, iw, :] = (0, 0, 0)
show(old_img, 'o2')

old_img_hsv = cv2.cvtColor(old_img, cv2.COLOR_RGB2HSV)
h, s, v = cv2.split(old_img_hsv)

ratio = random.uniform(0.1, 0.6)
h, s, v = h * ratio, s * ratio, v * ratio  # int->float

hsv_img = cv2.merge([h,s,v])
bgr_hsv_img = cv2.cvtColor(hsv_img.astype(np.uint8),cv2.COLOR_HSV2BGR)
show(bgr_hsv_img,'b')
