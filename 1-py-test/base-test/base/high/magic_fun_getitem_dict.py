"""
魔法函数

__getitem__：把实例对象当作列表和字典进行索引访问，如果是列表还可以使用切片。
"""


class MagicMethod:
    def __init__(self, 形参实例属性):
        self.实例属性 = 形参实例属性

    def __getitem__(self, param):
        print(f"\n> param = {param}")

        if isinstance(param, slice):
            keys = list(self.实例属性.keys())  # 获取字典键的列表
            sliced_keys = keys[param.start:param.stop:param.step]  # 根据切片获取相应的键
            return {key: self.实例属性[key] for key in sliced_keys}  # 返回新字典

        return self.实例属性[param]  # 如果 param 是字符串，返回对应的值


instance_object = MagicMethod({'name': 'yoyo', 'age': 30, 'sal': 3000})

print(instance_object['name'])
print(instance_object[::])
print(instance_object[1:3])
print(instance_object[::2])
