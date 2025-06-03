# 输出 50 个 # 字符
print('#' * 50)

# enumerate 内置函数：获取迭代对象的索引，生成索引序列，元组对（索引值, 元素）
# enumerate(迭代对象, 索引的初值)
lst = ['a', 'b', 'c']
for i in enumerate(lst):
    print(i)

# 先 col 后 row，可理解为最外层是 row 内层是 col
print([[col + 3 * row for col in range(1, 4)] for row in range(4)])

''' 30. 从 100 ~ 200 开始之间找出前 20 个质数，并计算他们的和（被除数/除数） '''
print([num for num in range(100, 201) if all([num % i for i in range(2, num)])])  # 内循环生成列表

# 完美数
print([num for num in range(1, 10000) if num == sum([i for i in range(1, num) if num % i == 0])])


def add2(x, y, z=10):
    c = x + y
    return c * z


# 3）关键字参数（调用时的实参，根据形参名字赋值）
print(add2(y=5, x=8))

''' 
2. 函数可变参数：位置可变 *args、关键字可变 **kwargs 
可变的关键字参数，必须在最后
'''


#        位置   可变位置   默认值  可变关键字
def parma(x, y, *args, k=1, **kwargs):
    return (x + y) * sum(args) / k - sum(kwargs.values())


print(parma(10, 20, 12, 23, c=30, b=2, a=1))
print(parma(10, 20, k=2))
print(parma(10, 20))


# 1. 返回值，可以是多个值。 返回元组对
def user_info():
    name = 'zhangsan'
    age = 20
    return name, age


na = user_info()
print(na)
n, a = user_info()
print(n, a)
