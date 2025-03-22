"""
类和对象
"""


# 定义类
class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def greet(self):
        return f"Hello, my name is {self.name} and I am {self.age} years old."


# 创建对象
print('\n------------------------')
print('创建对象')
alice = Person("Alice", 30)
print(alice.greet())  # 输出: Hello, my name is Alice and I am 30 years old.
