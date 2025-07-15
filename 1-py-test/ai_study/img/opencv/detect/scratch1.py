import cv2
import os
import numpy as np
def show(img,title=''):
    cv2.imshow(title,img)
    cv2.waitKey(0)
def preprocess_image(image):
    # 灰度化
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    # 平滑处理，使用高斯模糊去除噪声
    blurred_image = cv2.GaussianBlur(gray_image, (5, 5), 0)
    cv2.imwrite('output/blurred_image.jpg',blurred_image)
    # 边缘增强，例如使用CLAHE
    clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
    enhanced_image = clahe.apply(blurred_image)

    return enhanced_image
# def center_circles(img):
#     image = img.copy()
#     height, width = image.shape[:2]
#     center_x, center_y = width // 2, height // 2
#     height1, width1 = img.shape[:2]
#     center_x1, center_y1 = width // 2, height // 2
#     radius1 = min(height1, width1) // 4  # 例如，取图像对角线长度的1/4作为半径
#     # 定义矩形区域，以图像中心为中心
#     rect_top_left1 = (center_x1 - radius1, center_y1 - radius1)
#     rect_bottom_right1 = (center_x1 + radius1, center_y1 + radius1)
#
#     # 提取中心区域
#     center_region1 = image[rect_top_left1[1]:rect_bottom_right1[1], rect_top_left1[0]:rect_bottom_right1[0]]
#
#
#     # 定义中心区域的半径
#     radius = min(height, width) // 4  # 例如，取图像对角线长度的1/4作为半径
#     # 定义矩形区域，以图像中心为中心
#     rect_top_left = (center_x - radius, center_y - radius)
#     rect_bottom_right = (center_x + radius, center_y + radius)
#
#     # 提取中心区域
#     center_region = image[rect_top_left[1]:rect_bottom_right[1], rect_top_left[0]:rect_bottom_right[0]]
#     # 转换为灰度图像
#     gray_region = cv2.cvtColor(center_region, cv2.COLOR_BGR2GRAY)
#
#     # 应用阈值分割白色区域
#     _, thresh = cv2.threshold(gray_region, 127, 255, cv2.THRESH_BINARY_INV)
#     edges = cv2.Canny(image, 50, 150)
#
#     # 应用霍夫变换检测圆形
#     # circles = cv2.HoughCircles(edges, cv2.HOUGH_GRADIENT, dp=1, minDist=20, param1=50, param2=30, minRadius=0, maxRadius=0)
#     circles = cv2.HoughCircles(thresh, cv2.HOUGH_GRADIENT, dp=1, minDist=20, param1=50, param2=30, minRadius=0,
#                                maxRadius=0)
#     # 如果检测到圆形
#     if circles is not None:
#         circles = np.uint16(np.around(circles))
#         for i in circles[0, :]:
#             # 获取圆心坐标和半径
#             center = (i[0], i[1])
#             radius = i[2]
#
#             # 在原始图像上绘制圆心和圆
#             cv2.circle(center_region1, center, radius, (0, 255, 0), 2)
#             cv2.circle(center_region1, center, 5, (0, 0, 255), 3)  # 绘制圆心标记
#             show(center_region1,'image')
def adaptive_thresholding(image):
    # 使用Otsu's方法计算阈值
    image_copy = image.copy()
    _, thresholded_image = cv2.threshold(image, 80, 255, cv2.THRESH_BINARY_INV)
    x,y,r = 214,232,70
    cv2.circle(thresholded_image, (x, y), r, 0, thickness=-1)

    return thresholded_image


def extract_scratch(image,thresholded_image1):
    # 应用动态阈值
    thresholded_image = adaptive_thresholding(image)

    # 形态学操作，如腐蚀和膨胀，以分离划痕
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    eroded_image = cv2.erode(thresholded_image, kernel, iterations=2)
    dilated_image = cv2.dilate(eroded_image, kernel, iterations=2)


    edges = cv2.Canny(dilated_image, 50, 150)
    contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    # 遍历每个轮廓，找出圆的边界
    w1 = []
    h1 = []
    xmin1 =[]
    ymin1 = []
    xmin, xmax, ymin, ymax = float('inf'), 0, float('inf'), 0
    for contour in contours:
        # 获取轮廓的边界框
        # 获取轮廓的外接矩形
        x, y, w, h = cv2.boundingRect(contour)
        # 更新 xmin, xmax, ymin, ymax
        xmin = min(xmin, x)
        xmax = max(xmax, x + w)
        ymin = min(ymin, y)
        ymax = max(ymax, y + h)
        w1.append(w)
        h1.append(h)
        xmin1.append(xmin)
        ymin1.append(ymin)
    # #图像截取只剩下圆环边框
    imge2=thresholded_image1[ymin:ymax,xmin:xmax]
    imge1 = dilated_image[ymin:ymax, xmin:xmax]
    # 绘制掩膜
    canvas = np.zeros_like(imge1)
    height, width = imge1.shape[:2]
    center_x, center_y = width // 2, height // 2
    radius = 137
    # 在画布上绘制一个圆形作为掩膜
    mask = np.zeros_like(imge1[:, :])
    cv2.circle(mask, (center_x, center_y), radius, 255, -1)
    # 将掩膜应用到原始图像上
    result = cv2.bitwise_and(imge1, imge1, mask=mask)

    # 在全黑画布上绘制提取出的圆形部分
    canvas[mask > 0] = result[mask > 0]



    return dilated_image,canvas,xmin,ymin,w, h,imge2
def contour_scratch(canvas,xmin,ymin,image,w, h,img2):

    contours, _ = cv2.findContours(canvas, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    extract_scratch= []
    for contour in contours:
        area = cv2.contourArea(contour)
        if 10 <= area<500:
            extract_scratch.append(contour)
    # image[ymin:ymin + h, xmin:xmin + w] = canvas
    cv2.drawContours(img2, extract_scratch, -1, (0, 0,255), 2)

    # image[ymin[i]:ymin[i] + h[i], xmin[i]:xmin[i] + w[i]] = img2
    show(img2,'image')
    return img2
def process_images(image_paths, output_dir,output_finaimg):
    for i,image_path in enumerate(os.listdir(image_paths)):
        image = cv2.imread(f'./img/{image_path}')
        preprocessed_image = preprocess_image(image)

        thresholded_image = adaptive_thresholding(preprocessed_image)
        # center_circles(image)
        scratch_image,canvas,xmin,ymin,w, h,imge2 = extract_scratch(thresholded_image,image)
        if i==10:
            img2 = contour_scratch(canvas, xmin, ymin, image, w, h, imge2)
            image[ymin-2:ymin + h, xmin:xmin + w] = img2
        else:
            img2 = contour_scratch(canvas,xmin,ymin,image,w, h,imge2)
            image[ymin:ymin + h, xmin:xmin + w] = img2

        output_path = os.path.join(output_finaimg, os.path.basename(image_path))
        cv2.imwrite(output_path, image)
        # 保存处理后的图像
        output_path = os.path.join(output_dir, os.path.basename(image_path))
        cv2.imwrite(output_path, scratch_image)
if __name__=='__main__':
    image_paths = r'./img'
    outputdir = r'./display'
    output_finaimg = r'./output'
    process_images(image_paths,outputdir,output_finaimg)