"""
zip

zip 函数是将多个可迭代对象（字符串、元组、列表、字典）压缩到一起，生成一个元组迭代器，
每个元组包含迭代对象的每个元素，规则是按照最短的迭代对象进行处理。

同时也可以使用 zip 的解包操作符 * 进行解压可迭代对象，返回的是元组
"""

# 1. 将 2 个可迭代对象合并为一个元组列表
print(list(zip('python', [1, 2, 3, 4, 5])))  # [('p', 1), ('y', 2), ('t', 3), ('h', 4), ('o', 5)]

# 2. 将压缩的迭代对象进行解包, 返回元组
zipped = [('a', 1, True), ('b', 2, False), ('c', 3, True)]
l1, l2, l3 = zip(*zipped)
print(l1, l2, l3)  # ('a', 'b', 'c') (1, 2, 3) (True, False, True)
#


print("\n--------------------------")
# 案例 : 计算下面 3 个列表的元素对应位置的求和
lst1 = [1, 2, 3]
lst2 = [10, 20, 30]
lst3 = [100, 200, 300]
# 使用 zip 将三个列表合并
zipped = zip(lst1, lst2, lst3)
# 使用 map 和 lambda 对每组元素进行运算
result = map(lambda x: x[0] + x[1] + x[2], zipped)
# 将 map 对象转换为列表以查看结果
result_list = list(result)
print(result_list)  # [111, 222, 333]
