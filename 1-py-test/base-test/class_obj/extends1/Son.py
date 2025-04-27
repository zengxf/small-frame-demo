"""
继承 Father 测试
"""

from Father import *


class Son(Father):  # 定义一个儿子类，继承 Person 类
    def __init__(self, name, age, job):  # 定义 Song 类的构造函数
        super().__init__(name, age, job)  # 调用 Father 类的 __init__，初始化 name，age，job

    def show(self):  # 重写 Father 类的 show 实例方法
        son_show = super().show()  # 使用 super 函数调用父类
        return son_show


# 实例化对象 f1
son = Son('小王', 20, '学生')  # # 实例化对象 son

print(son.show())  # 我是小王，今年20岁，来自中国的一名学生，特征浓眉大眼
print(son.speak('日语'))  # 我会说日语
