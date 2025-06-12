# 动态修改：不给变原类的数据下，给对象和类绑定新的属性和方法
import types
import json


class Person:       # 类名的首字母大写，使用驼峰法 UserName
    national = '中国'     # 类属性
    name = 'lisi'

    def __init__(self, name, age):      # name 形参
        self.name = name                # self.name 实例属性
        self.age = age
        # print('实例对象，属性有name, age')

    def show(self, job='AI'):         # 实例方法
        return f'我叫{self.name}，今年多少岁{self.age}，{job}。{Person.name}'

''' 属性：类属性，实例属性 '''
# 【类.属性】 = 值 或【对象.实例属性】 = 值
''' 1. 对象.实例属性 = 值 ，给对象绑定新的属性 '''
# 实例化对象 p1  p2
p1 = Person('张三', 20)
p1.job = 'AI'           # 不存在，绑定新的属性
p1.name = '张小三'     # 存在，修改
print(p1.national)
print(p1.name)
print(p1)
print(json.dumps(p1.__dict__, indent=4))
p2 = Person('李四', 18)

''' 2. 类.类属性 = 值 ，给类绑定新的属性 '''
Person.national = '美国'
Person.addr = '深圳'      # 不存在，绑定新的类属性
print(p1.national)
print(p2.addr)
print(dir(p2))


''' 方法（函数）：类方法，实例方法 '''
# 对象.新方法 = 函数名      不要有括号

def speak(l):
    return (f'我会说中文！{l}')

p1.speak = speak    # 不能加括号，绑定方法
print(p1.speak(123))

# p1.speak = speak('abc')    # 加括号，函数调用，返回值。绑定 speak 属性
# print(p1.speak)

p2.speak = speak
p2.speak(123)


# 类.方法 = 函数
def mod_national():
    Person.national = '德国'
    return Person.national

Person.mod_national = mod_national

# print(dir(Person))
#
# print(dir(p1))
print(p1.national)
print(Person.mod_national())        #
# print(p1.national)
p3 = Person('王五', 20)
print(p3.national)


def show(l):
    return (f'我会说中文！{l}')

p1.show1 = show
print(p1.show('1234'))
print(dir(p1))


# 函数、类，优先访问内部有没有参数，如果有先访问内部，没有就去外部找。
