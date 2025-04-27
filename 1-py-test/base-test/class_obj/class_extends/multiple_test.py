"""
多继承
"""


class Mother:  # 定义一个母亲类
    def show(self):
        return f'--------- Mother'


class Person:  # 定义一个类
    def show(self):
        return f'--------- Person'


class Daughter(Mother, Person):  # 定义一个女儿类，继承 Father、Mother 一致 super
    pass


daughter = Daughter()
print(daughter.show())  # 输出 Mother.show
print(Daughter.mro())  # MRO 解析顺序可以通过 类名.mro() 查询
