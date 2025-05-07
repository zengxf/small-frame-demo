"""
海象运算符（Walrus Operator）

海象运算符（:=），也称为赋值表达式，是 Python 3.8 版本引入的一项新特性。它允许在表达式内部进行变量赋值，从而简化代码，
"""


def read_data():
    return 1


def process(v):
    print(v)


# 传统写法：
data = read_data()
if data:
    process(data)

# 使用海象运算符：
if (data := read_data()):
    process(data)
