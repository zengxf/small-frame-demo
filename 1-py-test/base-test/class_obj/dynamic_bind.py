"""
动态绑定属性和方法
"""


class People:
    pass


# 绑定类属性
People.city = '深圳'  # city不存在，增加新属性城市，值为深圳
People.country = '中国广东省'  # country 存在，修改属性的值为中国广东省

# 绑定对象属性
doctor = People()
doctor.sex = '男'  # sex不存在，增加对象属性性别为男
doctor.age = 28  # age存在，修改对象属性年龄为28

print(doctor.__dict__)
print(People.__dict__)
print("-------------------------\n")

# 属性的删除：del 类名[对象名].属性
del doctor.sex  # 删除对象属性 sex
del People.city  # 删除类属性 city

print(doctor.__dict__)
print(People.__dict__)
print("-------------------------\n")


# 动态绑定到对象的格式：对象名.绑定方法名 = 方法名
def merit():  # 函数
    return '我精通 YOLO v7、v8'


student = People()
student.fun1 = merit  # 将函数 merit 作为参数绑定到对象方法 stu.fun1 中
print(student.fun1())  # 只有 student 对象可以使用


# 动态绑定类的格式：类名.绑定类名 = 方法名
def feature(self):  # 函数
    return f'{People.country}人都是黄皮肤'


People.feature = feature  # 将函数 feature 作为参数绑定到类方法

print(doctor.feature())  # 所有对象都可以使用，student、 doctor
print(student.feature())  # 所有对象都可以使用，student、 doctor
