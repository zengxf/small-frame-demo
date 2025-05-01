"""
推导式

推导式（列式解析）：是 Python 中独特的一种数据处理方式，主要有元组、列表、字典、集合。
列表推导式：利用 for 循环从迭代对象中快速生成一个满足条件的新列表。从 for 开始执行。

基本格式：[输出表达式 for 变量 in 迭代对象]
带 if 格式：[输出表达式 for 变量 in 迭代对象 if 条件表达式]
带 if else 格式：[输出表达式1 if 条件表达式 else 输出表达式2 for 变量 in 迭代对象]
带 for 嵌套格式： [输出表达式 for 变量1 in 迭代对象1 for 变量2 in 迭代对象2]
"""

# 导入模块 string
import string

# 生成 1 到 10 之间的偶数，返回到列表中
# 常规写法的代码如下：
# 定义一个空列表 lst
lst = []
# 开始循环
for i in range(2, 11, 2):
    # 每次取 i 的值，添加到 lst 中
    lst.append(i)
else:
    print("for end...")
# 结束循环，打印 lst
print(lst)  # [2, 4, 6, 8, 10]
# 改写推导式：从 for 循环开始写
print([i for i in range(2, 11, 2)])  # [2, 4, 6, 8, 10]

# 带 if 的推导式，生成 1 到 10 之间的偶数，返回到列表中
print("\n------------------------")
# 改写推导式：从 for 循环开始写
print([i for i in range(1, 11) if i % 2 == 0])  # [2, 4, 6, 8, 10]

# 带 if else 的推导式， 1 到 10 之间，如果是偶数返回本身，如果是奇数返回平方
print("\n------------------------")
lst = []  # 定义一个空列表 lst
# 开始循环
for i in range(1, 10):
    # 每次取 i 的值，通过 if 语句判断
    if i % 2 == 0:  # 判断余数，为 0 时，将 i 添加到 lst
        lst.append(i)
    else:  # 否则，将 i * i 添加到 lst
        lst.append(i * i)
# 结果循环，打印 lst
print(lst)  # [1, 2, 9, 4, 25, 6, 49, 8, 81]
# 改写推导式：从 for 循环开始写
print([i if i % 2 == 0 else i * i for i in range(1, 10)])  # [1, 2, 9, 4, 25, 6, 49, 8, 81]

# 带 for 嵌套的推导式， 生成 -1 到 1 之间整数的坐标，返回元组列表
print("\n------------------------")
# 定义空列表 lst
lst = []
# 开始输出 x 的坐标，-1，0，1
for x in range(-1, 2):
    # 循环 y 的 坐标，-1，0，1
    for y in range(-1, 2):
        # 添加坐标 (x,y) 到 lst中
        lst.append((x, y))
# 结束循环，打印 lst
print(lst)
# 改写推导式
print([(x, y) for x in range(-1, 2) for y in range(-1, 2)])

# 计算前 10 个斐波那契数列的平方，映射到字典中
print("\n------------------------")
print("斐波那契数列")
# 定义斐波那契数列的初值
fib = [0, 1]
# 生成前 10 天的数
for i in range(6):
    fib.append(fib[i] + fib[i + 1])  # append是在原列表中添加
# 打印前 10 天的斐波那契数列的值
print(fib)

print("\n------------------------")
# 使用字典推导式，映射每个数的平方
print({i: i ** 2 for i in fib})

# print("\n------------------------")
# # 定义空集合 set1
# set1 = set()
# # 开始循环，读取字符串
# for c in input('请输入字符串：'):
#     if c in 'aoeiu':  # 判断是否为元音字母
#         set1.add(c)  # 如果是，将元素添加到 set1 集合中
# print(set1)
# # 改写推导式
# print({c for c in input('请输入字符串：') if c in 'aoeiu'})

print("\n------------------------")
# 定义空集合 set1
set1 = set()
set1.add('a')
set1.add('21')
set1.add('d')
set1.add('e')
print(set1)
# 改写推导式
print({c for c in set1 if c in 'aoeiu'})

print("\n------------------------")
# 案例：生成 a ~ z 的 ascii 吗，返回元组对 ((a,97）,(b,98))

print(string.ascii_lowercase)
print(f'ord("c") = {ord("c")}')
print(((c, ord(c)) for c in string.ascii_lowercase))  # <generator object <genexpr> at 0x000001BBBC37F5A0>
# 使用 tuple 转元组，也可以使用 list、set、dict(需要满足键值对) print(tuple((c,ord(c)) for c in string.ascii_lowercase))
print(list((c, ord(c)) for c in string.ascii_lowercase))
print(set((c, ord(c)) for c in string.ascii_lowercase))
print(dict((c, ord(c)) for c in string.ascii_lowercase))
