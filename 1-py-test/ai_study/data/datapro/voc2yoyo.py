'''voc 转 yolo
1. voc : size，class_name，bndbox
2. yolo: class_id, x, y, w, h (归一化/size)'''

# 1) 读取 xml 文件
# 2) 转 yolo 格式
# 3) 生成 txt 文件：类别文件classes.txt, 标签文件.txt


# import xml.etree.ElementTree as ET
# import os
#
# # xml_file = r'C:\Study\datapro\voc_label\test_00000546.xml'
# file_dir = r'C:\Study\datapro\voc_label'
# class_name = ['face', 'mask']
# class_name_dict = {c: i for i, c in enumerate(class_name)}
#
# for file in os.listdir(file_dir):
#     # 1. 解析 xml 的元素树对象，获取根
#     tree = ET.parse(os.path.join(file_dir, file))
#     root = tree.getroot()
#
#     # 2. 获取 w, h
#     width = int(root.findtext('size/width'))
#     height = int(root.findtext('size/height'))
#
#     # 3. 获取 object 多个: class_name, bndbox
#     objects = root.findall('object')
#     for obj in objects:
#         class_name = obj.find('name').text
#         bndbox = [int(obj.findtext(f'bndbox/{b}')) for b in ['xmin', 'ymin', 'xmax', 'ymax']]
#
#         # 转 yolo 格式
#         x1, y1, x2, y2 = bndbox
#         x = ((x2 - x1) / 2 + x1) / width  # (x2 + x1) / 2
#         y = ((y2 - y1) / 2 + y1) / height
#         w = (x2 - x1) / width
#         h = (y2 - y1) / height
#
#         yolo_txt = [class_name_dict[class_name], x, y, w, h]
#
#         file_name = os.path.splitext(os.path.basename(file))[0]+'.txt'
#         with open(file_name, mode='a', encoding='utf-8') as f:
#             f.write(' '.join(map(str, yolo_txt))+'\n')


''' 按格式：封装函数，尽量功能单一 '''
# 1. 读取 xml 数据， 读取图片size
# 2. 转 yolo 格式
# 3. 生成 yolo 文件

import os
import xml.etree.ElementTree as ET
from PIL import Image


def read_voc(xml_file):
    # 1. 解析 xml 的元素树对象，获取根
    tree = ET.parse(xml_file)
    root = tree.getroot()

    # 2. 获取 object 多个: class_name, bndbox
    voc_boxs = []
    objects = root.findall('object')
    for obj in objects:
        class_name = obj.find('name').text
        bndbox = [int(obj.findtext(f'bndbox/{b}')) for b in ['xmin', 'ymin', 'xmax', 'ymax']]
        voc_boxs.append([class_name, *bndbox])

    return voc_boxs


def read_image(image_name):
    print(__name__)
    with Image.open(image_name, mode='r') as img:
        return img.size


def voc_to_yolo(voc_boxs, class_name_dict, img_width, img_height):
    yolo_boxs = []
    for voc_box in voc_boxs:
        class_name, x1, y1, x2, y2 = voc_box
        class_id = class_name_dict[class_name]
        x = ((x2 - x1) / 2 + x1) / img_width  # (x2 + x1) / 2
        y = ((y2 - y1) / 2 + y1) / img_height
        w = (x2 - x1) / img_width
        h = (y2 - y1) / img_height

        yolo_boxs.append([class_id, x, y, w, h])

    return yolo_boxs


def generate_yolo_txt(yolo_txt_path, yolo_box):
    with open(yolo_txt_path, mode='a', encoding='utf-8') as f:
        f.write(' '.join(map(str, yolo_box)) + '\n')


if __name__ == '__main__':
    # xml 标签文件目录，图片文件目录
    file_xml_dir = r'./label-data'
    image_dir = r'./label-img'

    # 定义生成 yolo 标签额目录，创建目录
    yolo_dir = r'./label-data/yolo_txt_dir'
    if not os.path.exists(yolo_dir):
        os.makedirs(yolo_dir)

    # 类别
    class_name = ['face', 'others']
    class_name_dict = {c: i for i, c in enumerate(class_name)}

    # 判断
    if not os.path.exists(file_xml_dir):
        raise 'xml 标签目录错误'

    # 读取目录下的文件
    xml_files = [file for file in os.listdir(file_xml_dir) if file.endswith('.xml')]

    # 循环读取 xml_file 获取数据
    for file in xml_files:
        # 拼接文件名路径 , 图片文件路径
        xml_file_path = os.path.join(file_xml_dir, file)
        image_path = os.path.join(image_dir, os.path.splitext(file)[0] + '.jpg')

        # 拼接 yolo 的 txt 文件
        yolo_txt_path = os.path.join(yolo_dir, os.path.splitext(file)[0] + '.txt')

        # 读取 xml - 封装函数
        voc_box = read_voc(xml_file_path)
        # 读取 image 的大小
        img_width, img_height = read_image(image_path)

        # 转 yolo 格式
        yolo_boxs = voc_to_yolo(voc_box, class_name_dict, img_width, img_height)

        for yolo_box in yolo_boxs:
            generate_yolo_txt(yolo_txt_path, yolo_box)
    pass
