# 函数装饰器：在不改变原函数的功能的情况下，给原函数额外增加新功能。日志、时间
# 函数装饰器的本质就是函数，函数就是闭包函数

# 函数的参数还可以函数，或类
''' 使用语法糖 ： @引用函数装饰器 '''
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


@decor_fun
def fun1(x, y):
    return x ** y


print(fun1(3, 2))

# 定义函数装饰器：记录二进制递归函数，记录日志每次递归 商，余数
# print(f'第 {"次数"} 次调用 {"递归函数名"}，它的 {"参数"}, {"商"}、{"余数"}')
def decor_log_info(func):
    count = 0

    def count_bin_div_mod(*args, **kwargs):
        """'第 {"次数"} 次调用 {"func.__name__"}，它的 {"n"}, {"商"}、{"余数"}'"""
        nonlocal count
        count += 1
        print(f'第 {count} 次调用 {func.__name__}，参数 {args}，商 {args[0] // 2}，余数 {args[0] % 2}')
        return func(*args, **kwargs)

    return count_bin_div_mod

@decor_log_info
def clac_binary(n):
    return '0b' if n == 0 else str(clac_binary(n // 2)) + str(n % 2)

print(clac_binary(10))
