"""
函数作用域

global 全局变量  测试
"""

name = 'yoyo'  # 全局变量
print(f'name 是全局变量，内存地址是:{id(name)}')


def fun():
    name = 'xiaowang'  # 局部变量，与全局变量名相同，age=20 属于新变量
    print(f'name 是局部变量，内存地址是:{id(name)}')

    # global 关键字申明 new_name 是全局变量
    global new_name  # 全局变量
    new_name = '老王'

    print(f'new_name 是全局变量，内存地址是:{id(new_name)}')
    print('如果 fun 函数未调用，new_age 变量不能使用')


# 调用 fun 函数，new_age才可以使用
# print(new_name)
fun()
print(new_name)
