"""
sort
"""

# 用法4 : 将 lambda 函数作为参数传递给其他函数, 比如前面列表中的 sort 函数的使用
# 案例 : 对字典列表进行排序，按年龄排序
dict_list = [{'name': 'yoyo', 'age': 27, 'sal': 2000},
             {'name': 'li', 'age': 34, 'sal': 2500},
             {'name': 'hu', 'age': 17, 'sal': 6000},
             {'name': 'liu', 'age': 22, 'sal': 3500},
             {'name': 'wang', 'age': 35, 'sal': 5000},
             {'name': 'zhao', 'age': None, 'sal': 6000}]

dict_list.sort(key=lambda d: d['age'] if d['age'] is not None else float('-inf'))
# 排序过程会使用 lambda d: d['age'] 提取每个字典中的 'age' 值，并按照这些值进行排序

print(dict_list)
# 注意因为 key 必须是返回每个元素的一个值, 数据中有 None 抛出异常, 必须给 None 赋值
