"""
魔法函数

__setitem__：把实例对象当作列表和字典进行索引修改，如果是列表还可以使用切片。
__delitem__：把实例对象当作列表和字典进行索引删除，如果是列表还可以使用切片。
"""


class MagicMethod:
    def __init__(self, items):
        self.items = items

    def __setitem__(self, param, value):
        if isinstance(param, slice):
            self.items[param.start: param.stop: param.step] = value
        self.items[param] = value


instance_object = MagicMethod([1, 2, 3, 4, 5])

instance_object[2] = 123
print(instance_object.items)  # [1, 2, 123, 4, 5]

instance_object[1:5:2] = [123, 456]
print(instance_object.items)  # [1, 123, 123, 456, 5]

instance_object[1:3] = [456]
print(instance_object.items)  # [1, 456, 5]
