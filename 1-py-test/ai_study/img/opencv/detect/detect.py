# -*- coding: utf-8 -*-
"""--------------------------------------------
Author: lilu
Project->file: opencv -> 1
IDE: PyCharm
Creation time: 2023/11/20 11:10
--------------------------------------------"""
import cv2
import numpy as np
import shutil
import os


class ParameterValue:
    def __init__(self, image, image1, image2, pixel_precision):
        self.image = image
        self.image1 = image1
        self.image2 = image2
        self.pixel_precision = pixel_precision
        self.debug = True

    def show(self, path, img):
        if self.debug:
            cv2.imwrite(path, img)

    def imageProcessing(self, image):
        # 转换为灰度图
        gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        # 对灰度图进行直方图均衡化
        equalized_image = cv2.equalizeHist(gray_image)
        self.show('./debug/1-equalized_image.jpg',equalized_image)
        # 取反
        inverted_image = cv2.bitwise_not(equalized_image)
        self.show('./debug/2-inverted_image.jpg', inverted_image)
        # 对灰度图进行高斯模糊. 尝试使用双边滤波，看看怎么样
        gray_blurred = cv2.GaussianBlur(inverted_image, (3, 3), 0.5, 0.5)
        self.show('./debug/3-gray_blurred.jpg', gray_blurred)
        return gray_blurred

    def corrosionExpansion(self, gray_blurred, i, j):
        '''
        腐蚀膨胀
        :param gray_blurred:
        :param i:
        :param j:
        :return:
        '''
        kernel = cv2.getGaussianKernel(3, 3)
        eroded = cv2.erode(gray_blurred, kernel, iterations=i)
        self.show('./debug/4-erode.jpg',eroded)
        dilated = cv2.dilate(eroded, kernel, iterations=j)
        self.show('./debug/5-dilate.jpg', eroded)
        return kernel, dilated

    def imageFill(self, gray_blurred):
        '''
        阈值处理
        :param gray_blurred:
        :return:
        '''
        kernel, dilated = self.corrosionExpansion(gray_blurred, 3, 2)
        ret, thresholded = cv2.threshold(dilated, 230, 255, cv2.THRESH_TOZERO)
        self.show('./debug/6-thresholded.jpg',thresholded)
        eroded_again = cv2.erode(thresholded, kernel, iterations=1)
        self.show('./debug/7-eroded_again.jpg', eroded_again)
        kernel = np.ones((18, 18), np.uint8)
        opening = cv2.morphologyEx(eroded_again, cv2.MORPH_CLOSE, kernel)
        self.show('./debug/8-opening.jpg', opening)
        return opening

    def edgeDetection(self, opening):
        '''
        使用Sobel算子检测边缘
        :param opening:
        :return:
        '''
        sobel_x = cv2.Sobel(opening, cv2.CV_64F, 1, 0, ksize=3)
        sobel_y = cv2.Sobel(opening, cv2.CV_64F, 0, 1, ksize=3)
        abs_sobel_x = cv2.convertScaleAbs(sobel_x)
        abs_sobel_y = cv2.convertScaleAbs(sobel_y)
        sobel_edges = cv2.addWeighted(abs_sobel_x, 0.5, abs_sobel_y, 0.5, 0)
        self.show('./debug/9-sobel_edges.jpg', sobel_edges)
        _, binary_image = cv2.threshold(sobel_edges, 50, 255, cv2.THRESH_BINARY)
        self.show('./debug/9-binary_image.jpg', binary_image)
        contours, _ = cv2.findContours(binary_image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        return contours

    def widthFiltering(self, image, contours, min_area_threshold):
        '''
        初步检测
        :param image:
        :param contours:
        :param min_area_threshold:
        :param i:
        :return:
        '''
        y_values = []
        x_values = []
        h_values = []
        w_values = []
        for contour in contours:
            area = cv2.contourArea(contour)
            # 我最开始的时候，怎么知道要用1000来过滤？
            # img = cv2.drawContours(oldImg.copy(), contours, i, (0,0,255), 3)
            # 或者打印出来
            # print(area)
            if area > min_area_threshold:
                area_cm2 = area * pixel_precision * pixel_precision
                x, y, w, h = cv2.boundingRect(contour)
                width_cm = w * pixel_precision
                if width_cm > 0.50:
                    x_values.append(x)
                    y_values.append(y)
                    h_values.append(h)
                    w_values.append(w)
                    # image1 = self.plotting(image,x, y, w, h, area_cm2, width_cm)
        return x_values, y_values, w_values, h_values, image1

    def rectangleFiltering(self, image, x_values, contours, min_area_threshold):
        '''
        轮廓检测: 在原图中进行轮廓检测
        :param x_values:
        :param contours:
        :param min_area_threshold:
        :return:
        '''
        x_min = min(x_values)
        x_max = max(x_values)
        new_contour = []
        blackRectangle = []
        for i in range(len(contours)):
            contour = contours[i]
            area = cv2.contourArea(contour)
            if area > min_area_threshold:
                x, y, w, h = cv2.boundingRect(contour)
                if x != x_min and x != x_max:
                    area_cm2 = area * pixel_precision * pixel_precision
                    width_cm = w * pixel_precision
                    if width_cm > 0.50 and x > 0 and x < image.shape[1] - w:  # 检查左上角不在左边并且不在最右边
                        blackRectangle.append([x, y, w, h])
                        new_contour.append(contour)
                        image2 = self.plotting(image, x, y, w, h, area_cm2, width_cm)
        cv2.imwrite('./debug/img1.jpg', image2)
        return new_contour, image2, blackRectangle

    def plotting(self, image, x, y, w, h, area_cm2, width_cm):
        '''
        绘制
        :param image:
        :param x:
        :param y:
        :param w:
        :param h:
        :param area_cm2:
        :param width_cm:
        :return:
        '''
        cv2.rectangle(image, (x, y), (x + w, y + h), (0, 255, 0), 2)
        cv2.putText(image, "A:{:.3f} cm^2".format(area_cm2), (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX,
                    0.7, (0, 255, 0), 2)
        cv2.putText(image, "W:{:.3f} cm".format(width_cm), (x, y + h + 20),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0), 2)
        height_cm = h * pixel_precision
        center_x = x + w // 2
        center_y = y + h // 2
        cv2.putText(image, "H:{:.3f} cm".format(height_cm), (center_x, center_y + 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0), 2)
        return image

    def kjnovaClipper(self, image1, y_values, h_values):
        '''
        裁剪
        :param y_values:
        :param h_values:
        :return:
        '''
        y_min = max(y_values)
        y_max = y_min + max(h_values)
        cropped_image = image1[y_min:y_max, :]
        return cropped_image, y_min

    def cropImageProcessing(self, image, image1, y_min):
        '''
        处理剪切后的图
        :param image:
        :param image1:
        :param y_min:
        :return:
        '''
        whiteRectangle = []
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        _, binary_image = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
        self.show('./debug/a1-binary_image.jpg', binary_image)
        kernel = np.ones((5, 5), np.uint8)
        binary_image = cv2.erode(binary_image, kernel, iterations=1)
        self.show('./debug/a2-erode.jpg', binary_image)
        binary_image = cv2.dilate(binary_image, kernel, iterations=1)
        self.show('./debug/a3-dilate.jpg', binary_image)
        contours, _ = cv2.findContours(binary_image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        for contour in contours:
            x, y, w, h = cv2.boundingRect(contour)
            area = cv2.contourArea(contour)
            if w > 30 and w < 200 and h > 200:
                y = y + y_min
                whiteRectangle.append([x, y, w, h])
                cv2.rectangle(image1, (x, y), (x + w, y + h), (0, 255, 0), 2)
                x_center = x + w // 2
                y_center = y + h // 2
                w = w * 0.006
                cv2.putText(image1, "W:{}".format(w), (x_center, y_center + 50), cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                            (255, 0, 0), 2)
                h = h * 0.006
                cv2.putText(image1, "H:{}".format(h), (x_center, y_center + 100), cv2.FONT_HERSHEY_SIMPLEX,
                            0.7, (255, 0, 0), 2)
                area = area * 0.006
                cv2.putText(image1, "A:{}".format(area), (x_center, y_center), cv2.FONT_HERSHEY_SIMPLEX, 0.7,
                            (255, 0, 0), 2)
        return image1, whiteRectangle

    def centerPointDistance(self, image, blackRectangle):
        blackData = sorted(blackRectangle)[0:2]
        x_left_center = blackData[0][0] + blackData[0][2] // 2
        y_left_center = blackData[0][1] + blackData[0][3] // 2
        x_right_center = blackData[1][0] + blackData[1][2] // 2
        y_right_center = blackData[1][1] + blackData[1][3] // 2
        distance = (x_right_center - x_left_center) * 0.006
        cv2.line(image, (x_left_center, y_left_center), (x_right_center, y_right_center), (0, 255, 0), 2)
        cv2.putText(image, "{:.3f}".format(distance),
                    (x_left_center + (x_right_center - x_left_center) // 2, y_left_center),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.5,
                    (0, 255, 255), 2)
        return image

    def leftdistance(self, image, whiteRectangle, blackRectangle):
        whiteData = sorted(whiteRectangle)[0]
        blackData = sorted(blackRectangle)[0]
        x_white_left = whiteData[0]
        y_white_left = whiteData[1] + blackData[3] // 2
        x_black_center = blackData[0] + blackData[2] // 2
        y_black_center = blackData[1] + blackData[3] // 2
        distance = (x_black_center - x_white_left) * 0.006
        cv2.line(image, (x_white_left, y_black_center + 50), (x_black_center, y_black_center + 50), (0, 255, 0), 2)
        cv2.putText(image, "{:.3f}".format(distance),
                    (x_white_left + int((x_black_center - x_white_left)) // 2, y_black_center + 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.5,
                    (0, 255, 0), 2)
        return image

    def rightdistance(self, image, whiteRectangle, blackRectangle):
        whiteData = sorted(whiteRectangle)[19]
        blackData = sorted(blackRectangle)[18]
        x_white_left = whiteData[0] + whiteData[2]
        y_white_left = whiteData[1] + blackData[3] // 2
        x_black_center = blackData[0] + blackData[2] // 2
        y_black_center = blackData[1] + blackData[3] // 2
        distance = (x_white_left - x_black_center) * 0.006
        cv2.line(image, (x_black_center, y_black_center + 50), (x_white_left, y_black_center + 50), (0, 255, 0), 2)
        cv2.putText(image, "{:.3f}".format(distance),
                    (x_black_center + int((x_black_center - x_white_left)) // 2, y_black_center + 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.5,
                    (0, 255, 0), 2)
        return image

    def rectangleHeight(self, image, blackRectangle):
        blackData = sorted(blackRectangle)[2]
        x_black_center = blackData[0] + blackData[2] // 2
        y_black_lower = blackData[1]
        y_black_upper = blackData[1] + blackData[3]
        h = blackData[3] * 0.006
        cv2.line(image, (x_black_center, y_black_lower), (x_black_center, y_black_upper), (0, 255, 0), 2)
        cv2.putText(image, "{:.3f}".format(h), (x_black_center, y_black_lower + blackData[3] // 2),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.5,
                    (0, 255, 0), 2)
        return image

    def totalSpacing(self, image, whiteRectangle, blackRectangle):
        whiteData = sorted(whiteRectangle)[1:18]
        blackData = sorted(blackRectangle)[:19]
        white_distance = 0
        black_distance = 0
        for i in range(len(whiteData)):
            data = whiteData[i][2]
            if i == 1 or i == len(whiteData):
                data = data // 2
                white_distance += data
            else:
                white_distance += data
        for j in range(len(blackData)):
            data = blackData[i][2]
            if i == 1 or i == len(blackData):
                data = data // 2
                black_distance += data
            else:
                black_distance += data
        distance = (white_distance + black_distance) * 0.006
        black_left_center = blackData[0][0] + blackData[0][2] // 2
        black_right_center = blackData[18][0] + blackData[18][2] // 2
        y = blackData[0][1] + blackData[0][3] // 2
        cv2.line(image, (black_left_center, y), (black_right_center, y), (0, 150, 150), 2)
        cv2.putText(image, "{:.3f}".format(distance), (black_left_center + (white_distance + black_distance) // 2, y),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.5,
                    (0, 150, 150), 2)
        return image

    def totalWidth(self, image, whiteRectangle, blackRectangle):
        whiteData = sorted(whiteRectangle)[:20]
        blackData = sorted(blackRectangle)[:19]
        white_distance = 0
        black_distance = 0
        for i in range(len(whiteData)):
            data = whiteData[i][2]
            white_distance += data
        for j in range(len(blackData)):
            data = blackData[j][2]
            black_distance += data
        distance = (white_distance + black_distance) * 0.006
        white_left_center = whiteData[0][0]
        white_right_center = whiteData[19][0] + whiteData[19][2]
        y = whiteData[0][1] + whiteData[0][3] // 2
        cv2.line(image, (white_left_center, y - 50), (white_right_center, y - 50), (150, 150, 0), 2)
        cv2.putText(image, "{:.3f}".format(distance),
                    (white_left_center + (white_distance + black_distance) // 2, y - 50),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.5,
                    (150, 150, 0), 2)
        return image

    def cropImageProcessing1(self, image):
        '''
        处理剪切后的图片
        :param image:
        :return:
        '''
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        _, binary_image = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
        kernel = np.ones((5, 5), np.uint8)
        binary_image = cv2.erode(binary_image, kernel, iterations=3)
        binary_image = cv2.dilate(binary_image, kernel, iterations=3)
        contours, _ = cv2.findContours(binary_image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        cropdata = []
        for contour in contours:
            x, y, w, h = cv2.boundingRect(contour)
            area = cv2.contourArea(contour)
            if w > 30 and w < 200 and h > 200:
                cropdata.append([x, y, w, h])
                cv2.rectangle(image, (x, y), (x + w, y + h), (0, 255, 0), 2)
                w = w * 0.006
                cv2.putText(image, "{:.3f}".format(w), (x, y + h // 2 + 50), cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                            (0, 255, 0), 2)
                h1 = h * 0.006
                cv2.putText(image, "{:.3f}".format(h1), (x, y + h // 2 + 100), cv2.FONT_HERSHEY_SIMPLEX,
                            1.0, (0, 255, 0), 2)
                area = area * 0.006
                cv2.putText(image, "{:.3f}".format(area), (x, y + h // 2), cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                            (0, 255, 0), 2)
        return image, cropdata

    def detect(self):
        # 处理原图
        gray_blurred = self.imageProcessing(image)
        opening = self.imageFill(gray_blurred)
        contours = self.edgeDetection(opening)  # contours没有过滤的轮廓 # x_values过滤后的轮廓x坐标
        min_area_threshold = 1000
        x_values, y_values, w_values, h_values, _ = self.widthFiltering(image, contours, min_area_threshold)
        new_contour, _, blackRectangle = self.rectangleFiltering(image1, x_values, contours, min_area_threshold)
        # 处理剪切后的图
        cropped_image, y_min = self.kjnovaClipper(image1, y_values, h_values)
        cv2.imwrite('./debug/img2.jpg', cropped_image)
        img_1, whiteRectangle = self.cropImageProcessing(cv2.imread('./debug/img2.jpg'), cv2.imread('./debug/img1.jpg'), y_min)
        cv2.imwrite('./debug/result.jpg', img_1)
        # 再次剪切
        whiteRectangl = []
        for i in range(len(whiteRectangle)):
            whiteRectangl.append(whiteRectangle[i][2])
        max_value = max(whiteRectangl)
        max_index = whiteRectangl.index(max_value)
        white = whiteRectangle[max_index]
        y_min = white[1]
        y_max = white[1] + white[3]
        x_min = white[0]
        x_max = white[0] + white[2]
        crop_image = image1[y_min:y_max, x_min:x_max]
        cv2.imwrite('./debug/img3.jpg', crop_image)
        img_2, cropdata = self.cropImageProcessing1(cv2.imread('./debug/img3.jpg'))
        cv2.imwrite('./debug/img4.jpg', img_2)
        # 处理剪切后的坐标
        x1 = cropdata[0][0] + white[0]
        y1 = cropdata[0][1] + white[1]
        x2 = cropdata[1][0] + white[0]
        y2 = cropdata[1][1] + white[1]
        cropdata[0][0] = x1
        cropdata[0][1] = y1
        cropdata[1][0] = x2
        cropdata[1][1] = y2
        whiteRectangle[max_index] = cropdata[0]
        whiteRectangle.insert(max_index, cropdata[1])
        # 绘图
        for i in range(len(whiteRectangle) // 20):
            for j in range(len(blackRectangle) // 19):
                whiteRectangle1 = sorted(whiteRectangle)[i * 20:(i + 1) * 20]
                blackRectangle1 = sorted(blackRectangle)[i * 19:(i + 1) * 19]
                spacing = self.centerPointDistance(image2, blackRectangle1)  # 间距
                leftDistance = self.leftdistance(image2, whiteRectangle1, blackRectangle1)  # 左边距
                rightDistance = self.rightdistance(image2, whiteRectangle1, blackRectangle1)  # 右边距
                recHeight = self.rectangleHeight(image2, blackRectangle1)  # 线口长度
                distance = self.totalSpacing(image2, whiteRectangle1, blackRectangle1)  # 全间距
                overallWidth = self.totalWidth(image2, whiteRectangle1, blackRectangle1)  # 总宽
                cv2.imwrite('./debug/b0-spacing.jpg', spacing)
                cv2.imwrite('./debug/b1-leftDistance.jpg', leftDistance)
                cv2.imwrite('./debug/b2-rightDistance.jpg', rightDistance)
                cv2.imwrite('./debug/b3-recHeight.jpg', recHeight)
                cv2.imwrite('./debug/b4-distance.jpg', distance)
                cv2.imwrite('./debug/b5-overallWidth.jpg', overallWidth)
            print("结束一轮")


if __name__ == '__main__':
    image = cv2.imread(r'D:/Data/Test/img/detect0/img1.png')
    # cv2.imwrite('temp_image.jpg', image)
    # shutil.copy('temp_image.jpg', 'img/1/img2.png')
    # shutil.copy('temp_image.jpg', 'img/1/img3.png')
    # os.remove('temp_image.jpg')
    # image1 = cv2.imread('img/1/img2.png')
    # image2 = cv2.imread('img/1/img3.png')
    image2 = image.copy()
    image1 = image.copy()
    pixel_precision = 0.006
    # 为什么要用3个原图？
    img = ParameterValue(image, image1, image2, pixel_precision)
    img.detect()
