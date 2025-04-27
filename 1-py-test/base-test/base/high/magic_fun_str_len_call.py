"""
魔法函数

__str__：必须返回一个字符串，否则引发错误。可以用来做类的描述，也可以重写方法。
__len__：定义对象的长度，使对象兼容内置函数 len 的功能，通过 len 获取实例对象的长度。
__call__：使对象兼容函数的功能，可以把对象当作函数进行使用。
  还可以把 __call__ 方法当作装饰器使用。

__iter__：把对象当作迭代器使用。 __next__：获取迭代器序列的下一个值
"""


class MagicMethod:
    def __init__(self, 形参实例属性):  # 构造函数：初始化实例属性
        self.实例属性 = 形参实例属性

    def __str__(self):  # 定义 __str__ 方法，返回类的描述
        return f'{self.__class__.__name__} 类，主要介绍魔法函数 __str__ 的用法'

    def __len__(self):  # 定义 __len__ 方法，是对象兼容 len 内置函数
        return len(self.实例属性)

    def __call__(self, *args, **kwargs):
        return [self.实例属性 * _ for _ in args]
        # return [self.实例属性 * _ for _ in kwargs.values()]


instance_object = MagicMethod('实参实例属性')
print(instance_object)

#
# 创建实例对象 instance_object1，可以使用len获取实例对象的长度
instance_object1 = MagicMethod('实参实例属性')
print(len(instance_object1))  # 6

instance_object2 = MagicMethod([1, 2, 3, 4, 5])
print(len(instance_object2))  # 5

# 创建实例对象 instance_object1，可以当函数使用
instance_object1 = MagicMethod(10)  # 实例属性为 10
print(instance_object1(1, 2, 3))
# print(instance_object1(cat=1, pig=2, dog=3))
