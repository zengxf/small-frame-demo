# 对象好处：继承，继承父类的属性和方法，子类直接使用
# 单继承一个父类可以有多个子类 和 多继承一个子类可以继承多个父类
'''
class 父类1:
    def __init__(self, name):
        self.name = name

class 子类(父类1, 父类2):
    pass
'''
'''
class Person:

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def show(self):
        return f'我叫{self.name}，今年多少岁{self.age}'


class Student(Person):

    def __init__(self, name, age, job):
        Person.__init__(self, name, age)            # 父类.方法
        self.job = job

    def show1(self):
        return Person.show(self)
        # super().show()

s1 = Student('小红', 20, '学生')
print(s1.show())
print(s1.show1())

class Teacher(Person):

    def __init__(self, name, age):
        super().__init__(name, age)                 # super().方法()

    def show(self):
        return f'我叫{self.name}，今年多少岁{self.age}，我是一名老师'

t1 = Teacher('老王', 40)
print(t1.show())


# 继承：
# 1. 类名.方法(self, )      必须有 self
# 2. super().方法()
# 3. super(类名, 实例方法).方法()
'''
''' 练习：单继承定义人类，父亲类，母亲类 '''


class Person:

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def show(self):
        return f'我叫{self.name}，今年多少岁{self.age}'


# 没有 __init__ 属性，只有方法的类，minix ，继承必须写在第一个父类
class Minix:
    def compute_birth(self, age):
        return 2025 - age


class Father(Person):

    def __init__(self, name, age, job):
        super().__init__(name, age)
        self.job = job

    def show(self):
        father_show = Person.show(self)
        return father_show


class Mather(Minix, Person):

    def __init__(self, name, age, sex='女'):
        Person.__init__(self, name, age)
        self.sex = sex

    def show(self):
        mather_show = super().show()
        return mather_show


f = Father('老王', 22, '医生')
m = Mather('小李', 22, '女')
print(m.show())
print(f.show())


class Son(Father, Mather):

    def __init__(self, name, age, job, sex):
        # Father.__init__(self, name, age, job)
        # Mather.__init__(self, name, age, sex)
        super().__init__(name, age, job)
        self.sex = sex

    def show(self):
        father_show = super(Father, self).show()
        father_show = super(Father, f).show()
        return f'我的父亲：{father_show}'

s = Son('小王', 3, '无', '男')
print(Son.mro())
# print(s.compute_birth(s.age))

print(s.show())


############ 多态（方法名字相同）：不同的对象调用返回结果不同
# 第一种：通过继承实现多态
# 第二种：通过方法重写
