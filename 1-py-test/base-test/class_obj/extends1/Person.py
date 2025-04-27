"""
类测试
"""


class Person:
    national = '中国'  # 类属性：national

    def __init__(self, name, age):  # 形参：name ,age
        self.name = name  # 实例属性（变量）: name
        self.age = age  # 实例属性（变量）: age

    def show(self):  # 实例方法1，默认参数名 self
        print(f'我叫{self.name}，今年{self.age}岁')

    # print(f'访问类属性：{self.national},{Person.national}')
    def speak(self, language):  # 实例方法2，带参数 language
        return f'我会讲{language}'

    # 类方法主要用来访问和修改类属性
    @classmethod  # 类方法的修饰符
    def mod_national(cls, new_notional):  # 类方法，第一个参数 cls
        cls.national = new_notional
        return cls.national

    # 静态方法主要用来进行运算，相当于一个工具函数
    @staticmethod  # 静态方法的修饰符
    def get_birth(age):  # 静态方法：不能访问实例属性和实例方法
        # print(f'静态方法与类和实例无关，但是可以访问：{Person.national} 和类方法')
        return 2024 - age

