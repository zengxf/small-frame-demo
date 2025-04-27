"""
生成器

生成器定义：就是返回一个值的特殊的迭代器，通常使用 yield关键字来创建的函数，
    并且暂停函数的执行状态，直到下一次调用 next(生成器函数)。
特点：惰性生成，有暂停恢复功能，节省内存空间
"""


# 案例：用生成器，来斐波那契数列
def fib(one=1, two=1):  # 创建生成器函数
    while True:
        yield one
        one, two = two, one + two


fib_genertor = fib()  # 通过生成器函数创建生成器对象

print(next(fib_genertor))
print(next(fib_genertor))
print([next(fib_genertor) for _ in range(10)])
