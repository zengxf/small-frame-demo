'''yolo 转 coco
1. yolo: class_id, x, y, w, h (归一化/size)
2. coco: class_name, xmin, ymin, w, h '''

import os
from voc2yoyo import read_image
import json


def read_yolo(yolo_file):
    yolo_boxs = []
    if file != 'classes.txt':
        with open(file_path, mode='r', encoding='utf-8') as f:
            for row in f.readlines():
                yolo_box = row.strip().split()
                yolo_boxs.append([int(yolo_box[0]), *map(float, yolo_box[1:])])

    return yolo_boxs


# [[0, 0.5, 0.513333, 0.546667, 0.546667], [1, 0.5, 0.513333, 0.546667, 0.546667]]
def yolo_to_coco(yolo_boxs, class_name, img_width, img_height):
    coco_boxs = []
    for box in yolo_boxs:
        class_id, x, y, w, h = box
        x = (x - w / 2) * img_width
        y = (y - h / 2) * img_height
        width = w * img_width
        height = h * img_height

        coco_boxs.append([class_name[class_id], x, y, width, height])
    return coco_boxs


def coco_json(coco_boxs, image_name, coco_dir):
    # 构造 json 文件格式 [{image：图片名, annotations：[{label:类别,coordinates:{x:y:w:h}}]}]
    annotation = []
    for box in coco_boxs:
        class_name, x, y, width, height = box
        annotation.append({'label': class_name,
                           'coordinates': {'x': x, 'y': y, 'width': width, 'height': height}})

    json_data = [{'image': os.path.basename(image_name), 'annotations': annotation}]

    coco_file_name = os.path.join(coco_dir, os.path.splitext(os.path.basename(image_name))[0] + '.json')

    with open(coco_file_name, mode='w', encoding='utf-8') as f:
        json_str = json.dumps(json_data, indent=4)
        f.write(json_str)


if __name__ == '__main__':
    # yolo 标签目录：类别文件、标签文件
    yolo_label_dir = r'label-data'

    # 图片目录
    image_dir = r'label-img'

    # 创建 coco_dir
    coco_dir = r'label-data/coco_json_dir'
    os.makedirs(coco_dir, exist_ok=True)

    # 获取目录下的文件
    yolo_files = os.listdir(yolo_label_dir)
    yolo_files = [file for file in yolo_files if file.endswith('.txt')]

    # classes.txt 文件，获取 {}
    class_name_file = os.path.join(yolo_label_dir, 'classes.txt')
    with open(class_name_file, mode='r', encoding='utf-8') as f:
        class_name = {i: c.strip() for i, c in enumerate(f.readlines())}

    # 读取 yolo_label 文件
    for file in yolo_files:
        if file != 'classes.txt':
            # 拼接文件路径
            file_path = os.path.join(yolo_label_dir, file)
            yolo_boxs = read_yolo(file_path)

            image_name = os.path.join(image_dir, os.path.splitext(file)[0] + '.jpg')
            img_width, img_height = read_image(image_name)

            coco_box = yolo_to_coco(yolo_boxs, class_name, img_width, img_height)

            coco_json(coco_box, image_name, coco_dir)
