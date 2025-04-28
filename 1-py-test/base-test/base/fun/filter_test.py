"""
filter 过滤函数
"""

dict_list = [{'name': 'yoyo', 'age': 27, 'sal': 2000},
             {'name': 'li', 'age': 34, 'sal': 2500},
             {'name': 'hu', 'age': 17, 'sal': 6000},
             {'name': 'liu', 'age': 22, 'sal': 3500},
             {'name': 'wang', 'age': 35, 'sal': 5000},
             {'name': 'zhao', 'age': None, 'sal': 6000}]

# 案例：对字典列表进行排序 dict_list 中过滤 age 不为 None
print(filter(lambda d: d['age'] is not None, dict_list))  # <filter object at 0x0000012D65F65420>

print(list(filter(lambda d: d['age'] is not None, dict_list)))
