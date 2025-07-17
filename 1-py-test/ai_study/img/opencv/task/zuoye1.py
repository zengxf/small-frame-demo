import cv2
import random
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)
    print('hello')


# 在指定区域画小方块
# 在 box 区域中进行 mask=0。迫使 CNN 去学习主要特征，减少误检。但不适用小目标
def cut_out_box(img, rect_size=(20, 20), num_regions=10, boxes_xyxy=[[]],
                min_cut_box_size=[64, 64], color=[170, 170, 170]):
    height, width = img.shape[:2]
    rect_w, rect_h = rect_size
    # 创建全 0 掩码
    mask = np.zeros((height, width), dtype=np.uint8)
    # 如果没有指定 box，则在整个图像范围内操作
    if not boxes_xyxy:
        boxes_xyxy = [[0, 0, width, height]]
    processed_regions = 0
    # 尝试找到并处理指定数量的区域
    while processed_regions < num_regions:
        # 随机选择一个 box
        box = random.choice(boxes_xyxy)
        left_x, left_y, right_x, right_y = box
        cv2.rectangle(img, box, [255, 0, 255], 2, 1)

        # 计算可用的区域范围(考虑小矩形大小)
        avail_w = right_x - left_x  # - rect_w
        avail_h = right_y - left_y  # - rect_h

        if avail_w <= min_cut_box_size[0] or avail_h <= min_cut_box_size[1]:
            continue  # 这个 box 太小，跳过

        # 在选定的 box 内随机生成位置
        x = left_x + random.randint(left_x, avail_w)
        y = left_y + random.randint(left_y, avail_h)

        # 检查该区域是否未被标记
        if np.all(mask[y:y + rect_h, x:x + rect_w] == 0):
            # 将原图对应区域置零
            img[y:y + rect_h, x:x + rect_w] = color
            # 在掩码上标记这个区域
            mask[y:y + rect_h, x:x + rect_w] = 255
            print(f"在区域 {box} 内的坐标({x}, {y})处将 {rect_w}x{rect_h} 区域置零")
            processed_regions += 1

    return img


# 添加 n 个小方块，随机到不到位置
# 实现 cut_out。分类可能涨 0.8
def cut_out_class(img, rect_size=(20, 20), num_regions=1, color=[170, 170, 170]):
    height, width = img.shape[:2]
    # 创建与原图相同大小的全 0 掩码
    mask = np.zeros((height, width), dtype=np.uint8)
    # 记录已处理的区域数量
    processed_regions = 0
    rect_w, rect_h = rect_size
    # 只要还有未标记的区域且未达到要求的处理数量就继续
    while np.any(mask == 0) and processed_regions < num_regions:
        # 生成随机坐标
        x = random.randint(1, width - rect_w - 1)
        y = random.randint(1, height - rect_h - 1)
        # 检查该区域是否未被标记
        if np.all(mask[y:y + rect_h, x:x + rect_w] == 0):
            # 将原图对应区域设为 0
            img[y:y + rect_h, x:x + rect_w] = color
            # 在掩码上标记这个区域
            mask[y:y + rect_h, x:x + rect_w] = 255
            print(f"在坐标({x}, {y})处将[{rect_w}x{rect_h}]区域置零")
            processed_regions += 1
    return img


# 融合图片
def cut_mix(smallImg, smallImg_boxes_xyxy, bigImg, model_size=[640, 640]):
    """
    bigImg: 目标图片
    smallImg: 用来做干扰的图片

    实现 cut_mix，目标检测和分类都有可能涨 +1%。注意事项，要传 box，要同时 resize。
    还要注意，透明度的比例.

    cat_dog 试验，+1%
    """
    m_h, m_w = model_size
    # 这个函数要换成 letterbox
    smallImg = cv2.resize(smallImg, [m_w, m_h])
    bigImg = cv2.resize(bigImg, [m_w, m_h])

    box = random.choice(smallImg_boxes_xyxy)
    left_x, left_y, right_x, right_y = box

    mask = np.zeros((m_h, m_w, 3), dtype=np.uint8)
    box_img = smallImg[left_y:right_y, left_x:right_x]  # 截部分干扰图
    # show(box_img, 'box_img')
    mask[left_y:right_y, left_x:right_x] = box_img  # 加入到底图
    # show(mask, 'mask')

    # 按权重融合
    cut_mix_img = cv2.addWeighted(mask, 0.6, bigImg, 0.4, 0)
    return cut_mix_img, box


if __name__ == "__main__":
    img = cv2.imread(r"D:/Data/Test/img/lenaNoise.png")
    """
    思路to do：
    步骤1：读图
    步骤2：创建一个原步骤1中相同大小mask图，全0
    步骤3：在步骤2中，随机一个不为255的初始坐标点，宽高为20x20的矩形坐标，然后在步骤1对应的区域填0

    观察关键点：
    观察关键代码是不是按你思路来写的（*****）
    """

    # cut_img = cut_out_class(img, num_regions=10)
    # show(cut_img, 'cut')

    # cut_box_img = cut_out_box(img, rect_size=[30, 30], boxes_xyxy=[[20, 50, 450, 500]], num_regions=5)
    # show(cut_box_img, 'cut2')
    # cv2.imwrite('cut2.jpg', cut_box_img)

    smallImg = cv2.imread(r"D:/Data/Test/img/lenaNoise2.png")
    bigImg = cv2.imread(r"D:/Data/Test/img/ye2.jpg")
    mix_cut_img, _ = cut_mix(smallImg, [[20, 50, 450, 500]], bigImg)
    show(mix_cut_img, 'mix_cut')
