"""
魔法函数

__setitem__：把实例对象当作列表和字典进行索引修改，如果是列表还可以使用切片。
__delitem__：把实例对象当作列表和字典进行索引删除，如果是列表还可以使用切片。
"""


class MagicMethod:
    def __init__(self, 形参实例属性):
        self.实例属性 = 形参实例属性

    def __setitem__(self, param, value):
        if isinstance(param, dict):  # param 是字典，只处理字典键的赋值
            self.实例属性[param] = value
        else:  # 如果 param 不是字典，则可以处理切片
            if isinstance(param, slice):
                self.实例属性[param.start: param.stop: param.step] = value
            else:
                self.实例属性[param] = value



instance_object = MagicMethod({'name': 'yoyo', 'age': 30, 'sal': 3000})
instance_object['age'] = 31  # 通过键修改字典的值

print(instance_object.实例属性)  # 输出: {'name': 'yoyo', 'age': 31, 'sal': 3000}
