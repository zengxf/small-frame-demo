"""
魔法函数

__getitem__：把实例对象当作列表和字典进行索引访问，如果是列表还可以使用切片。
__delitem__：把实例对象当作列表和字典进行索引删除，如果是列表还可以使用切片。
"""


class MagicMethod:
    def __init__(self, 形参实例属性):
        self.实例属性 = 形参实例属性

    def __getitem__(self, param):  # param，实例参数表示访问元素 或 切片
        print(f"\n> param = {param}")

        if isinstance(param, slice):  # 是切片对象
            return self.实例属性[param.start: param.stop: param.step]  # 返回新列表

        return self.实例属性[param]  # 是字符串，返回对应的值

    def __delitem__(self, param):
        print(f"\n> param = {param}")
        if isinstance(param, slice):
            del self.实例属性[param.start: param.stop: param.step]
        else:
            del self.实例属性[param]


instance_object = MagicMethod(['a', 'b', 'c', 'd', 'e'])
print(instance_object[3])
print(instance_object[1:4])
print(instance_object[1:5:2])
print(instance_object[::2])

# ****************************
# ****************************

instance_object = MagicMethod([1, 2, 3, 4, 5])

del instance_object[3]
print(instance_object.实例属性)

del instance_object[1:3]
print(instance_object.实例属性)
