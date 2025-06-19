''' coco 转 voc
1. coco: class_name, xmin, ymin, w, h
2. voc : size，class_name，bndbox '''

import json
import os
import xml.etree.ElementTree as ET
from voc2yoyo import read_image
from xml.dom import minidom


def read_coco(json_file):
    with open(json_file, mode='r', encoding='utf-8') as f:
        json_list = json.load(f)
        coco_boxs = []
        for annotation in json_list[0]['annotations']:
            # {'label': 'face', 'coordinates': {'x': 73.5, 'y': 85.10096153846155, 'width': 79.0, 'height': 86.0}}
            class_name = annotation['label']
            coordinate = annotation['coordinates']
            x, y, width, height = coordinate.values()
            coco_boxs.append([class_name, x, y, width, height])

    return coco_boxs


def coco_to_voc(coco_boxs):
    voc_boxs = []
    for box in coco_boxs:
        class_name, x, y, width, height = box
        voc_boxs.append([class_name, x, y, x + width, y + height])
    return voc_boxs


def voc_xml(voc_boxs, image_path, save_xml):
    # 构造 xml 的根
    tree = ET.Element('annotation')
    ET.SubElement(tree, 'filename').text = os.path.basename(image_path)
    ET.SubElement(tree, 'path').text = image_path

    img_width, img_height = read_image(image_path)
    size = ET.SubElement(tree, 'size')
    ET.SubElement(size, 'width').text = str(img_width)
    ET.SubElement(size, 'height').text = str(img_height)

    # 多个 box，添加 object
    for box in voc_boxs:
        class_name, x, y, width, height = box
        object = ET.SubElement(tree, 'object')
        ET.SubElement(object, 'name').text = class_name
        bndbox = ET.SubElement(object, 'bndbox')
        ET.SubElement(bndbox, 'xmin').text = str(x)
        ET.SubElement(bndbox, 'ymin').text = str(y)
        ET.SubElement(bndbox, 'xmax').text = str(width)
        ET.SubElement(bndbox, 'ymax').text = str(height)

    tree = ET.tostring(tree)
    format_xml = minidom.parseString(tree).toprettyxml(indent='\t')
    with open(save_xml, mode='w', encoding='utf-8') as f:
        f.write(format_xml)


if __name__ == '__main__':
    # coco 的目录，获取文件
    coco_dir = r'./label-data'
    coco_files = [file for file in os.listdir(coco_dir) if file.endswith('.json')]

    # 图片目录
    image_dir = r'./label-img'

    # 生成 xml 文件
    xml_dir = r'./label-data/xml_dir'
    os.makedirs(xml_dir, exist_ok=True)

    # 循环读取 json 文件
    for file in coco_files:
        # 拼接 json 文件路径
        json_file_path = os.path.join(coco_dir, file)

        # 拼接图片的路径
        image_path = os.path.join(image_dir, os.path.splitext(file)[0] + '.jpg')

        # 函数：读取 json 数据
        coco_boxs = read_coco(json_file_path)

        # 转 voc 格式
        voc_boxs = coco_to_voc(coco_boxs)

        # 拼接保存的 xml 文件路径
        xml_file_path = os.path.join(xml_dir, os.path.splitext(file)[0] + '.xml')

        voc_xml(voc_boxs, image_path, xml_file_path)
        pass
