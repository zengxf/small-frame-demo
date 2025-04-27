"""
单继承
"""

from Person import *


class Father(Person):  # 定义一个父亲类，继承 Person 类，
    def __init__(self, name, age, job):  # 定义 Father 类的构造函数，增加 job 形参
        Person.__init__(self, name, age)  # 调用 Person 类的 __init__，初始化 name，age
        self.job = job  # 形参 job 赋值给实例属性 self.job
        self.feature = '浓眉大眼'  # 实例属性直接赋值

    def show(self):  # 重写 Person 类的 show 实例方法
        father_show = Person.show(self)  # 获取 Person 类中 show方法的返回值
        return f'{father_show}的一名{self.job}特征{self.feature}'  # 重新返回 Father 类的 show 方法


father = Father('老王', 58, '警察')  # 实例化对象 father
print(father.show())  # 我是老王，今年58岁，来自中国的一名警察，特征浓眉大眼

# Father 继承了 Person 类的 speak 方法
print(father.speak('英语'))  # 我会说英语
