# 推导式：由 for 循环构成，分4种：元组、列表、字典、集合
'''
1. 元组推导式返回的不是元组，是元组生成器。需要使用 list、tuple、set
2. 列表、字典、集合推导式，返回本身列表、字典（键值对）、集合（去重无序）
3. 由 for 循环，也可以嵌套 for，没有括号按顺序执行，有括号属于内循环
4. if 再循环后面，条件过滤，再 for 前面，选择判断。
5. 如果字典，选择和过滤时，都是针对值 value
'''

''' 格式1： [变量 for 变量 in 迭代对象] '''
# 案例：计算 1 ~ 10 中每个数的平方，返回列表
'''
lst = []
for i in range(1, 11):
    lst.append(i*i)
print(lst)
'''
print(tuple(i * i for i in range(1, 11)))
print('#' * 30, '1')
# <generator object <genexpr> at 0x0000021785A05BD0>

print([i * i for i in range(1, 11)])

# 计算 1 ~ 10 中每个数的平方，返回字典 {数字:平方}
print({i: i * i for i in range(1, 11)})


''' 格式2：[变量 for 变量 in 迭代对象 if 条件过滤] '''
# 案例：计算 1 ~ 10 之间，偶数的平方，返回字典
print({i: i*i for i in range(1, 11) if i % 2 ==0})


''' 格式3：[变量1 if 条件 else 变量2 for 变量 in 迭代对象] '''
# 案例：计算 1 ~ 10 之间，偶数的平方，奇数的三次方，返回字典
print({i: i**2 if i % 2 ==0 else i**3 for i in range(1, 11)})


''' 格式4：[[变量1 for 内变量1 in 迭代对象1] for 外变量2 in 迭代对象2]
         [变量1 for 外变量1 in 迭代对象1 for 内变量2 in 迭代对象2]'''

''' 31. 利用循环生成二维数组 [[1, 2, 3], [4, 5, 6], [7, 8, 9]] '''
# [[1, 2, 3],
# [4, 5, 6],
# [7, 8, 9]]
# 思路：
# 1. 定义空列表 【】
# 2. 外循环3次3个元素，添加 3行  0，1，2
# 3. 定义空列表 【1，2，3】
# 4. 嵌套循环填充值，1，2，3    3*外+1
# 5. 循环中列表，添加到最外的列表
lst = []
for row in range(3):            # 1 行   0 123  1 456  2 789
    # print(row)
    l = []
    for col in range(1, 4):
        l.append(col + 3 * row)
    lst.append(l)
print(lst)

print('#' * 50, 2)
print([[col + 3 * row for col in range(1, 4)] for row in range(4)])


''' 30. 从 100 ~ 500 开始之间找出前 20 个质数，并计算他们的和（被除数/除数） '''
# 思路：做了 11 是否质数--> num = 11
# 可以保存到列表中，通过切片[:20]
# for num in range(100, 501):
lst = []
for num in range(100, 501):  # 取 100 ~ 500
    flag = 1
    for i in range(2, num):  # 除数
        if num % i == 0:
            flag = 0
            break
    if flag:    # 变量，改变
        lst.append(num)
print(lst[:20])

print('#' * 50, 3)
print([num for num in range(100, 501) if not any(True if num % i == 0 else False for i in range(2, num))])
print([num for num in range(100, 501) if all(False if num % i == 0 else True for i in range(2, num))])
print([num for num in range(100, 501) if all(num % i for i in range(2, (num + 1) // 2))])
print([num for num in range(100, 501) if all([num % i for i in range(2, num)])])  # 内循环生成列表


print('#'*50)
# 3. 使用列表推导式，生成前 4 个完美数（完美数 = 除本身外其他因子数的和）
# 6 : 1 + 2 + 3 = 6
num = 6
# 1. 找因字数，去掉 6    被除数 6   除数 1~6  找余数为0
lst = []
for i in range(1, num):
    if num % i == 0:
        lst.append(i)
if num == sum(lst):
    print(True)

# [num for num in range(1, 1000) if num == sum(因子数)]
print([num for num in range(1, 10000) if num == sum([i for i in range(1, num) if num % i == 0])])


''' 练习 '''
# 推导式练习题（共 10 题）
# 1. 生成平方列表
# 生成一个包含 0~9 每个数的平方的列表。
#
# 2. 筛选偶数
# 给定一个整数列表 [1, 2, 3, 4, 5, 6, 7, 8, 9]，用推导式筛选出其中的偶数。
#
# 3. 将字符串列表转换为大写
# 将 ['apple', 'banana', 'cherry'] 中的每个字符串转换成大写。
#
# 4. 找出所有能被 3 整除的数字
# 生成一个 1~30 的整数列表，找出其中能被 3 整除的数。
#
# 5. 长度大于3的单词
# 给定一个字符串列表 ['hi', 'hello', 'yes', 'no', 'great']，找出长度大于 3 的单词。
#
# 6. 字典推导式：反转键值
# 给定字典 {'a': 1, 'b': 2, 'c': 3}，用推导式交换键和值，得到 {1: 'a', 2: 'b', 3: 'c'}。
#
# 7. 字符串中提取数字字符
# 给定字符串 'a1b2c3d4'，用推导式提取出数字字符，生成 ['1', '2', '3', '4']。
#
# 8. 嵌套推导式：二维数组转一维
# 将二维列表 [[1, 2], [3, 4], [5, 6]] 展平为一维列表 [1, 2, 3, 4, 5, 6]。
#
# 9. 生成带条件的元组列表
# 生成 (x, y) 的列表，其中 x 和 y 从 1 到 5，且 x != y。
#
# 10. 替换列表中的负数为 0
# 给定列表 [-3, 4, -2, 7, -1]，使用推导式将所有负数替换为 0，得到 [0, 4, 0, 7, 0]。