"""
map 映射函数

map 函数用于将一个函数应用于一个或多个可迭代对象（比如 列表、元组、字典）中的每一个元素，
    并返回一个 map 对象（迭代器）。如果是多个迭代对象使用时，按照最短的对象进行处理
"""

# 1. 内置函数 str
# 案例 : 将整数列表的元素映射为字符串
lst = [1, 2, 3, 4, 5]
print(list(map(str, lst)))  # ['1', '2', '3', '4', '5']

# 2. 匿名函数 lambda, 映射一个可迭代对象
# 案例 : 给字典列表 dict_list 的每个用户的信息中，新增一个键 birth = 2024 -age
dict_list = [{'name': 'yoyo', 'age': 27, 'sal': 2000},
             {'name': 'li', 'age': 34, 'sal': 2500},
             {'name': 'hu', 'age': 17, 'sal': 6000},
             {'name': 'liu', 'age': 22, 'sal': 3500},
             {'name': 'wang', 'age': 35, 'sal': 5000},
             {'name': 'zhao', 'age': None, 'sal': 6000}]
print(list(map(lambda d: {**d, 'birth': 2024 - d['age'] if d['age'] is not None else 2024}, dict_list)))

# 3. 匿名函数 lambda, 映射多个迭代对象
# 案例 : 将 2 个 lst 的元素, 映射为字典格式
lst1 = ['a', 'b', 'c']
lst2 = [1, 2, 3, 4, 5]
print(dict(map(lambda x, y: (x, y), lst1, lst2)))  # {'a': 1, 'b': 2, 'c': 3}
