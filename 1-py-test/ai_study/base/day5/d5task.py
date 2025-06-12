# 练习：写斐波那契数列，输入前20个，返回列表 1，1，2，3，5，8，13
def fib(n):
    fib_list = []
    a, b = 1, 1
    for i in range(n):
        fib_list.append(a)
        a, b = b, a + b
    return fib_list


print(fib(20))
print(len(fib(20)))

''' 练习 '''
# 推导式练习题（共 10 题）
# 1. 生成平方列表
# 生成一个包含 0~9 每个数的平方的列表。
print([i * i for i in range(10)])

# 2. 筛选偶数
# 给定一个整数列表 [1, 2, 3, 4, 5, 6, 7, 8, 9]，用推导式筛选出其中的偶数。
print([i for i in range(1, 10) if i % 2 == 0])

# 3. 将字符串列表转换为大写
# 将 ['apple', 'banana', 'cherry'] 中的每个字符串转换成大写。
print([s.upper() for s in ['apple', 'banana', 'cherry']])

# 4. 找出所有能被 3 整除的数字
# 生成一个 1~30 的整数列表，找出其中能被 3 整除的数。
print([i for i in range(1, 30) if i % 3 == 0])

# 5. 长度大于3的单词
# 给定一个字符串列表 ['hi', 'hello', 'yes', 'no', 'great']，找出长度大于 3 的单词。
print([s for s in ['hi', 'hello', 'yes', 'no', 'great'] if len(s) > 3])

# 6. 字典推导式：反转键值
# 给定字典 {'a': 1, 'b': 2, 'c': 3}，用推导式交换键和值，得到 {1: 'a', 2: 'b', 3: 'c'}。
print({v: k for k, v in {'a': 1, 'b': 2, 'c': 3}.items()})

# 7. 字符串中提取数字字符
# 给定字符串 'a1b2c3d4'，用推导式提取出数字字符，生成 ['1', '2', '3', '4']。
print([i for i in 'a1b2c3d4' if i.isdigit()])

# 8. 嵌套推导式：二维数组转一维
# 将二维列表 [[1, 2], [3, 4], [5, 6]] 展平为一维列表 [1, 2, 3, 4, 5, 6]。
print([j for sub in [[1, 2], [3, 4], [5, 6]] for j in sub])

# 9. 生成带条件的元组列表
# 生成 (x, y) 的列表，其中 x 和 y 从 1 到 5，且 x != y。
print([(x, y) for x in range(1, 6) for y in range(1, 6) if x != y])

# 10. 替换列表中的负数为 0
# 给定列表 [-3, 4, -2, 7, -1]，使用推导式将所有负数替换为 0，得到 [0, 4, 0, 7, 0]。
print([0 if i < 0 else i for i in [-3, 4, -2, 7, -1]])
