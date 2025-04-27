"""
xml.etree.ElementTree 模块

xml 读取
"""

import xml.etree.ElementTree as ET

# 实例操作：user.xml
xml_file_path = r'user.xml'

# 通过 parse() 函数，返回一个 ElementTree 对象，该对象表示整个 XML 文档的树结构 '''
tree = ET.parse(xml_file_path)  # <xml.etree.ElementTree.ElementTree object at 0x0000022499A92F50>

# 通过 getroot() 函数，从 ElementTree 对象中获取根元素 tag 标签
root = tree.getroot()  # <Element 'data' at 0x000001E4918CD2B0>

# 通过 find('标签') 查找姓名，年龄。text 返回 str 文本
# 通过 findall('标签')查找所有的姓名，年龄。返回 list列表
name = root.find('info/name').text  # 一个 / 表示绝对路径
age = root.findtext('info//age')  # 两个 // 表示相对路径
print(f'姓名：{name}，年龄：{age}')
