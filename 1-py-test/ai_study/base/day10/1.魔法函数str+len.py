# 魔法函数：在类中首尾使用双下划线，内置的函数 len、call、getitem、iter
''' 1. __str__ 返回字符串，用来描述类 '''


class MagicFun:

    def __init__(self, 形参):
        self.属性 = 形参

    def __str__(self):
        return '这是一个字符串'


m = MagicFun('12345')
print(m)

''' 2. __len__ 使实例属性兼容 len 函数的功能 '''


class MagicLen:

    def __init__(self, data):
        self.data = data  # 实例属性

    def __len__(self):
        return len(self.data)


lst = [1, 2, 3]
m2 = MagicLen(lst)  # 实例对象，没有 len
print(dir(m2))
print('__len__' in dir(m2))
print(len(m2))

''' 3. __call__ 使实例对象，当函数去使用 '''


class MagicCall:

    def __init__(self, n):
        self.属性 = n

    def __call__(self, *args, **kwargs):
        return [self.属性 + _ for _ in args]


# 实例对象  打点 .
m3 = MagicCall(10)
print(m3, type(m3))

# 函数，调用 函数()
print(m3(1, 2, 3, 4))

# 利用 call 方法实现类装饰器
''' 78. 定义类装饰器，记录函数运行时间 '''
import time


class Timer:

    def __init__(self, func):
        self.func = func
        print('func: ', func.__name__)

    def __call__(self, *args, **kwargs):
        start = time.perf_counter()
        res = self.func(*args, **kwargs)
        end = time.perf_counter()
        print(f'用时 {end - start}')
        return res


if __name__ == '__main__':
    @Timer  # @
    def sum_():
        return sum([i for i in range(1000000)])


    print(sum_())
