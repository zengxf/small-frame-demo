import cv2
import random
import numpy as np

def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)

"""
Laplacian，去噪声效果要更好一些。

边缘检测之后，可再次进行二值化、平滑、形态学

"""
path = r"D:\zhigu-ai-27-note\untitled2\opencv32\1.png"
img = cv2.imread(path, 1)
imgold = img.copy()
b,g,r = cv2.split(img)
img_lap_b = cv2.convertScaleAbs(cv2.Laplacian(b,-1))  # 默认卷积核一般都是3
show(img_lap_b,'ss')
img_lap_b[img_lap_b>40] = 255

img_lap_g = cv2.convertScaleAbs(cv2.Laplacian(g,-1))
img_lap_g[img_lap_g>40] = 255

img_lap_r = cv2.convertScaleAbs(cv2.Laplacian(r,-1))
img_lap_r[img_lap_r>40] = 255

show(img_lap_b,'b')
show(img_lap_g,'g')
show(img_lap_r,'r')

bian_imgbgr = cv2.merge([img_lap_b,img_lap_g,img_lap_r])
die_jia = cv2.addWeighted(imgold,0.5,bian_imgbgr,0.5, 0)
show(die_jia,'bian_bgr')

