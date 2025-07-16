# -*- coding = utf-8 -*-
# @Time : 2024/9/13 15:51
# @Author :zc
# @File : data_augmentation.py
# @Software: PyCharm
import numpy as np
import cv2
import os
import random


# 读取图片和标签文件
def read_img_label(imagedir, labeldir):
    images = []
    labels = []

    # 获取图片文件和标签文件列表
    image_files = sorted(
        [f for f in os.listdir(imagedir) if f.lower().endswith(('.png', '.jpg', '.jpeg', '.bmp', '.tiff'))])
    label_files = sorted([f for f in os.listdir(labeldir) if f.lower().endswith('.txt')])

    # 提取文件名（去除扩展名）
    image_file_set = set(os.path.splitext(f)[0] for f in image_files)
    label_file_set = set(os.path.splitext(f)[0] for f in label_files)

    # 确保每个图片都有对应的标签
    common_files = image_file_set.intersection(label_file_set)
    common_files = sorted(list(common_files))

    if not common_files:
        raise ValueError("图片目录和标签目录之间没有匹配的文件。")

    for base_name in common_files:
        # 读取图片
        img_path = os.path.join(imagedir, base_name + '.jpg')  # 根据实际情况调整扩展名
        img = cv2.imread(img_path)
        if img is None:
            print(f"警告: 无法读取图片 {img_path}")
            continue

        # 读取标签
        label_path = os.path.join(labeldir, base_name + '.txt')
        boxes = []
        try:
            with open(label_path, 'r') as file:
                for line in file:
                    info = line.strip().split()
                    if len(info) != 5:
                        print(f"警告: 标签格式不正确 {label_path}")
                        continue
                    boxes.append([int(info[0]), float(info[1]), float(info[2]), float(info[3]), float(info[4])])
        except Exception as e:
            print(f"读取标签文件 {label_path} 时出错: {e}")
            continue
        labels.append(boxes)
        images.append(img)
    datas = list(zip(images, labels))
    return datas


# voc转yolo
def voc2yolo(voc_boxes, ih, iw):
    if isinstance(voc_boxes, list):
        voc_boxes = np.array(voc_boxes)
    yolo_boxes = voc_boxes.astype(float).copy()
    yolo_boxes[..., 1] = (voc_boxes[..., 1] + voc_boxes[..., 3]) / 2 / iw
    yolo_boxes[..., 2] = (voc_boxes[..., 2] + voc_boxes[..., 4]) / 2 / ih
    yolo_boxes[..., 3] = (voc_boxes[..., 3] - voc_boxes[..., 1]) / iw
    yolo_boxes[..., 4] = (voc_boxes[..., 4] - voc_boxes[..., 2]) / ih
    # 将结果转为浮点数，保留6位小数
    yolo_boxes = np.around(yolo_boxes, decimals=6)
    return yolo_boxes


# yolo转成voc
def yolo2voc(yolo_boxes, ih, iw):
    if isinstance(yolo_boxes, list):
        yolo_boxes = np.array(yolo_boxes)
    voc_boxes = yolo_boxes.copy()
    voc_boxes[..., 1] = yolo_boxes[..., 1] * iw - yolo_boxes[..., 3] * iw / 2
    voc_boxes[..., 2] = yolo_boxes[..., 2] * ih - yolo_boxes[..., 4] * ih / 2
    voc_boxes[..., 3] = yolo_boxes[..., 1] * iw + yolo_boxes[..., 3] * iw / 2
    voc_boxes[..., 4] = yolo_boxes[..., 2] * ih + yolo_boxes[..., 4] * ih / 2
    # 将坐标转换为整数
    voc_boxes = voc_boxes.astype(int)
    return voc_boxes


# 画矩形框并展示图片
def drawRectangle_show(image, boxes, title=''):
    if boxes is None:
        cv2.imshow(title, image)
        cv2.waitKey(0)
        return
    boxes = yolo2voc(boxes, *image.shape[:2])
    for box in boxes:
        class_name, x1, y1, x2, y2 = box
        cv2.rectangle(image, (x1, y1), (x2, y2), (0, 255, 0), 2)
    cv2.imwrite('./1.jpg',image)
    cv2.imshow(title, image)
    cv2.waitKey(0)


# 等比例图像缩放并填充
def letterbox(image, size=(640, 640), boxes=None):
    ih, iw = image.shape[:2]
    w, h = size

    if ih == h and iw == w:
        return image, boxes, 1, 0, 0, 0, 0  # image, boxes, ratio, padT, padB, padL, padR

    ratio = min(w / iw, h / ih)

    new_w = int(iw * ratio)
    new_h = int(ih * ratio)

    dw = (w - new_w) // 2
    dh = (h - new_h) // 2

    scale_img = cv2.resize(image, [new_w, new_h])

    padded_image = cv2.copyMakeBorder(scale_img, dh, h - dh - new_h, dw, w - dw - new_w, cv2.BORDER_CONSTANT,
                                      value=[114, 114, 114])
    voc_boxes = yolo2voc(boxes, ih, iw)
    padded_boxes = voc_boxes.copy()
    padded_boxes[:, 1] = voc_boxes[:, 1] * ratio + dw
    padded_boxes[:, 2] = voc_boxes[:, 2] * ratio + dh
    padded_boxes[:, 3] = voc_boxes[:, 3] * ratio + dw
    padded_boxes[:, 4] = voc_boxes[:, 4] * ratio + dh
    padded_boxes = voc2yolo(padded_boxes, h, w)
    return padded_image, padded_boxes, ratio, dh, h - dh - new_h, dw, w - dw - new_w


# letterbox缩放后的框重绘到原图
def yolo_box_to_origin_image(origin_img, letterbox_image, new_boxes, ratio, padT, padL):
    """
    将letterbox的图及其boxes，还原回原图，原boxes
    :param origin_img:
    :param new_boxes:
    :param ratio:letterbox时的缩放比例
    :param padT:
    :param padL:
    :return:
    """
    # boxes还原
    new_boxes = yolo2voc(new_boxes, *letterbox_image.shape[:2])
    origin_boxes = new_boxes.copy()
    classes = new_boxes[:, 0]
    origin_boxes[:, 1] = (new_boxes[:, 1] - padL) / ratio
    origin_boxes[:, 2] = (new_boxes[:, 2] - padT) / ratio
    origin_boxes[:, 3] = (new_boxes[:, 3] - padL) / ratio
    origin_boxes[:, 4] = (new_boxes[:, 4] - padT) / ratio
    # 标签转为yolo格式
    yolo_origin_boxes = voc2yolo(origin_boxes, *origin_img.shape[:2])
    return origin_img, yolo_origin_boxes, classes


# bgr转rgb
def bgr2rgb(image, boxes):
    rgb_image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    return rgb_image, boxes


# bgr转灰度图
def bgr2gray(image, boxes):
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    return gray_image, boxes


# 全局直方图均衡化
def global_histogram_equalization(image, boxes):
    b, g, r = cv2.split(image)
    b = cv2.equalizeHist(b)  # 直方图均衡化
    g = cv2.equalizeHist(g)
    r = cv2.equalizeHist(r)
    img_equ = cv2.merge([b, g, r])
    return img_equ, boxes


# 自适应(区域)直方图均衡化
def adaptive_histogram_equalization(image, boxes):
    b, g, r = cv2.split(image)
    clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
    b = clahe.apply(b)
    g = clahe.apply(g)
    r = clahe.apply(r)
    img_clahe = cv2.merge([b, g, r])
    return img_clahe, boxes


# 随机亮度调节
def random_brightness(image, boxes, rnd_min=0.5, rnd_max=1.5):
    # 生成随机亮度调整因子
    ratio = random.uniform(rnd_min, rnd_max)
    # # 调整图像的亮度，确保类型为 uint8，并将像素值限制在 0 到 255 之间
    image = np.clip(image * ratio, 0, 255).astype('uint8')
    return image, boxes, ratio


# 图像取反
def img_bitwise_not(image, boxes):
    inverted_img = cv2.bitwise_not(image)
    return inverted_img, boxes


# 减图像均值
def subtract_mean(image, boxes):
    mean = np.mean(image, axis=(0, 1))
    subtract_mean_img = image - mean
    subtract_mean_img = np.clip(subtract_mean_img, 0, 255).astype(np.uint8)
    return subtract_mean_img, boxes


# 图像混合
def mix_up(img1, box1, img2, box2, weight=0.5, dst_size=(640, 640)):
    img1, box1, _, _, _, _, _ = letterbox(img1, size=dst_size, boxes=box1)
    img2, box2, _, _, _, _, _ = letterbox(img2, size=dst_size, boxes=box2)
    mixed_img = cv2.addWeighted(img1, weight, img2, 1 - weight, 0)
    return mixed_img, np.concatenate([box1, box2], axis=0)



# 拼成一个n*m的图像
def mosaic(img4Array, boxArray, mosaic_num, letterbox_size=(512, 512)):
    """
    将4张图及其boxes，缩放，填充后，拼入一张图
    :param img4Array:
    :param boxArray:
    :param mosaic_num:
    :param letterbox_size:
    :return:
    """
    mosaic_boxes = []
    mosaic_image = np.zeros((letterbox_size[0] * mosaic_num[0], letterbox_size[1] * mosaic_num[1], 3), dtype='uint8')
    for i, (img, box) in enumerate(zip(img4Array, boxArray)):
        padded_image, padded_boxes, _, _, _, _, _ = letterbox(img, letterbox_size, box)
        row, col = divmod(i, mosaic_num[1])
        start_row = row * letterbox_size[0]
        end_row = start_row + letterbox_size[0]
        start_col = col * letterbox_size[1]
        end_col = start_col + letterbox_size[1]
        mosaic_image[start_row:end_row, start_col:end_col] = padded_image
        padded_boxes[:, 1] = padded_boxes[:, 1] / mosaic_num[0] + col / mosaic_num[0]
        padded_boxes[:, 2] = padded_boxes[:, 2] / mosaic_num[1] + row / mosaic_num[1]
        padded_boxes[:, 3] = padded_boxes[:, 3] / mosaic_num[0]
        padded_boxes[:, 4] = padded_boxes[:, 4] / mosaic_num[1]
        mosaic_boxes.append(padded_boxes)
    mosaic_boxes = np.concatenate(mosaic_boxes, axis=0)
    return mosaic_image, mosaic_boxes


# 滑动裁图
def sliding_crop(image, boxes, num=(2, 2), overlap=200, area_thresh=0.5):
    """
    滑动裁图,裁成num[0]*num[1]个图，标签框被裁开时，保留被裁到的标签面积大于原标签面积50%的框
    :param image:
    :param boxes:
    :param num:竖向滑动num[0]格，横向滑动num[1]格
    :param overlap:重叠多少
    :param area_thresh:决定标签去留的面积占比阈值
    :return:
    """
    img_height, img_width = image.shape[:2]
    boxes = yolo2voc(boxes, img_height, img_width)
    crop_height, crop_width = int(img_height / num[0] + overlap / 2), int(img_width / num[1] + overlap / 2)  # 要裁剪的图的宽高
    step_h, step_w = crop_height - overlap, crop_width - overlap  # 竖向,横向滑动步长

    crop_images = []  # 存放裁下来的图、滑动裁图后的标签
    crop_boxes = []
    image = image.copy() #label, xmin,ymin,xmax,ymax
    original_boxes_area = (boxes[..., 3] - boxes[..., 1]) * (boxes[..., 4] - boxes[..., 2])  # 计算原始框面积
    for y in range(0, img_height - crop_height + 1, step_h):  # 竖向滑动
        for x in range(0, img_width - crop_width + 1, step_w):  # 横向滑动
            crop_image = image[y:y + crop_height, x:x + crop_width]
            crop_images.append(crop_image)
            # 计算交集
            inter_x_min = np.maximum(boxes[..., 1], x)
            inter_y_min = np.maximum(boxes[..., 2], y)
            inter_x_max = np.minimum(boxes[..., 3], x + crop_width)
            inter_y_max = np.minimum(boxes[..., 4], y + crop_height)
            # 判断哪些 box 有交集
            inter_boxes_mask = (inter_x_min < inter_x_max) & (inter_y_min < inter_y_max)
            # 保留标签面积大于原标签面积50%的框
            inter_boxes = boxes[inter_boxes_mask]
            inter_boxes_area = (inter_x_max[inter_boxes_mask] - inter_x_min[inter_boxes_mask]) * (
                    inter_y_max[inter_boxes_mask] - inter_y_min[inter_boxes_mask])
            area_thresh_mask = inter_boxes_area / original_boxes_area[inter_boxes_mask] > area_thresh
            selected_boxes = inter_boxes[area_thresh_mask].copy()
            if np.any(area_thresh_mask):
                # 更新 box 的坐标，使其相对于裁剪后的图像
                selected_boxes[..., 1] = inter_x_min[inter_boxes_mask][area_thresh_mask] - x
                selected_boxes[..., 2] = inter_y_min[inter_boxes_mask][area_thresh_mask] - y
                selected_boxes[..., 3] = inter_x_max[inter_boxes_mask][area_thresh_mask] - x
                selected_boxes[..., 4] = inter_y_max[inter_boxes_mask][area_thresh_mask] - y
                # 将更新后的 box 添加到 result_boxes
                selected_boxes = voc2yolo(selected_boxes, crop_height, crop_width)
                crop_boxes.append(selected_boxes)
            else:
                crop_boxes.append(None)
    crop_images_boxes = list(zip(crop_images, crop_boxes))
    return crop_images_boxes


# 锐化
# 拉普拉斯+原图
def laplacian_sharpen(image, boxes, alpha, gamma=0):
    laplacian = cv2.Laplacian(image, ddepth=-1, ksize=3)
    # 转换为绝对值，并将图像转换回8位
    laplacian = cv2.convertScaleAbs(laplacian)
    img_laplacian_sharpen = cv2.addWeighted(laplacian, alpha, image, 1 - alpha, gamma)
    return img_laplacian_sharpen, boxes


# 高斯模糊+原图
def gaussian_sharpen(image, boxes, alpha, ksize=(3, 3), gamma=0):
    gaussian = cv2.GaussianBlur(image, ksize, 0)
    img_gaussian_sharpen = cv2.addWeighted(gaussian, alpha, image, 1 - alpha, gamma)
    return img_gaussian_sharpen, boxes


# 均值模糊+原图
def mean_sharpen(image, boxes, alpha, ksize=(3, 3), gamma=0):
    mean = cv2.blur(image, ksize)
    img_mean_sharpen = cv2.addWeighted(mean, alpha, image, 1 - alpha, gamma)
    return img_mean_sharpen, boxes


# sobel边缘重绘到原图
def sobel_edge_to_image(image, boxes, ksize=3):
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    # 使用 Sobel 算子计算 X Y 方向上的梯度
    sobel_x = cv2.Sobel(gray, -1, 1, 0, ksize=ksize)
    sobel_y = cv2.Sobel(gray, -1, 0, 1, ksize=ksize)
    # 计算梯度幅值
    gradient_magnitude = np.sqrt(sobel_x ** 2 + sobel_y ** 2)
    # 归一化并增强边缘
    gradient_magnitude = np.uint8(255 * gradient_magnitude / np.max(gradient_magnitude))
    edge_enhanced = cv2.convertScaleAbs(gradient_magnitude)
    # 将边缘信息重绘到原图上
    # 将边缘图像转换为三通道
    edge_enhanced_colored = cv2.cvtColor(edge_enhanced, cv2.COLOR_GRAY2BGR)

    # 合成原图和边缘图像，增强边缘
    alpha = 0.5  # 原图权重
    beta = 1.0  # 边缘图像权重
    enhanced_image = cv2.addWeighted(image, alpha, edge_enhanced_colored, beta, 0)
    return enhanced_image, boxes


# 翻转
def flip_image(image, boxes=None, flip_code=1):
    """
    翻转图片并修改对应box
    :param image: opencv读取的图片
    :param boxes: yolo格式的box
    :param flip_code: 1水平翻转，0垂直翻转，-1同时水平和垂直翻转
    :return: rotated_image 翻转后的图片, rotated_boxes 翻转后的box
    """
    rotated_image = cv2.flip(image, flip_code)
    rotated_boxes = np.array(boxes.copy())
    # 修改box
    if rotated_boxes is not None:
        # 水平翻转后只有cx改变
        if flip_code == 1:
            rotated_boxes[:, 1] = 1 - rotated_boxes[:, 1]
        # 垂直翻转后只有cy改变
        elif flip_code == 0:
            rotated_boxes[:, 2] = 1 - rotated_boxes[:, 2]
        # 同时水平和垂直翻转cx、cy都变
        else:
            rotated_boxes[:, 1] = 1 - rotated_boxes[:, 1]
            rotated_boxes[:, 2] = 1 - rotated_boxes[:, 2]
    return rotated_image, rotated_boxes


# 图片随机平移
def image_translation(image, boxes):
    image_height, image_width = image.shape[:2]
    # 随机生成平移量，范围在图片尺寸的一半内
    tx = np.random.randint(-image_width / 2, image_width / 2)
    ty = np.random.randint(-image_height / 2, image_height / 2)
    # 创建平移矩阵
    moving_matrix = np.float64([[1, 0, tx], [0, 1, ty]])
    # 对图像进行平移
    translated_image = cv2.warpAffine(image, moving_matrix, (image_width, image_height))
    # 计算box新坐标
    voc_boxes = yolo2voc(boxes, image_width, image_height)
    new_x_min = np.maximum(voc_boxes[..., 1] + tx, 0)
    new_y_min = np.maximum(voc_boxes[..., 2] + ty, 0)
    new_x_max = np.minimum(voc_boxes[..., 3] + tx, image_width)
    new_y_max = np.minimum(voc_boxes[..., 4] + ty, image_height)
    # 过滤不在图片内的box
    valid_boxes_mask = (new_x_min < new_x_max) & (new_y_min < new_y_max)
    # 更新box
    final_voc_boxes = voc_boxes[valid_boxes_mask].copy()
    final_voc_boxes[..., 1] = new_x_min[valid_boxes_mask]
    final_voc_boxes[..., 2] = new_y_min[valid_boxes_mask]
    final_voc_boxes[..., 3] = new_x_max[valid_boxes_mask]
    final_voc_boxes[..., 4] = new_y_max[valid_boxes_mask]
    translation_boxes = voc2yolo(final_voc_boxes, image_width, image_height)
    return translated_image, translation_boxes


# 旋转
def rotate_image_and_boxes(image, boxes, angle, rotated_center_x=0.5, rotated_center_y=0.5, zoom=1.0, fill_up=False):
    """
    :param image:
    :param boxes:
    :param angle: 旋转角度，正数顺时针旋转，负数逆时针旋转
    :param rotated_center_x: 旋转中心的x坐标在图中的比例，范围0-1
    :param rotated_center_y: 旋转中心的y坐标在图中的比例，范围0-1
    :param zoom: 缩放比例
    :param fill_up: True时，图片填充满窗口
    :return:
    """
    image_height, image_width = image.shape[:2]
    rotated_center_x *= image_width
    rotated_center_y *= image_height
    angle_rad = np.deg2rad(angle)
    if fill_up:
        # 计算旋转后的新图像尺寸
        new_width = abs(image_height * np.sin(angle_rad)) + abs(image_width * np.cos(angle_rad))
        new_height = abs(image_height * np.cos(angle_rad)) + abs(image_width * np.sin(angle_rad))
        # 计算保持图像在原始尺寸内的最大缩放
        zoom_w = image_width / new_width
        zoom_h = image_height / new_height
        zoom = min(zoom_w, zoom_h)
    rotation_matrix = cv2.getRotationMatrix2D((rotated_center_x, rotated_center_y), -angle, zoom)  # 2x3的旋转矩阵
    rotated_image = cv2.warpAffine(image, rotation_matrix, (image_width, image_height))
    rotated_boxes = []
    for box in boxes:
        x_center = box[1] * image_width
        y_center = box[2] * image_height
        box_width = box[3] * image_width
        box_height = box[4] * image_height
        corners = np.array([
            [x_center - box_width / 2, y_center - box_height / 2],
            [x_center + box_width / 2, y_center - box_height / 2],
            [x_center + box_width / 2, y_center + box_height / 2],
            [x_center - box_width / 2, y_center + box_height / 2]])
        # 添加第三维以应用旋转矩阵
        corners = np.hstack((corners, np.ones((4, 1))))
        rotated_corners = (rotation_matrix @ corners.T).T
        # 获取旋转后边界框的最小和最大坐标
        x_min, y_min = np.min(rotated_corners, axis=0)[:2]
        x_max, y_max = np.max(rotated_corners, axis=0)[:2]
        # 确保坐标在图像范围内
        x_min, y_min = np.maximum(0, [x_min, y_min])
        x_max, y_max = np.minimum([image_width, image_height], [x_max, y_max])
        # 检查是否形成有效矩形
        if x_min < x_max and y_min < y_max:
            rotated_boxes.append([box[0], x_min, y_min, x_max, y_max])
    rotated_boxes = voc2yolo(np.array(rotated_boxes), image_height, image_width)
    return rotated_image, rotated_boxes


# 透视变换
def perspective_transformation(image, boxes=None, src_points=None, dst_points=None):
    image_h, image_w = image.shape[:2]
    # 获取透视变换矩阵
    M = cv2.getPerspectiveTransform(np.float32(src_points), np.float32(dst_points))
    # 对图像应用透视变换
    warped_image = cv2.warpPerspective(image, M, (image_w, image_h))

    # 计算框的新坐标
    new_boxes = []
    for box in boxes:
        x_center, y_center, width, height = box[1], box[2], box[3], box[4]

        # 计算原始图像中的坐标
        new_x_center = x_center * image_w
        new_y_center = y_center * image_h
        new_width = width * image_w
        new_height = height * image_h

        # 转换框的四个角点
        box_points = np.array([
            [new_x_center - new_width / 2, new_y_center - new_height / 2],  # 左上角
            [new_x_center + new_width / 2, new_y_center - new_height / 2],  # 右上角
            [new_x_center + new_width / 2, new_y_center + new_height / 2],  # 右下角
            [new_x_center - new_width / 2, new_y_center + new_height / 2]  # 左下角
        ], dtype=np.float32)

        # 如果使用透视变换，将框的角点应用到透视变换矩阵上
        # [0] 是为了提取数组中的第一个元素，得到变换后的点 [[x1', y1'], [x2', y2'], [x3', y3'], [x4', y4']]，即去掉最外层的括号，只保留四个变换后的角点。
        transformed_points = cv2.perspectiveTransform(np.array([box_points]), M)[0]

        # 重新计算变换后的框中心和宽高
        x_min, y_min = np.min(transformed_points, axis=0)
        x_max, y_max = np.max(transformed_points, axis=0)

        # 确保坐标在图像范围内
        x_min, y_min = np.maximum(0, [x_min, y_min])
        x_max, y_max = np.minimum([image_w, image_h], [x_max, y_max])

        # 检查是否形成有效矩形
        if x_min < x_max and y_min < y_max:
            new_boxes.append([box[0], x_min, y_min, x_max, y_max])

    new_boxes = voc2yolo(np.array(new_boxes), image_h, image_w)
    return warped_image, new_boxes


# copy_paste
def copy_paste(image, yolo_boxes, copy_times, class_id = 0):
    """
    在当前image中将指定的label复制N份，随机一个位置，随机的新boxes不能与原boxes重叠
    :param image:
    :param yolo_boxes:
    :param copy_times: 复制times份
    :return:
    """
    ih, iw = image.shape[0:2]
    boxes = yolo2voc(yolo_boxes, ih, iw)
    his_boxes = [*boxes]  # 存放已存在的历史标签
    mask = np.zeros((ih, iw), dtype=np.uint8)  # 为标签创建一个掩码，以防止重叠

    # 将 原boxes框 写入掩码
    for box in boxes:
        mask[box[2]:box[4], box[1]:box[3]] = 1

    for box in [bx for bx in boxes if bx[0] == class_id]:  # 指定粘贴类别 b[0] == 0 class_id为0的
        label_img = image[box[2]:box[4], box[1]:box[3]]
        [box] = voc2yolo([box], ih, iw)
        num = np.random.rand()  # 切标签图 上采样，下采样，翻转，旋转45度 np.random.rand if num < 0.1: < 0.2: < 0.4: 分别操作
        if 0.25 > num >= 0:
            label_img, [box] = pyr_down(label_img, [box], 1)
        if 0.5 > num >= 0.25:
            label_img, [box] = pyr_up(label_img, [box], 1)
        if 0.75 > num >= 0.5:
            label_img, [box] = flip_image(label_img, [box], 1)  # flip_image(image, boxes=None, flipcode=1)
        if 1 > num >= 0.75:
            label_img, [box] = rotate_image_and_boxes(label_img, [box], 45, rotated_center_x=0.5, rotated_center_y=0.5,
                                                      zoom=1.0, fill_up=False)
        bh, bw = label_img.shape[:2]
        for _ in range(copy_times):
            while True:
                x = np.random.randint(0, iw - bw)
                y = np.random.randint(0, ih - bh)
                if np.all(mask[y:y + bh, x:x + bw] == 0):
                    image[y:y + bh, x:x + bw] = label_img  # 粘贴标签到指定位置
                    mask[y:y + bh, x:x + bw] = 1  # 粘贴的标签在掩码上的对应位置赋1
                    his_boxes.append([box[0], x, y, x + bw, y + bh])
                    break
    return image, his_boxes


# 贴image1标签到image2
def paste_img1box_to_img2(img1, boxes1, img2, boxes2):
    img2_h, img2_w = img2.shape[:2]
    # 图2上面的所有原有标签
    img2_boxes = [*boxes2]
    # 为标签创建一个掩码，以防重叠
    mask = np.zeros((img2_h, img2_w), dtype=np.uint8)

    boxes1 = yolo2voc(boxes1, *img1.shape[:2])
    boxes2 = yolo2voc(boxes2, img2_h, img2_w)
    # 将图2的标签框写入掩码，标记为1
    for box in boxes2:
        mask[box[2]:box[4], box[1]:box[3]] = 1
    # 截取图1的标签框
    for box in boxes1:
        label1_w, label1_h = box[3] - box[1], box[4] - box[2]
        label1_img = img1[box[2]:box[4], box[1]:box[3]]
        while True:
            x = np.random.randint(0, img2_w - label1_w)  # 随机生成新位置,并确保新位置不会超出图像边界
            y = np.random.randint(0, img2_h - label1_h)
            # 排除图1框与图2框有交集的地方
            if np.all(mask[y:y + label1_h, x:x + label1_w] == 0):
                # 图1框粘贴至图2
                img2[y:y + label1_h, x:x + label1_w] = label1_img
                # 将在图2粘贴过的框区域，其掩码设置为标记1
                mask[y:y + label1_h, x:x + label1_w] = 1
                # 将后来粘贴上去的框添加至图2的标签里
                img2_boxes.append([box[0], x, y, x + label1_w, y + label1_h])
                break
    yolo_2_boxes = voc2yolo(img2_boxes, img2_h, img2_w)
    return img2, yolo_2_boxes


# 上采样
def pyr_up(img, boxes, times):
    ih, iw = img.shape[:2]
    yolo_boxes = voc2yolo(boxes, iw, ih)

    for _ in range(times):
        img = cv2.pyrUp(img)
    return img, yolo_boxes


# 下采样
def pyr_down(img, boxes, times):
    ih, iw = img.shape[:2]
    yolo_boxes = voc2yolo(boxes, iw, ih)

    for _ in range(times):
        img = cv2.pyrDown(img)
    return img, yolo_boxes


# 切图放大
def cut_box(img, boxes, times):
    height, width = img.shape[:2]
    cropped_images_and_boxes = []
    boxes = yolo2voc(boxes, height, width)
    for box in boxes:
        new_x_min = box[1] - np.minimum(box[1], 100)
        new_y_min = box[2] - np.minimum(box[2], 100)
        new_x_max = box[3] + np.minimum(width - box[3], 100)
        new_y_max = box[4] + np.minimum(height - box[4], 100)
        new_img_h = new_y_max - new_y_min
        new_img_w = new_x_max - new_x_min
        cropped_img = img.copy()[new_y_min:new_y_max, new_x_min:new_x_max, :]
        # box
        box_x_min = int(np.minimum(100, box[1]))
        box_y_min = int(np.minimum(100, box[2]))
        box_x_max = int(new_img_w - np.minimum(100, width - box[3]))
        box_y_max = int(new_img_h - np.minimum(100, height - box[4]))
        new_voc_box = np.array([box[0], box_x_min, box_y_min, box_x_max, box_y_max])
        new_yolo_box = voc2yolo(new_voc_box, new_img_h, new_img_w)
        for _ in range(times):
            cropped_img = cv2.pyrUp(cropped_img)
        cropped_images_and_boxes.append((cropped_img, [new_yolo_box]))
    return cropped_images_and_boxes


if __name__ == '__main__':
    imagedir = fr'F:\2025\d3_yuan_seg\0219\0228_rect\images'
    labeldir = fr'F:\2025\d3_yuan_seg\0219\0228_rect\labels'
    datas = read_img_label(imagedir, labeldir)  # 读取图片和标签文件
    #
    image, boxes, ratio, padT, padB, padL, padR = letterbox(datas[0][0], size=[640, 640], boxes=datas[0][1])
    #
    # image, boxes, classes = yolo_box_to_origin_image(datas[0][0], image, boxes, ratio, padT, padL)
    #
    # image, boxes = bgr2rgb(datas[0][0], datas[0][1])
    #
    # image, boxes = bgr2gray(datas[0][0], datas[0][1])
    #
    # image, boxes = global_histogram_equalization(datas[0][0], datas[0][1])
    #
    # image, boxes = adaptive_histogram_equalization(datas[0][0], datas[0][1])
    #
    # image, boxes, ratio = random_brightness(datas[0][0], datas[0][1], rnd_min=0.5, rnd_max=1.5)
    #
    # image, boxes = img_bitwise_not(datas[0][0], datas[0][1])
    #
    # image, boxes = subtract_mean(datas[0][0], datas[0][1])
    #
    # image, boxes = mix_up(datas[0][0], datas[0][1], datas[1][0], datas[1][1], weight=0.5, dst_size=[640, 640])
    #
    image, boxes = mosaic([datas[0][0], datas[1][0], datas[2][0], datas[3][0]], [datas[0][1], datas[1][1], datas[2][1], datas[3][1]], [2, 2], [512, 512])

    #
    images_boxes = sliding_crop(datas[0][0], datas[0][1], (2, 3), 200, area_thresh=0.5)  # (2, 3)竖向滑动2格，横向滑动3格; 200重叠多少;
    #
    # image, boxes = laplacian_sharpen(datas[0][0], datas[0][1], 0.5, gamma=0)
    #
    # image, boxes = gaussian_sharpen(datas[0][0], datas[0][1], 0.5, ksize=(3, 3), gamma=0)
    #
    # image, boxes = mean_sharpen(datas[0][0], datas[0][1], 0.5, ksize=(3, 3), gamma=0)
    #
    # image, boxes = sobel_edge_to_image(datas[0][0], datas[0][1], ksize=3)
    #
    # image, boxes = flip_image(datas[0][0], datas[0][1], flip_code=1)
    #
    # image, boxes = image_translation(datas[0][0], datas[0][1])
    #
    # for i, data in enumerate(datas) :
    #     ange = random.choice([45,35,15,25,5,10])
    #     image, boxes = rotate_image_and_boxes(data[0], data[1], ange, rotated_center_x=0.5, rotated_center_y=0.5, zoom=1.0, fill_up=False)
    #     cv2.imwrite(f'{imagedir}\\rotate_{i}.jpg',image)
    # drawRectangle_show(image, boxes)
    #
    # h, w = datas[0][0].shape[:2]
    # src_points = [(0, 0), (w - 1, 0), (w - 1, h - 1), (0, h - 1)]  # 原图四角
    # dst_points = [(50, 50), (w - 50, 100), (w - 100, h - 100), (100, h - 50)]
    # image, boxes = perspective_transformation(datas[0][0], boxes=datas[0][1], src_points=src_points, dst_points=dst_points)
    #
    image, boxes = copy_paste(datas[0][0], datas[0][1], 1)
    #
    # image, boxes = paste_img1box_to_img2(datas[0][0], datas[0][1], datas[1][0], datas[1][1])
    #
    # image, boxes = pyr_up(datas[0][0], datas[0][1], 2)
    #
    # image, boxes = pyr_down(datas[0][0], datas[0][1], 2)
    #
    # images_boxes = cut_box(datas[0][0], datas[0][1], 2)

    # show出图和框,以验证结果是否正确
    # drawRectangle_show(image, boxes)

    # show出滑动裁图的图和框,以验证裁剪、画框是否正确
    # for data in images_boxes:
    #     drawRectangle_show(data[0].copy(), data[1])
