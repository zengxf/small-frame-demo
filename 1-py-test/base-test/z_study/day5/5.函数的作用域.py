'''
全局变量： 定义在函数外面的变量，作用于整个脚本文件
局部变量： 定义在函数体内的变量，只在函数内有效
关键字变量： global 将局部变量申明为全局变量，nonlocal 可修改外部函数的局部变量，闭包
'''

name = 'yoyo'
def info():
    global name     # 将 name 申明为全局变量，但是必须调用该函数，才生效
    name = '张三'
    age = 20
    return name

def info1():
    return name*2

print(info())
print(info1())
print(name)

