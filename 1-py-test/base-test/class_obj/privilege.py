"""
权限控制

Python 中下划线【 _ 】在不同位置，表示的含义，也就是对类的属性和方法添加下划线【 _ 】 来进行访问的权限控制，常用 5 种含义如下：
 ① 单下划线开头：表示受保护的（protected）的属性和方法，通常只允许类本身或子类进行访问
 ② 单下划线结尾：主要用来避免与 Python关键字冲突
 ③ 双下划线开头：表示私有的（private）的属性和方法，这些属性和方法只允许类本身进行访问
 ④ 首尾双下划线：特殊方法也称为魔法方法。常用的魔法方法有：__init__、__dict__等
 ⑤ 单下划线：用作临时或无意义的变量，当作占位符使用。也可以忽略特定的值。
 如：print(list(_ for _ in range(5)))  # _表示临时变量，返回： [0, 1, 2, 3, 4]
 如：print(list(i for i,_ in enumerate('abc')))  # _表示忽略元素，返回索引值： [0, 1, 2]
"""


class Person(object):
    def __init__(self, name, sex, age):
        self.name = name
        self._sex = sex  # 受保护，定义类可以使用
        self.__age = age  # 私有的，定义的类

    def _fun1(self):
        print('_fun1属于受保护的方法：子类和本身')

    def __fun2(self):
        print('__fun2属于私有的方法：本身')

    def show(self):
        print(self.name, self._sex, self.__age)
        self._fun1()
        self.__fun2()  # 类内部可以访问受保护属性和方法类


# 创建对象
p1 = Person('张三', '男', 20)
print(p1._sex)  # 访问受保护的属性
p1._fun1()  # 访问受保护的方法

# print(p1.__age)  # 不能访问私有的属性
# p1.__fun2()  # 不能访问私有的方法

print(dir(p1))  # 查看对象的属性和方法

# _类名__属性 和 _类名__方法
print(p1._Person__age)  # 访问私有属性
p1._Person__fun2()  # 访问私有方法
