import numpy as np
import cv2
import os


def Masking(img, center_point, radius, color_value, outter=True):
    # 将指定区域染成指定颜色
    img = img.copy()
    # 染成圆形
    # 参数图像，圆心坐标，半径，圆以外还是圆以内
    # 确定图像shape
    height, width = img.shape[:2]
    # 生成元素点xy坐标
    y, x = np.ogrid[:height, :width]

    # 计算每个点到圆心的距离的平方
    dist_from_center = (x - center_point[0]) ** 2 + (y - center_point[1]) ** 2

    # 将距离圆心距离小于半径的点的遮罩值设为指定颜色 单通道
    if outter == True:
        img[dist_from_center >= radius ** 2] = color_value
    else:
        img[dist_from_center <= radius ** 2] = color_value
    return img


def selfDefine(img, threshod):
    img = img.copy()
    # 自定义二值化
    img[img > threshod] = 255
    img[img <= threshod] = 0

    return img


def save_img(filename, img):
    file_path = "./imgProcessing" + f"/{filename}.png"
    cv2.imwrite(file_path, img)


def picture_deal(file_path_name):

# path = "./findScratch/ng1.png"

    img = cv2.imread(file_path_name,1)
    save_img('img', img)

    img_pro1_gray = img.copy()
    img_pro1_gray = cv2.cvtColor(img_pro1_gray, cv2.COLOR_BGR2GRAY)
    save_img('img_pro1_gray', img_pro1_gray)

    # 直方图均衡化
    img_pro2_hist = cv2.equalizeHist(img_pro1_gray)
    save_img('img_pro2_hist', img_pro2_hist)

    # 取反
    img_pro3_bitwise = cv2.bitwise_not(img_pro2_hist)
    save_img('img_pro3_bitwise', img_pro3_bitwise)

    # 高斯模糊
    img_pro4_gaussian = cv2.GaussianBlur(img_pro3_bitwise, ksize=(3, 3), sigmaX=0)
    save_img('img_pro4_gaussian', img_pro4_gaussian)

    contours, hierarchy = cv2.findContours(img_pro4_gaussian,
                                           cv2.RETR_TREE,
                                           cv2.CHAIN_APPROX_SIMPLE)
    # 画图
    img_pro5_contours = img.copy()
    for i in range(len(contours)):
        x, y, w, h = cv2.boundingRect(contours[i])

        # 用面积和周长判断两个圆
        area = cv2.contourArea(contours[i])
        if area > 700 and area < 110000:
            # 计算圆 圆心及半径
            x_circle = x + w / 2
            y_circle = y + h / 2
            radius = w / 2 + 7
            cv2.circle(img_pro5_contours, (int(x_circle), int(y_circle)), int(radius), (0, 0, 0), -1)

        if area > 110000:
            img_x_circle = x + w / 2
            img_y_circle = y + h / 2
            img_radius = w / 2

            # 存储大圆的中心及半径
            center_ = (img_x_circle, img_y_circle, img_radius)
            big_circle = [img_x_circle, img_y_circle, img_radius]

    save_img(f'img_pro5_contours', img_pro5_contours)

    # 去掉大圆及以外的图像
    # for i in range(40,60,2): #52
    radius = center_[2] - 54
    img_pro6_mask = Masking(img_pro5_contours.copy(), center_[:2], radius, 0)
    save_img(f'img_pro6_mask', img_pro6_mask)

    # img_pro7_gray = img_pro6_mask.copy()
    img_pro7_gray = cv2.cvtColor(img_pro6_mask, cv2.COLOR_BGR2GRAY)
    save_img('img_pro7_gray', img_pro7_gray)

    # 直方图均衡化
    img_pro8_hist = cv2.equalizeHist(img_pro7_gray)
    save_img('img_pro8_hist', img_pro8_hist)

    # 取反
    img_pro9_bitwise = cv2.bitwise_not(img_pro8_hist)
    save_img('img_pro9_bitwise', img_pro9_bitwise)

    # 自定义二值化
    # for i in range(1,10):#1
    img_pro10_define_threshold = selfDefine(img_pro9_bitwise, 5)
    save_img(f'img_pro10_define_threshold', img_pro10_define_threshold)

    # 去掉边缘颜色
    # for j in range(60,80): #65
    # 中心圆
    radius_inner = 69
    img_pro11_mask = Masking(img_pro10_define_threshold,
                             center_[:2], radius_inner, 255, False)
    save_img(f'img_pro11_mask', img_pro11_mask)

    # for j in range(139,160,1): #139
    # 最大圆
    radius_outter = 139
    img_pro12_mask = Masking(img_pro11_mask,
                             center_[:2], radius_outter, 255, True)
    save_img(f'img_pro12_mask', img_pro12_mask)

    contours, hierarchy = cv2.findContours(img_pro12_mask,
                                           cv2.RETR_TREE,
                                           cv2.CHAIN_APPROX_SIMPLE)


    # 画图
    img_pro13_contours = img.copy()

    for j in range(len(contours)):
        # 计算面积
        area = cv2.contourArea(contours[j])

        # 计算弧长
        arc_length = cv2.arcLength(contours[j], True)
        # 获取轮廓的近似多边形表示
        epsilon = 0.01 * cv2.arcLength(contours[j], True)
        approx = cv2.approxPolyDP(contours[j], epsilon, True)
        if area > 30:
            if arc_length < 1700:
                # 绘制多边形轮廓
                cv2.polylines(img_pro13_contours, [approx], True, (0, 0, 255), 2)

    save_img(f'img_pro13_contours', img_pro13_contours)

    # file_path = os.path.dirname(file_path_name)
    file_name = os.path.basename(file_path_name).split('.')[0]
    save_img(f'{file_name}',img_pro13_contours)


if __name__ == "__main__":
    file_path = "./findScratch"
    num = 1
    for each_file in os.listdir(file_path):
        each_file_path = os.path.join(file_path,each_file)
        picture_deal(each_file_path)




