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