''' xml 格式： '''

'''
<root>      标签
    <name> '属性值' </name>    # name 属性 == '属性值'
</root>
'''

# 1. 导入内置模块
import xml.etree.ElementTree as ET

# 2. 解析 xml 文件，返回元素树对象
xml_file_path = r'C:\Study\datapro\voc_label\test_00000546.xml'
tree = ET.parse(xml_file_path)
print(tree)         # xml.etree.ElementTree.ElementTree object

# 3. 从元素树对象中获取根
root = tree.getroot()
print(root)         # Element 'annotation'

# 4. 从 root 下面获取（size 图片大小，name 标注的类别，bbox框的: xmin,ymin,xmax,ymax)
# 路径：/ 表示绝对路径         // 表示相对路径
# 1）size 大小：width，height
width = root.find('size/width').text
print(type(width),width)            # <class 'str'>

height = root.findtext('size/height')
print(height)

# 2）name，bndbox（如果多个框，都在 object 子标签中）
objects = root.findall('object')        # 返回列表，objects 元素树对象
print(objects)

# 多个循环读取：for
bbox = []
for obj in objects:
    class_name = obj.findtext('name')
    xmin = obj.find('bndbox/xmin').text
    ymin = obj.find('bndbox/ymin').text
    xmax = obj.findtext('bndbox/xmax')
    ymax = obj.findtext('bndbox/ymax')

    bbox.append([class_name, int(xmin), int(ymin), int(xmax), int(ymax)])
    print(bbox)

# voc 保存到 .xml 文件格式
# yolo 生成 txt文件：类别 classes.txt  标签 class_id, x,y中心点,w,h框宽高

# 作业：
# 1. 读取 voc   szie, name, box 。。。除以图片 size 归一化 yolo 数据
''' ['face', 70, 22, 152, 104], ['mask', 83, 65, 140, 122] '''

# 2. 转 yolo 格式 [class_id，x_center, y_center, 框w, 框h]
''' [0, 70/size_w, 22/size_h, 152/size_w, 104/size_h], [1, 83, 65, 140, 122]  '''
''' [0 0.500000 0.513333 0.546667 0.546667]  '''

# 3. 写入 txt
''' 文件：标签文件 [0 0.500000 0.513333 0.546667 0.546667]  类别文件 face '''