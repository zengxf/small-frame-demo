# 集合 set, 与字典一样使用 {} 大括号
# 1. 集合对元素进行去重
# 2. 集合是无序的 , 不能索引
# 3. 元素不能是可变数据类型:列表\字典\集合
s = {1, 'a', 2, 3, 'b', 4, 5, 1, 2, 3}
print(s)

s1 = {}  # 空字典
s2 = set()  # 空集合

s3 = {1, 3.14, True, 'hello', (1, 2, 3)}
print(s3, type(s3))

# set(可迭代对象)   将可迭代对象转为集合
print(set('hello'))
d = {'hello': 1, 'age': 2}
print(set(d))  # {'hello', 'age'}


# 集合的操作: 交集-&  并集-|  补集-^  差集 -
s4 = {1, 3, 4, 6}
s5 = {1, 2, 4, 7, 8}
# 交集,找两边都出现的 {1, 4}
print(s4 & s5)
print(s4.intersection(s5))

# 并集,合并数据   {1, 2, 3, 4, 6, 7, 8}
print(s4 | s5)
print(s4.union(s5))

# 差集, 减
print(s4 - s5)      # {3, 6}
print(s4.difference(s5))        # 返回 s4

print(s5 - s4)      # {8, 2, 7}
print(s5.difference(s4))

# 补集, 返回 并集 - 交集  {2, 3, 6, 7, 8}
print(s4 ^ s5)
print(s4.symmetric_difference(s5))


# update 在原集合上进行更新
s4.update({2,3,4,5,5})       # None
print(s4)       # {1, 2, 3, 4, 6, 7, 8}

s4 = {1, 3, 4, 6}
s5 = {1, 2, 4, 7, 8}
s4.difference_update(s5)        # 求 s4 - s5 的差集, 返回s4
s4.intersection_update(s5)
s4.symmetric_difference_update(s5)
print(s4)

print('#'*50)
s6 = {1,2,3,4,5}
s7 = {7,8,9}
# s7.difference_update(s6)
# print(s7)
# print(s6)

# print(s7.issubset(s6))      # s7 是否是 s6 的子集
# print(s6.issuperset(s7))      # s6 是否是 s7 的父集
# print(s6.isdisjoint(s7))        # 是否有交集


###### 集合的增 add(元素) 删改
s8 = set()
s8.add(1)
s8.add(2)
print(s8)


s9 = {1, 'a', 2, 3, 'b', 4, 5, 1, 2, 3}
print(s9)
s9.pop()    # 删除第一个元素, 无序随机删除
print(s9)


s10 = s9.copy()
print(s10)

s10.clear()
print(s10)

