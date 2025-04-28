"""
set 测试
"""

# 对象为字典，只返回字典的键
s4 = set({'name': 'yoyo', 'sex': 'man'})
print(s4)  # 返回：{'name', 'sex'}

# 集合操作符：交集 &、并集 |、差集 - 、补集 ^
set2 = {1, 2, 3, 4, 7, 8}
set3 = {3, 4, 5, 7, 9}

# & ：交集，返回相同的部分
print(set2 & set3)  # 返回：{3, 4, 7}
# | ：并集，返回全部的内容
print(set2 | set3)  # 返回：{1, 2, 3, 4, 5, 7, 8, 9}
# - ：差集，返回第一个集合中不相同的部分
print(set2 - set3)  # 返回：{8, 1, 2}
# ^ ：补集，返回不相同的部分
print(set2 ^ set3)  # 返回：{1, 2, 5, 8, 9}
print("--------------------\n")

set7 = set((1, 2, 3, 4, 5))
set8 = set((2, 3, 4))
print(f'set7: {set7}')
print(f'set8: {set8}')
# 1. 判断2个集合中是否有相同的元素，如果有返回 False，否则返回 True
# 格式：集合1.isdisjoint(集合2)
print(set7.isdisjoint(set8))  # 返回： False
# 2.判断集合1是否为集合2的子集，如果是返回 True，否则返回 False
# 格式：集合1.issubset(集合2)
print(set7.issubset(set8))  # 返回： False
# 3. 判断集合2是否为集合1的子集，如果有返回 True ，否则返回 False
# 格式：集合1.issuperset(集合2)
print(set7.issuperset(set8))  # 返回： True
