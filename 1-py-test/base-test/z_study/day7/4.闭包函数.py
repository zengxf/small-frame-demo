# 闭包函数：函数嵌套函数，形成闭包，外部函数的返回值是内部函数，数据保护
'''
global 将局部变量申明为全局变量
nonlocal 应用在闭包中，声明外部函数的变量，可以修改外部函数的变量
'''

# 实现闭包函数 3 个条件：
# 1. 外部函数嵌套内部函数
# 2. 外部函数返回值内部函数
# 3. 内部函数访问外部函数变量，如果修改外部函数变量 nonlocal

def 外部函数():
    name = 'yoyo'

    def 内部函数():
        nonlocal name         # 声明后，可以修改外部变量
        name = '张三'         # 内部函数中定义变量 name
        print('内部函数可以直接访问外部函数的变量')
        return name

    return 内部函数         # 注意函数名

# 创建闭包函数
闭包函数 = 外部函数()
print(闭包函数())           # 外部函数() = 内部函数

'''
name = 'zhangsan'

def fun():
    global name     # 全局变量，可以修改 name
    name = '李四'
    return name
    
print(name)
print(fun())        # 调用函数，global name 生效
print(name)
'''


# 定义函数，实现存取款功能（使用闭包实现）
def bank():
    balance = 1000

    def withdraw(money):
        nonlocal balance
        balance -= money
        return balance

    def deposit(money):
        nonlocal balance
        balance += money
        return balance

    return withdraw, deposit
withdraw, deposit = bank()
print(withdraw(100))
print(deposit(2000))