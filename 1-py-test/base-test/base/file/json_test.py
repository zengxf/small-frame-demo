"""
Json 测试
"""

import json

json_str = '{"name": "yoyo", "age": 30, "city": "shenzhen"}'

# 将 json 字符串转换为 dict 字典
json_to_dict = json.loads(json_str)
print(json_to_dict)  # {'name': 'yoyo', 'age': 30, 'city': 'shenzhen'}

# 将 dict 字典转换为 json 字符串
dict_to_json = json.dumps(json_to_dict)
print(dict_to_json)  # {'name': 'yoyo', 'age': 30, 'city': 'shenzhen'}
