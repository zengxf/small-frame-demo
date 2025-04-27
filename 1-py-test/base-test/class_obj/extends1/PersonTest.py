"""
Person test
"""

from Person import *

# 实例化对象：student
student = Person('yoyo', 30)
# 【实例对象】访问：类属性和实例属性
print(f'我叫{student.name},来自{student.national},今年{student.age}')
# 【实例对象】访问：实例方法、类方法、静态方法
student.show()
print('---------------------------------\n')

print('实例对象访问实例方法：', student.speak('英语'))
print('实例对象访问类方法：', student.mod_national('美国'))
print('实例对象访问静态方法：', student.get_birth(student.age))
print('---------------------------------\n')

# 【类名】只能访问：类属性、类方法、静态方法，不能访问实例方法
print('类名访问类属性：', Person.national)
print('类名访问类方法：', Person.mod_national('日本'))
print('类名访问静态方法：', Person.get_birth(student.age))
print('---------------------------------\n')

# 动态属性和方法
Person.city = '深圳'  # city不存在，增加新属性城市，值为深圳
Person.country = '中国广东省'  # country 存在，修改属性的值为中国广东省
print(Person.__dict__.keys())  # 查看类的属性名
