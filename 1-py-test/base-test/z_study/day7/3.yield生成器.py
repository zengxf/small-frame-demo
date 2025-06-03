# yield 生成器关键字
''' 生成器是一种特殊的迭代器，有一个暂停功能 '''


def fun():
    yield 1     # 生成器，返回生成器对象
    yield 2

# print(fun())    # 函数，返回生成器对象
# print(next(fun()))      # fun() 创建生成器
# print(next(fun()))

# 1. next 取值
g = fun()         # 创建生成器对象
print(next(g))
print(next(g))
# print(next(g))      # StopIteration


# 例子：用生成器实现斐波那契数列
def fib(a=1, b=1):
    # a, b = 1, 1
    while True:         # 死循环
        a, b = b, a + b
        yield b     # 暂停功能

# for _ in range(10):
#     print(next(fib()))    # 调用 fib() 时，重新创建生成器对象

# print(fib())
# g = fib()       # 创建生成器对象
# for _ in range(10):
#     print(next(g))


# 简化格式：yield from
def fun():
    yield from [1,2,3,4,5]

# 与下面格式相同
def fun1():
    for i in [1,2,3,4,5]:
        yield i

g1 = fun()
print(next(g1))     # 1
print(next(g1))     # 2
print(next(g1))