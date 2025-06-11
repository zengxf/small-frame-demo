# 函数主要用来封装代码（功能单一），调用函数，格式：函数名()
name = 'yoyo'
print(name)

''' 函数的格式 
    函数名命令：标识符，字母+数字+下划线，不能以数字开头
    通常命令方法：使用下划线  user_name   定义类命名：UesrName
'''


def 函数名(形参):
    '函数体：实现的功能'
    return '函数返回内容'


函数名('实参')

''' 1. 函数的参数：位置参数、默认值参数、关键字参数(实参)
    形参就是形式参数，实参就是调用时传的实际参数 '''


# 1）对应函数形参的位置传实参
def add(a, b):
    return a + b


print(add(1, 2))


# 2）定义函数是给出形参的默认值，默认值参数必须在位置参数后面
def add1(x, y, z=10):
    c = x + y
    return c * z


print(add1(2, 3))
print(add1(2, 3, 20))


def add2(x, y, z=10):
    c = add(x, y)
    return c * z


print(add2(2, 3, 20))

# 3）关键字参数（调用时的实参，根据形参名字赋值）
print(add2(y=5, x=8))

''' 2. 函数可变参数：位置可变 *args、关键字可变 **kwargs 
    可变的关键字参数，必须在最后
    '''


# 案例：用户输入数字，计算和
def sum_(*args):
    return sum(args)


print(sum_(1, 2, 3, 4, 5))
print(sum_(1, 2, 3))


def dict_(**kwargs):
    return kwargs

print('#'*20,1)
print(dict_(x=1, y=2, z=3))


# 计算value的和

def dict_(**kwargs):
    # for k,v in kwargs.items():      # k,v = (键,值)
    #     print(f'{k}键的值{v}')
    k = list(kwargs.keys())
    v = tuple(kwargs.values())
    return f'键：{k}  值：{v}  和：{sum(kwargs.values())}'


print(dict_(x=11, y=21, z=31))

#        位置   可变位置   默认值  可变关键字
def parma(x, y, *args, k=1, **kwargs):
    return (x + y) * sum(args) / k - sum(kwargs.values())

print(parma(10,20, 12, 23, c=30, b=2, a=1))
print(parma(10,20, k=2))
print(parma(10,20))


# 2. 编写函数，找出数字 1、6、15、28、45、66、... 的规律，输出第 10 个数
# 1=1*1、6=2*3、15=3*5、28=4*7、45=5*9、66=6*11   n * 2n -1

def num(n):
    return n * (2*n-1)

print(num(10))


# 练习：写斐波那契数列，输入前20个，返回列表 1，1，2，3，5，8，13
# lst = [1, 1]
# for i in range(18):
#     lst.append(lst[i] + lst[i+1])
# print(len(lst),lst)

# def fib(n):
#     lst = [1, 1]
#     for i in range(n - 2):
#         lst.append(lst[i] + lst[i+1])
#     print(len(lst),lst)
#
# fib(2)

def fib(n):
    return [1 if n==1 or n==2 else i for i in range(n)]

print(fib(20))