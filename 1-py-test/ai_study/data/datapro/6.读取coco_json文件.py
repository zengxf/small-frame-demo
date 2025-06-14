''' json 文件格式，json格式与 dict 字典 '''
from textwrap import indent

json = {"键1": "值1", "键2": "值2"}
# json 键只能是字符串，值是类型不限。json 格式中只能用双引号

import json

json_file_path = r'./label-data/test_00000546.json'
size_w, size_h = 180, 180

''' 1. 读取 json 文件：load、loads 
    json.load(读取文件对象)
    json.loads(字符串)
'''
with open(json_file_path, mode='r', encoding='utf-8') as json_file:
    content = json.load(json_file)

    annotations = content[0]['annotations']
    for labels in annotations:
        class_name = labels['label']
        # bbox = labels['coordinates']
        x, y, width, height = [float(labels['coordinates'][k]) for k in ['x', 'y', 'width', 'height']]
        # x=xmin， y=ymin， width框宽，height框高

        voc = [x, y, x + width, y + height]
        print('voc数据', voc)
        yolo = [(x + width / 2) / size_w, (y + height / 2) / size_h, width / size_w, height / size_h]
        print('yolo数据', yolo)

with open(json_file_path, mode='r', encoding='utf-8') as f:
    # f 文件对象，load 直接读取对象
    content = json.loads(f.read())
pass

''' 2. 写入 json 文件：dump、dumps 
    json.dump(json数据, 可写的文件对象, indent = 4)      # 自动写入
    json.dumps(json数据)    # 返回json字符串，需要手动写入文件
'''
data = {"name": "yoyo", "age": 20}
with open('./label-data/new_json.json', mode='w', encoding='utf-8') as f:
    json.dump(data, f, indent=4)

# json.dumps 将 data（字典）转为 json 格式的字符串
json_str = json.dumps(data, indent=4)
# print(json_str,type(json_str))
with open('./label-data/new_json1.json', mode='w', encoding='utf-8') as f:
    f.write(json_str)
