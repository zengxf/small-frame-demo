# 面向对象编程：python
''' 编程方法：面向过程，面向函数（调用函数），面向对象（类-函数）'''

# 类的基本概念：
''' 
1. 类：具体相同属性和方法（函数），抽象为一个类
2. 对象：根据类实例化对象，通过对象使用方法，属性
3. 属性：类的数据，类属性和实例属性
   类属性：定义在函数外面的属性
   实例属性：在构造函数 __init__ 中，定义的属性
4. 方法：类的操作（函数），实例方法（self）、类方法（cls）、静态方法 @
5. 类的三大特性：封装、继承、多态
'''

'''
# 类的组成
class 类名(object, 父类):           # 类名后面小括号可以有，可无，基类 object
    属性 = '值'        # 变量，类属性
    
    def __init__(self, 形参1, 形参2):       # 实例属性
        self.属性1 = 形参1          # self.属性1，形参赋值
        self.属性2 = 形参2
        
    def 实例方法(self, 形参):         # 函数的第一个参数是 self，可以有多个
        return 形参
    
    @classmethod            # 类方法的修饰符
    def 类方法(cls):
        return 
    
    @staticmethod           # 静态方法的修饰符
    def 静态方法():
        return '当做工具使用'


# 实例对象
对象1 = 类名()          # 函数调用，类实例

对象1使用：通过对象打点调用类的属性和方法【对象1.属性和方法】
'''

# __init__ 构造函数，初始化函数
''' __init__ 可以有，可以没有，构造实例属性。不能使用 return，可以使用 print '''


# 构造一个人类，属性 name,age
class Person:  # 类名的首字母大写，使用驼峰法 UserName
    national = '中国'  # 类属性

    def __init__(self, name, age):  # name 形参
        self.name = name  # self.name 实例属性
        self.age = age
        # print('实例对象，属性有name, age')

    def show(self, job='AI'):  # 实例方法
        print(f'实例方法可以通过【self.实例属性和类属性】{self.national}{self.name}'
              f'类属性也可以通过类名打点调用{Person.national}')
        return f'我叫{self.name}，今年多少岁{self.age}，{job}。'

    @classmethod
    def modify(cls):  # 类方法通常用来修改类属性
        cls.national = '美国'
        print(f'类方法不能通过 self 访问实例属性和方法')
        return cls.national

    @staticmethod
    def tool(age):  # 当作工具使用
        print(f'静态方法，不能使用类属性和方法，实例属性和方法，于类属性和方法无关！')
        return 2025 - age


# 创建对象，实例对象
p1 = Person('张三', 20)
p2 = Person('李四', 20)
print(dir(p1))

print('实例对象，可以打点访问属性和方法', p1.show(), p1.name)
p1.national = 'AA'
print(p1.national)
print(p2.national)
print("#" * 30)

p1.modify()  # p1 对象调用类方法：修改美国
print(p1.national)
print(p2.national)

print(p1.tool(p1.age))

''' 练习：定义银行类 bank，属性中国银行 bank_name，地址 bank_addr，电话 bank_phone
    1. 姓名 name，账号 account，密码 pwd，余额 balance 属性
    2. 取款 、存款、 余额查询方法
    3. 类方法可修改属性电话
    4. 静态方法计算金额是否为 100 的整数倍
    5. 实例 3 个对象，提示开户信息
    6. 实现统计开户的人数 count '''


# 实例对象：xiaoliu、xiaowang、xiaoli
# xiaoliu 存款 1000，取款 2000
# xiaowang 存款 800，修改开户的地址，查询余额
# xiaoli 修改密码，查询余额，查询开户人数

class Bank:
    bank_name = '中国银行'
    bank_addr = '深圳'
    bank_phone = '0755-12345678'
    count = 0

    def __init__(self, name, account, pwd, balance=10000):
        self.name = name
        self.account = account
        self.pwd = pwd
        self.balance = balance
        Bank.count += 1
        print(f'开户信息：\n银行：{self.bank_name}...')

    def withdraw(self, money):
        if 0 < money < self.balance:
            self.balance -= money
        return self.balance

    def deposit(self, money):
        if money > 0:
            self.balance += money
        return self.balance

    def get_balance(self):
        return self.balance

    @classmethod
    def mod_phone(cls, new_phone):
        cls.bank_phone = new_phone
        return cls.bank_phone

    @staticmethod
    def compute_money(money):
        if money > 0 and money % 100 == 0:
            return money


print('*--'*20)
li = Bank('xiaoli', 12345678, 123456)
liu = Bank('xiaoliu', 123456780, 123456, 2000)
wang = Bank('xiaowang', 123456789, 123456)
print(Bank.count)
# xiaoliu 存款 1000，取款 2000
liu.deposit(1000)
liu.withdraw(100)
print(liu.get_balance())

# xiaowang 存款 800，修改开户的地址，查询余额
wang.mod_phone('0755-1111111')
print(wang.get_balance())
print(liu.bank_phone)

# xiaoli 修改密码，查询余额，查询开户人数
li.pwd = 'abcde'
print(li.pwd, liu.pwd)
