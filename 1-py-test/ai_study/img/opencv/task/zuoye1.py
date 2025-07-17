import cv2
import random
import numpy as np


def show(img, title=''):
    cv2.imshow(title, img)
    # 等待在图中按任意键
    cv2.waitKey(0)
    print('hello')


def cut_out_box(img, rect_size=(20, 20), num_regions=1, boxes_xyxy=[[]], min_cut_box_size=[64, 64],color=[170,170,170]):
    """
    在box区域中进行mask=0。迫使CNN去学习主要特征，减少误检。但不适用小目标
    :param img:
    :param rect_size:
    :param num_regions:
    :param boxes_xyxy:
    :param min_cut_box_size:
    :return:
    """
    height, width = img.shape[:2]
    rect_w, rect_h = rect_size
    # 创建全0掩码
    mask = np.zeros((height, width), dtype=np.uint8)
    # 如果没有指定box，则在整个图像范围内操作
    if not boxes_xyxy:
        boxes_xyxy = [[0, 0, width, height]]
    processed_regions = 0
    # 尝试找到并处理指定数量的区域
    while processed_regions < num_regions:
        # 随机选择一个box
        box = random.choice(boxes_xyxy)
        left_x, left_y, right_x, right_y = box

        # 计算可用的区域范围(考虑小矩形大小)
        avail_w = right_x - left_x  # - rect_w
        avail_h = right_y - left_y  # - rect_h

        if avail_w <= min_cut_box_size[0] or avail_h <= min_cut_box_size[1]:
            continue  # 这个box太小，跳过

        # 在选定的box内随机生成位置
        x = left_x + random.randint(left_x, avail_w)
        y = left_y + random.randint(left_y, avail_h)

        # 检查该区域是否未被标记
        if np.all(mask[y:y + rect_h, x:x + rect_w] == 0):
            # 将原图对应区域置零
            img[y:y + rect_h, x:x + rect_w] = color

            # 在掩码上标记这个区域
            mask[y:y + rect_h, x:x + rect_w] = 255

            print(f"在区域{box}内的坐标({x}, {y})处将{rect_w}x{rect_h}区域置零")
            processed_regions += 1

    return img


def cut_out_class(img, rect_size=(20, 20), num_regions=1,color=[170,170,170]):
    """
    实现cut_out。分类可能涨0.8
    :param image:
    :param size:
    :return:
    """
    height, width = img.shape[:2]
    # 步骤2：创建与原图相同大小的全0掩码
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
            # 步骤4：将原图对应区域设为0
            img[y:y + rect_h, x:x + rect_w] = color
            # 在掩码上标记这个区域
            mask[y:y + rect_h, x:x + rect_w] = 255
            print(f"在坐标({x}, {y})处将{rect_w}x{rect_h}区域置零")
            processed_regions += 1
    return img


def cut_mix(image1, image1_boxes_xyxy, image2, image2_boxes_xyxy,model_size=[640,640]):
    """
    实现cut_mix，目标检测和分类都有可能涨+1%。注意事项，要传box，要同时resize。
    还要注意，透明度的比例.

    cat_dog试验，+1%

    :param image1:
    :param image1_boxes_xyxy:
    :param image2:
    :param image2_boxes_xyxy:
    :param model_size:
    :return:
    """
    # image1_h, image1_w = image1.shape[:2]
    # image2_h, image2_w = image2.shape[:2]
    m_h, m_w = model_size
    # 这个函数要换成letterbox
    image1 = cv2.resize(image1,[m_w, m_h])
    image2 = cv2.resize(image2, [m_w, m_h])

    box = random.choice(image1_boxes_xyxy)
    left_x, left_y, right_x, right_y = box

    mask = np.zeros((m_h, m_w, 3), dtype=np.uint8)
    box_img = image1[left_y:right_y, left_x:right_x]
    mask[left_y:right_y, left_x:right_x] = box_img

    cut_mix_img = cv2.addWeighted(mask,0.6, image2, 0.4,0)
    return cut_mix_img,box,image2_boxes_xyxy





if __name__ == "__main__":
    img = cv2.imread(r'D:\zhigu-ai-27-note\untitled2\opencv0627\imgs\lenaNoise.png')
    img2 = cv2.imread(r'D:\zhigu-ai-27-note\untitled2\opencv0627\imgs\1.png')
    """
    思路to do：
    步骤1：读图
    步骤2：创建一个原步骤1中相同大小mask图，全0
    步骤3：在步骤2中，随机一个不为255的初始坐标点，宽高为20x20的矩形坐标，然后在步骤1对应的区域填0
    
    观察关键点：
    观察关键代码是不是按你思路来写的（*****）
    
    
    """
    # cut_img = cut_out_class(img,num_regions=10)
    # show(cut_img,'cut')

    # cut_box_img = cut_out_box(img, rect_size=[40, 40], boxes_xyxy=[[223, 134, 845, 818]], num_regions=5)
    # show(cut_box_img, 'cut2')
    mix_cut_img,_,_ = cut_mix(img,[[223, 134, 845, 818]],img2,[[]])
    show(mix_cut_img,'mixcut')