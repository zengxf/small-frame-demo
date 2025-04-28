"""
sorted 排序函数
"""

dict_list = [{'name': 'yoyo', 'age': 27, 'sal': 2000},
             {'name': 'li', 'age': 34, 'sal': 2500},
             {'name': 'hu', 'age': 17, 'sal': 6000},
             {'name': 'liu', 'age': 22, 'sal': 3500},
             {'name': 'wang', 'age': 35, 'sal': 5000},
             {'name': 'zhao', 'age': None, 'sal': 6000}]

# 案例：对字典列表进行排序 dict_list 中的 sal 进行排序
new_list = sorted(dict_list, key=lambda d: d['sal'] if d['sal'] is not None else float('-inf'))

print(new_list)
