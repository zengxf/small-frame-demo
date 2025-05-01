"""
魔法函数

__call__：使对象兼容函数的功能，可以把对象当作函数进行使用。
  还可以把 __call__ 方法当作装饰器使用。
"""


class CalcCount:
    def __init__(self, func):
        self.func = func
        self.count = 0

    # 可以把 __call__ 方法当作装饰器使用。
    def __call__(self, *args, **kwargs):
        self.count += 1
        print(f'递归函数 {self.func.__name__} 调用 {self.count} 次数, args: {args}')
        return self.func(*args, **kwargs)


@CalcCount
def clac_binary(n):
    return '1' if n == 1 else clac_binary(n // 2) + str(n % 2)


print(clac_binary(20))


#


# ---------------------------------
class MagicMethod1:
    def __init__(self, 形参实例属性):
        self.实例属性 = 形参实例属性

    def __call__(self, *args, **kwargs):
        return [self.实例属性 * _ for _ in args]
        # return [self.实例属性 * _ for _ in kwargs.values()]


# 创建实例对象 instance_object1，可以当函数使用
instance_object1 = MagicMethod1(10)  # 实例属性为 10
print(instance_object1(1, 2, 3))
