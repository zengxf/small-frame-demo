"""
装饰器 decorator

在 Python中装饰器有 2 种：
1. 函数装饰器，使用 @funciton_name 来修饰
2. 类装饰器：使用 @class_name 来修饰

在 Python 中函数参数除了：位置参数、默认参数、关键字参数、个数可变参数以外，
    还可以使用另一个函数作为该函数的参数。这种情况就称为装饰器。

类装饰器：通常使用 __call__ 方法实现，使类的实例对象像函数一样调用。
"""

# 创建装饰器函数，统计函数执行时间
import time


def decorator_count_time(function):
    def wrapper(*args, **kwargs):
        start_time = time.time()
        res = function(*args, **kwargs)  # 参数传递过去
        end_time = time.time()
        print(f'函数执行的总时间：{end_time - start_time:.15f}')
        return res

    return wrapper


@decorator_count_time
def sum_number(totol=0):
    for i in range(100000):
        totol += i
    return totol


print(sum_number(0))
print(sum_number(100))
print("****************************\n")


# 创建装饰器函数，统计斐波那契数列递归时，递归调用的次数
def create_decorator_count(function):
    count = 0

    def wrapper(*args, **kwargs):
        nonlocal count
        count += 1
        print(f'调用 {function.__name__} 函数 {count} 次 args: {args}')
        return function(*args, **kwargs)

    return wrapper


# 使用装饰器，定义函数实现斐波那契数列
@create_decorator_count
def fib(n):
    return n if n <= 1 else fib(n - 1) + fib(n - 2)


print(fib(4))  # 1 1 2 3
