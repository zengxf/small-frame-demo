''' labelimg 标注工具：三种数据格式 '''

# voc 格式（xml文件，一个）：xmin, ymin, xmax, ymax
# yolo 格式（txt文件，两个类别,标签）：class_id, x, y, w, h（归一化/size_wh）
# coco 格式（json文件，一个）：xmin, ymin, w, h

import os

# 1. 打开 txt
label_dir = r'label-data'

# 打开 classes.txt 读取类别，转为字典 {face:0, mask:1}
class_path = r'label-data/classes.txt'
with open(class_path, mode='r', encoding='utf-8') as fc:
    class_names = {i: c.strip() for i, c in enumerate(fc.readlines())}
    # print(class_names)

# 2. 读取数据：class_id, x, y, w, h
with open(os.path.join(label_dir, 'test_00000546.txt'), mode='r', encoding='utf-8') as f:
    # 读取数据（文件读取返回字符串）：去两边空格，分割为列表
    # class_id, x, y, w, h = map(float, f.read().strip().split())

    # 多个标签数据
    bbox = []
    for line in f.readlines():
        class_id, x, y, w, h = map(float, line.strip().split())
        # bbox.append([int(class_id), float(x), float(y), float(w), float(h)])

        class_name = class_names[int(class_id)]

        # 3. 转 voc : xmin, ymin, xmax, ymax
        # 假设图片大小 width, height = 180, 180
        width, height = 180, 180
        xmin = (x - w / 2) * width
        ymin = (y - h / 2) * height
        xmax = (x + w / 2) * width
        ymax = (y + h / 2) * height

        bbox.append([class_name, xmin, ymin, xmax, ymax])

        print(bbox)

# 4. 写入 xml 文件，标签属性都是字符串
# [['face', 40.79997, 43.19991, 139.20003, 141.59997]]

import xml.etree.ElementTree as ET

# 1）构造 element 元素的根标签
root = ET.Element('annotation')

# 2）从 xml 的根标签 root 构造子标签
size = ET.SubElement(root, 'size')
ET.SubElement(size, 'width').text = str(180)
ET.SubElement(size, 'height').text = str(180)

obj = ET.SubElement(root, 'object')
ET.SubElement(obj, 'name').text = 'face'

bndbox = ET.SubElement(obj, 'bndbox')
ET.SubElement(bndbox, 'xmin').text = str(40.79997)
ET.SubElement(bndbox, 'ymin').text = str(43.19991)

# 3）转 element 元素树结构。write()
tree = ET.ElementTree(root)
tree.write(os.path.join(label_dir, 'new_label.xml'))

# 4）格式化生成 xml， 导入 from xml.dom import minidom
from xml.dom import minidom

tree = ET.tostring(root, encoding='utf-8')
format_xml = minidom.parseString(tree).toprettyxml(indent='\t')

with open(os.path.join(label_dir, 'new_label1.xml'), mode='w', encoding='utf-8') as f:
    f.write(format_xml)

pass
