# 输出 50 个 # 字符
print('#' * 50)

# enumerate 内置函数：获取迭代对象的索引，生成索引序列，元组对（索引值, 元素）
# enumerate(迭代对象, 索引的初值)
lst = ['a', 'b', 'c']
for i in enumerate(lst):
    print(i)

# 先 col 后 row，可理解为最外层是 row 内层是 col
print([[col + 3 * row for col in range(1, 4)] for row in range(4)])

''' 30. 从 100 ~ 200 开始之间找出前 20 个质数，并计算他们的和（被除数/除数） '''
print([num for num in range(100, 201) if all([num % i for i in range(2, num)])])  # 内循环生成列表

# 完美数
print([num for num in range(1, 10000) if num == sum([i for i in range(1, num) if num % i == 0])])


def add2(x, y, z=10):
    c = x + y
    return c * z


# 3）关键字参数（调用时的实参，根据形参名字赋值）
print(add2(y=5, x=8))

''' 
2. 函数可变参数：位置可变 *args、关键字可变 **kwargs 
可变的关键字参数，必须在最后
'''


#        位置   可变位置   默认值  可变关键字
def parma(x, y, *args, k=1, **kwargs):
    return (x + y) * sum(args) / k - sum(kwargs.values())


print(parma(10, 20, 12, 23, c=30, b=2, a=1))
print(parma(10, 20, k=2))
print(parma(10, 20))


# 1. 返回值，可以是多个值。 返回元组对
def user_info():
    name = 'zhangsan'
    age = 20
    return name, age


na = user_info()
print(na)
n, a = user_info()
print(n, a)

# -------------------------------
# 函数装饰器

# 统计函数运行时间：import time
import time  # 统计时间


def decor_fun(func):  # 函数的参数是另一个函数（给函数增加功能）

    def count_run_time(*args, **kwargs):  # 函数参数可以是任意
        start = time.perf_counter()  # 开始时间，记录当前 cpu 时间
        res = func(*args, **kwargs)
        end = time.perf_counter()  # 结束时间
        print(f'调用{func.__name__}函数，共耗时 {end - start}')
        return res

    return count_run_time


@decor_fun  # @ 修饰符
def fun(n):
    return sum(i for i in range(n))


print(fun(10000))


# -----------------
# 闭包函数

# 定义函数，实现存取款功能（使用闭包实现）
def bank():
    balance = 1000

    def withdraw(money):
        nonlocal balance
        balance -= money
        return balance

    def deposit(money):
        nonlocal balance
        balance += money
        return balance

    return withdraw, deposit


withdraw, deposit = bank()
print(withdraw(100))
print(deposit(2000))

# +-------------------------
# os 模块

import os

# split 分割文件名， splitext 分割扩展名  文件中返回元组
dirs, filename = os.path.split(r'C:\Study\python33\day8\4.模块os.py')
print(dirs)
print(filename)

# join 拼接文件的路径，为文件操作中，拼接文件路径，
# 如果出现多个反斜杠（r'\a',r'\b','c'），以最后一个为主，\b\c, a清空
print(os.path.join('a', 'b', 'c'))  # a\b\c
print(os.path.join(r'\a', 'b', 'c'))  # \a\b\c
print(os.path.join(r'\a', r'\b', 'c'))  # \b\c
print(os.path.join('D:', r'\a', r'\b', 'c'))  # d:\b\c  盘符保留

# print(os.path.getsize(r'4.模块os.py'))
# print(os.path.getatime(r'4.模块os.py'))
# print(os.path.getctime(r'4.模块os.py'))
# print(os.path.getmtime(r'4.模块os.py'))


# ------------------------------
# time 模块

import time

print(time.strftime('%Y-%m-%d %H:%M:%S %p %A', time.localtime()))

# ------------------------------
# 随机 10 个 a~z 组成的字符串


import string
import random

length = 10
# 获取所有小写字母
letters = string.ascii_lowercase
# 随机选择指定长度的字母并拼接成字符串
random_string = ''.join(random.choice(letters) for _ in range(length))
print(random_string)


# ---------------------------------------

# 3. 类的属性和方法前面：1）使用单个下划线 _ 受保护的，2）使用双下划线 __ 私有的
# 首尾双下划线 __init__ 类内置的属性和方法（魔法函数 len、getitem、iter、call-类装饰器）

class Person:

    def __init__(self, name, age, sal):
        self.name = name  # 没有下划线，普通的
        self._age = age  # _属性，受保护的
        self.__sal = sal  # __属性，私有的

    def show(self):
        print(f'实例方法中都可以通过 self. 属性')
        return f'我叫{self.name}，今年多少岁{self._age}，薪资{self.__sal}'

    def _speak(self):
        return f'说中文{self.__sal}'

    def __speak(self):
        return f'说中文{self.__sal},{self._speak()}'


p1 = Person('yoyo', 20, 1000)
print(p1.name, p1.show())

print(p1._age, p1._speak())

# print(p1.__sal, p1.__speak())
print(p1._Person__sal)  # 强制访问： _类名__属性方法
print(p1._Person__speak())
# print(p1.__speak())
print(dir(p1))

#
#
# ------------------------------

''' 80. 数据批处理迭代器：在训练模型时，通常需要按批次处理数据，划分 batch 。
#     比如训练 data 为 [1 ~ 20]，每隔 batch_size 为 5 次输出一组数据 '''

lst = list(range(1, 21))
size = 5
lst_iter = iter(lst)
for _ in range(4):
    print([next(lst_iter) for _ in range(5)])


# [1, 2, 3, 4, 5]
# [6, 7, 8, 9, 10]
# [11, 12, 13, 14, 15]
# [16, 17, 18, 19, 20]


class DataIter:
    def __init__(self, data, size=5):
        self.data = data
        self.size = size
        self.start = 0

    def __iter__(self):
        return self

    def __next__(self):  # 必须判断，大于长度抛出 StopIteration
        if self.start >= len(self.data):
            raise StopIteration
        res = self.data[self.start: self.start + self.size]
        self.start += self.size
        return res


data = list(range(1, 23))
# print(data)
# print(data[20:25])
# print('*' * 20)
d = DataIter(data)
for i in d:
    print(i)

# ------------------------------

''' 5. 分别使用 % 和 f表达式，输出 number = 3.14159 的整数部分 和 小数部分（保留 3 位） '''
number = 3.14159
print(f'{int(number)}, 小数 {number:.3f}')

''' 7. 统计字符串 apple 中，字母 p 出现的次数 '''
print('apple'.count('p'))

''' 8. 用户输入一个字符串，如果是回文，输出 True，否则输出 False '''
print((s := input('输入字符串')) == s[::-1])

''' 9. 用户输入一个整数，判断是奇数还是偶数。如：输入 7 ， 输出 "奇数" '''
print(['偶数', '奇数'][int(input('输入整数')) % 2])

''' 10. 分别使用 % 和 f表达式格式化输出，我叫 yoyo, 今年 22 岁, 从事的工作是人工智能 '''
name, age, job = 'yoyo', 20, 'AI'
print(f'我叫 {name:20s}, 今年 {age:<20d} 岁, 从事的工作是{job:>20s}')

''' 13. 合并两个字典：输入 {'a': 4, 'b': 2} 和 {'b': 3, 'a': 1}，输出 {'a': 1, 'b': 2, 'c': 4} '''
d1 = {'a': 1, 'b': 3}
d2 = {'b': 2, 'c': 4}
d1.update(d2)
print(d1)

''' 14. 使用 * 解包，输出列表 lst = [19,7,9,13,20,29,3] 的最小值，最大值，以及剩余的列表元素 '''
lst = [19, 7, 9, 13, 20, 29, 3]
lst.sort()
min_xx, *mid_xx, max_xx = lst
print(min_xx, mid_xx, max_xx)

''' 17. 请设置密码，要求长度 8 ~ 16 位，首字母为大写，且必须符合标识符字符，否则按要求给出提示 '''
# pwd = input('设置密码:')
# if not (len(pwd)>8 and len(pwd)<16):
#     print('长度为 8 ~ 16 位')
# elif not pwd[0].isupper():
#     print('首字母要求大写')
# elif not pwd.isidentifier():
#     print('不符合标识符')
# else:
#     print('设置成功')


''' 26. 输入一个包含 1 ~ n 中部分数字的列表（无重复），找出缺失的所有数字（如 [3,5] → [1, 2, 4]）'''
# range 用法，max，列表只能+，集合-
# nums = list(map(int, input('输入多个数字用空格分割：').split()))
# print(list(set(range(1, max(nums))) - set(nums)))


''' 41. 找出所有水仙花数3位（个位^3+十位^3+百位^3=本身），计算他们的和 '''
# 思路：100 ~ 1000
print(sum([num for num in range(100, 1000) if num == ((num // 100) ** 3 + (num // 10 % 10) ** 3 + (num % 10) ** 3)]))

print([b * 100 + s * 10 + g for b in range(1, 10) for s in range(0, 10) for g in range(0, 10) if
       b ** 3 + s ** 3 + g ** 3 == b * 100 + s * 10 + g]
      )

''' 43. 将二维数组扁平化 [[2, -1, 3], [-5, 3, -2], [6, 2, 4]]，并过滤负值 '''
l = [[2, -1, 3], [-5, 3, -2], [6, 2, 4]]
# 思路：for 外面【】， 内 [2, -1, 3]  if 过滤
print([col for row in l for col in row if col > 0])

''' 45. 统计 "python pip,conda is anaconda pycharm code." 每个单词首字母出现的次数，以字典形式返回 {字母：次数} '''
s = "python pip,test pip,conda is anaconda pycharm code."
words = s.replace(',', ' ').split()
print(words)
print({c: list(word[0] for word in words).count(c) for c in list(word[0] for word in words)})

''' 64. 使用 iter 实现猜数字的游戏，要求记录次数并限定最大次数 5，以及提示范围 '''
import random


def guess_num():
    ''' 输入猜测的数字：实现逻辑 '''
    global start, end, count

    input_num = int(input(f'你有 {max_count - count} 次机会，请输入区间为 {start} ~ {end} 的数字：'))
    if input_num > rand_num:
        end = input_num
        print(f'猜大了，数字的范围在 {start} ~ {input_num}。{rand_num}')

    elif input_num < rand_num:
        start = input_num
        print(f'猜小了，数字的范围在 {input_num} ~ {end}。{rand_num}')

    count += 1
    return input_num


# 生成随机数
rand_num = random.randint(1, 100)
print('rand_num:', rand_num)
start, end = 1, 100
count = 0
max_count = 5

for i in iter(guess_num, rand_num):
    if count > 5:
        print('提示次数用完！')
        break
else:
    print('猜中了')

''' 65. 使用 yield 从列表 lst = [1,2,3,4,5,6,7,  8,9,10] 中，生成 4 个元素的子列表。
    如：[1,2,3]，[2,3,4] ... [8,9,10] '''


def sub_list(lst, size):
    for i in range(len(lst) - size + 1):
        yield lst[i: i + size]


lst = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
size = 2

sub = sub_list(lst, size)
for i in sub:
    print(i)

''' 69. 函数装饰器：统计递归函数（二进制）调用的次数，并记录每次调用日志（函数名、参数、返回值、调用层级） '''
print('#' * 100)


def log_info(func, count=0):
    depth = 0

    def logs(*args, **kwargs):
        nonlocal count, depth
        count += 1
        depth += 1
        indent = '\t' * (depth - 1)
        print(f'{indent}第 {count} 次进栈 {func.__name__} 函数，参数：{args}{kwargs}')
        res = func(*args, **kwargs)
        depth -= 1
        print(f'{indent}第 {count} 次出栈 {func.__name__} 函数，参数：{args}{kwargs}，返回值{res}')
        count -= 1
        return res

    return logs


@log_info
def binary(n):
    return '0b' if n == 0 else str(binary(n // 2)) + str(n % 2)


print(binary(10))
