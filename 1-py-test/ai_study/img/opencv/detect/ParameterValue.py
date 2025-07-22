# -*- coding: utf-8 -*-
"""--------------------------------------------
Author: fenglei
Project->file: opencv -> 1
IDE: PyCharm
Creation time: 2024/6/17 17:10
--------------------------------------------"""
import os
import shutil

import cv2
import numpy as np


class ParameterValue:
    def __init__(self, path):
        self.image = cv2.imread(path)
        self.debug = True
        self.yolos_black = []
        self.yolos_white = []
        self.max_y = 0

    def show(self, title, img):
        if self.debug:
            path = os.path.join('./debug', title + '.jpg')
            cv2.imwrite(path, img)

    def imageProcessing(self, img, thresh_num=30, flag_not=True, open_num=5, close_num=8):
        img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # 取灰度图
        self.show(f'img_gray{thresh_num}', img)
        # clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
        # img = clahe.apply(img)  # 直方图均衡化
        # self.show(f'img_cl{thresh_num}',img)
        # img = cv2.blur(img, (5, 5)) # 均值滤波 降噪
        # self.show(f'img_blur{thresh_num}',img)
        _, img = cv2.threshold(img, thresh_num, 255, cv2.THRESH_BINARY)  # 二值化
        self.show(f'img_thresh{thresh_num}', img)
        if flag_not:
            img = cv2.bitwise_not(img)  # 取反
            self.show(f'img_not{thresh_num}', img)
        if open_num != 0:
            kernel = np.ones((open_num, open_num), np.uint8)
            img = cv2.morphologyEx(img, cv2.MORPH_OPEN, kernel, iterations=2)  # 开运算 去掉噪声
            self.show(f'img_open{thresh_num}', img)
        if close_num != 0:
            kernel = np.ones((close_num, close_num), np.uint8)
            img = cv2.morphologyEx(img, cv2.MORPH_CLOSE, kernel, iterations=2)  # 闭运算 填白色内的黑坑
            self.show(f'img_close{thresh_num}', img)
        return img

    def edgeDetection(self, img):
        img = cv2.Canny(img, 100, 200)
        self.show('img_canny', img)
        contours, _ = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        img = self.image.copy()
        for i in range(len(contours)):  # 把边框画出来看看
            img = cv2.drawContours(img, contours, i, (0, 0, 255), 3)
        self.show('img_contours', img)
        return contours

    def widthFiltering(self, contours, max_h, min_h=0, flag=True):
        yolos = []
        for contour in contours:
            yolo = cv2.boundingRect(contour)  # 返回外界矩形x,y,w,h
            if min_h < yolo[3] < max_h:
                yolos.append(yolo)
        yolos.sort(key=lambda x: x[0])
        if flag:
            yolos.pop(0)
            yolos.pop()
        return yolos

    def plotting(self, img, yolos, title):
        for yolo in yolos:
            img = cv2.rectangle(img, yolo, (0, 255, 255), 3)
        self.show(title, img)

    def kjnovaClipper(self, img):
        max_y = max(self.yolos_black, key=lambda x: x[1])[1]
        min_h = min(self.yolos_black, key=lambda x: x[3])[3] - 10
        img = img[max_y:max_y + min_h, ...]
        self.show('img_white', img.copy())
        self.max_y = max_y - 10
        return img

    def yolos_add(self, yolos, x, y):
        for i in range(len(yolos)):
            yolos[i] = tuple(
                yolos[i][j] + x if j == 0 else yolos[i][j] + y if j == 1 else yolos[i][j] for j in range(4))

    def errorProcess(self, img):
        img = cv2.copyMakeBorder(img, 10, 10, 0, 0, cv2.BORDER_CONSTANT, value=(0, 0, 0))
        insert_yolos = []
        for i in range(len(self.yolos_white)):
            k = self.yolos_white[i]
            if k[2] > 150:
                img_err = img[..., k[0]:k[0] + k[2], :]
                self.show('img_err', img_err)
                img_err = self.imageProcessing(img_err, 140, False, 0, 0)
                img_err = cv2.bitwise_not(img_err)
                kernel = np.ones((5, 5), np.uint8)
                img = cv2.morphologyEx(img, cv2.MORPH_OPEN, kernel, iterations=2)  # 开运算
                img_err = cv2.bitwise_not(img_err)
                # self.show('img_err',img_err)
                contours = self.edgeDetection(img_err)
                yolos = self.widthFiltering(contours, 300, 200, False)
                self.plotting(img, yolos, 'img_yplos_error')  # 画出白色的框，观察是否有问题（中间有两个框有问题）
                self.yolos_add(yolos, k[0], 0)
                insert_yolos.append((i, yolos))
        index = 0
        for i, yolos in insert_yolos:
            self.yolos_white[i + index] = yolos[0]
            self.yolos_white.insert(i + 1 + index, yolos[1])
            index += 1

    def draw_line(self, x1, y1, x2, y2, h, txt, color):
        self.image = cv2.line(self.image, (x1, y1 + h),
                              (x2, y1 + h),
                              color, 5)
        self.image = cv2.line(self.image, (x1, y1),
                              (x1, y1 + h),
                              color, 5)
        self.image = cv2.line(self.image, (x2, y2),
                              (x2, y1 + h),
                              color, 5)
        cv2.putText(self.image, "{:.3f}cm".format(txt), ((x2 + x1) // 2 - 50, y1 + h - 20),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                    color, 2)

    def draw(self):
        self.yolos_add(self.yolos_white, 0, self.max_y)
        for i in range(5):
            yolos_w = self.yolos_white[i * 20:(i + 1) * 20]
            yolos_b = self.yolos_black[i * 19:(i + 1) * 19]

            # 1。总宽w
            w = (yolos_w[-1][0] - yolos_w[0][0] + yolos_w[-1][2]) * 0.006
            self.draw_line(yolos_w[0][0], yolos_w[0][1], yolos_w[-1][0] + yolos_w[-1][2],
                           yolos_w[-1][1], 700, w, (255, 0, 0))

            # 2。全间距pt
            pt = (yolos_b[-1][0] - yolos_b[0][0] + (yolos_b[-1][2] - yolos_b[0][2]) // 2) * 0.006
            self.draw_line(yolos_b[0][0] + yolos_b[0][2] // 2, yolos_b[0][1],
                           yolos_b[-1][0] + yolos_b[-1][2] // 2, yolos_b[-1][1], 600, pt, (0, 255, 0))

            # 3。间距
            k = (yolos_b[11][0] - yolos_b[10][0] + (yolos_b[11][2] - yolos_b[10][2]) // 2) * 0.006
            self.draw_line(yolos_b[10][0] + yolos_b[10][2] // 2, yolos_b[10][1], yolos_b[11][0] + yolos_b[11][2] // 2,
                           yolos_b[11][1], 100, k, (0, 0, 255))

            # 4。左边距
            m1 = (yolos_b[0][0] + yolos_b[0][2] // 2 - yolos_w[0][0]) * 0.006
            self.draw_line(yolos_w[0][0], yolos_w[0][1], yolos_b[0][0] + yolos_b[0][2] // 2,
                           yolos_b[0][1], 100, m1, (0, 255, 255))

            # 5。右边距
            m2 = (yolos_w[-1][0] + yolos_w[-1][2] - yolos_b[-1][0] - yolos_b[-1][2] // 2) * 0.006
            self.draw_line(yolos_b[-1][0] + yolos_b[-1][2] // 2, yolos_b[-1][1], yolos_w[-1][0] + yolos_w[-1][2],
                           yolos_w[-1][1], 100, m2, (0, 255, 255))

            # 6。线口长度s
            s = yolos_b[5][3] * 0.006
            self.image = cv2.line(self.image, (yolos_b[5][0] + yolos_b[5][2] // 2, yolos_b[5][1]),
                                  (yolos_b[5][0] + yolos_b[5][2] // 2, yolos_b[5][1] + yolos_b[5][3]),
                                  (255, 255, 0), 5)
            cv2.putText(self.image, "{:.3f}cm".format(s), (yolos_b[5][0] + yolos_b[5][2] // 2 + 10,
                                                           yolos_b[5][1] + yolos_b[5][3] // 2),
                        cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                        (255, 255, 0), 2)

        self.debug = True
        self.show('img_end', self.image)

    def detect(self):
        img = self.imageProcessing(self.image.copy())
        contours = self.edgeDetection(img)
        self.yolos_black = self.widthFiltering(contours, 300)
        self.plotting(self.image.copy(), self.yolos_black, 'img_yolos_black')  # 画出黑色的框，观察是否有问题
        white_img = self.kjnovaClipper(self.image.copy())
        white_img1 = white_img
        white_img = self.imageProcessing(white_img, 50, False, open_num=8, close_num=8)
        white_img = cv2.copyMakeBorder(white_img, 10, 10, 0, 0, cv2.BORDER_CONSTANT, value=(0, 0, 0))
        self.show('white_img1', white_img)
        white_contours = self.edgeDetection(white_img)
        self.yolos_white = self.widthFiltering(white_contours, 500)
        # self.plotting(white_img1,self.yolos_white,'img_yolos_white') # 画出白色的框，观察是否有问题（中间有两个框有问题）
        self.errorProcess(white_img1)
        self.plotting(white_img1, self.yolos_white, 'img_yolos_white')  # 画出白色的框，观察是否有问题
        self.draw()


if __name__ == '__main__':
    if os.path.exists('./debug'):
        shutil.rmtree('./debug')
        os.makedirs('./debug')

    img_path = r'D:/Data/Test/img/detect0/img1.png'
    pv = ParameterValue(img_path)
    # pv.show('img_old',pv.image)展示原图
    pv.detect()
