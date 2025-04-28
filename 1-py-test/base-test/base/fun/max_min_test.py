"""
max | min
最大|最小函数
"""

# 2. 匿名函数 lambda, 映射一个可迭代对象
# 案例 : 给字典列表 dict_list 的每个用户的信息中，新增一个键 birth = 2024 - age
dict_list = [{'name': 'yoyo', 'age': 27, 'sal': 2000},
             {'name': 'li', 'age': 34, 'sal': 2500},
             {'name': 'hu', 'age': 17, 'sal': 6000},
             {'name': 'liu', 'age': 22, 'sal': 3500},
             {'name': 'wang', 'age': 35, 'sal': 5000},
             {'name': 'zhao', 'age': None, 'sal': 6000}]
print(list(map(lambda d: {**d, 'birth': 2024 - d['age'] if d['age'] is not None else 2024}, dict_list)))

print("---------------------------------\n")
# 案例：找出 dict_list 中最高薪资的用户信息
# 首先过滤 None 值
filter_none = list(filter(lambda d: d['sal'] is not None, dict_list))

# 找出最大薪资
max_sal = max(filter_none, key=lambda d: d['sal'])['sal']  # 只能一个 6000

# 在找出等于最高薪资的用户
print([d for d in filter_none if d['sal'] == max_sal])
# [{'name': 'hu', 'age': 17, 'sal': 6000}, {'name': 'zhao', 'age': 19, 'sal': 6000}]
