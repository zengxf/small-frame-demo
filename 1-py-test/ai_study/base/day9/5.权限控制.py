# 权限控制：类，对象的访问权限

''' python 通过下划线 _ 来控制访问 '''
# 1. 单个下划线 _ : 临时、无意义
print([_ for _ in range(10)])
# a, _ = 函数(返回值a, b)

_='a'
print(_)


# 2. 变量名后面 _: 区别关键字
sum_ = 0


# 3. 类的属性和方法前面：1）使用单个下划线 _ 受保护的，2）使用双下划线 __ 私有的
# 首尾双下划线 __init__ 类内置的属性和方法（魔法函数 len、getitem、iter、call-类装饰器）

class Person:

    def __init__(self, name, age, sal):
        self.name = name        # 没有下划线，普通的
        self._age = age         # _属性，受保护的
        self.__sal = sal        # __属性，私有的

    def show(self):
        print(f'实例方法中都可以通过 self. 属性')
        return f'我叫{self.name}，今年多少岁{self._age}，薪资{self.__sal}'

    def _speak(self):
        return f'说中文{self.__sal}'

    def __speak(self):
        return f'说中文{self.__sal},{self._speak()}'



p1 = Person('yoyo', 20, 1000)
print(p1.name, p1.show())

print(p1._age, p1._speak())

# print(p1.__sal, p1.__speak())
print(p1._Person__sal)      # 强制访问： _类名__属性方法
print(p1._Person__speak())
# print(p1.__speak())
print(dir(p1))