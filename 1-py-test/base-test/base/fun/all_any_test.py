"""
all | any 判断函数

all | any 判断（全部、某个）函数是判断可迭代对象中的所有元素的布尔值，返回布尔值（True、 False）。
all 全部时也可以结合自定义函数 lambda ，从可迭代对象中的找极值
"""

import math

dict_list = [{'name': 'yoyo', 'age': 27, 'sal': 2000},
             {'name': 'li', 'age': 34, 'sal': 2500},
             {'name': 'hu', 'age': 17, 'sal': 6000},
             {'name': 'liu', 'age': 22, 'sal': 3500},
             {'name': 'wang', 'age': 35, 'sal': 5000},
             {'name': 'zhao', 'age': None, 'sal': 6000}]

# 案例：判断 dict_list 中, 是否有 None 数据
print(all(d['name'] and d['age'] and d['sal'] is not None for d in dict_list))  # False

# 案例：检查下面二维矩阵中的数据是否都是在 0 ~ 1 之间的浮点数
matrix = [
    [0.5488135039273248, 0.7151893734100989, 0.6027633760716439],
    [0.5448831829968969, 0.4236547993389047, 0.6458941130666561],
    [0.4375872112626925, 0.8917730007820798, 0.9636627605010293]]

print(all(all(map(lambda v: 0 < v < 1 and math.isfinite(v), row)) for row in matrix))

# # 案例：验证 lst 中所有字符串的结尾是否已指定字符（.txt）结尾
str_lst = ['101.txt', '102.txt', '103.txt', '104.txt']
print(all(map(lambda c: c.endswith('.txt'), str_lst)))
# print(all(e.endswith('.txt') for e in str_lst))
# 注意：推导式导致代码冗余，函数使代码更简洁。性能差异不大
