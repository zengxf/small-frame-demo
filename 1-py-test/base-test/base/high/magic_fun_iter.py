"""
魔法函数

__iter__：把对象当作迭代器使用。 __next__：获取迭代器序列的下一个值
"""


class MagicMethod:
    def __init__(self, 形参实例属性):  # 构造函数：初始化实例属性
        self.实例属性 = 形参实例属性

    def __iter__(self):  # __iter__ 把对象当作迭代器，与 next 一起使用
        self.index = 0  # 跟踪当前迭代器的索引值位置
        return self

    def __next__(self):
        if self.index < len(self.实例属性):
            res = self.实例属性[self.index]
            self.index += 1
            print(f"index: {self.index}, res: {res}")
            return res
        else:
            raise StopIteration


# 创建实例对象 instance_object1，可以当迭代器使用
instance_object1 = MagicMethod(('x', 'y', 'z', 'x', 'y', 'z', 'a'))
print({i for i in instance_object1})  # 组装成 set
