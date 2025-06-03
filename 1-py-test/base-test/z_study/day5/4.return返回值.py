# 函数中遇到 return 函数就结束

# 1. 返回值，可以是多个值。 返回元组对
def user_info():
    name = 'zhangsan'
    age = 20
    return name, age

# print(user_info())      # ('zhangsan', 20)

na = user_info()
print(na)

n, a = user_info()
print(n,a)

# 2. return 可以没有，函数没有返回值 None
def add(a,b):
    c = a+b

# print(add(2,3))

# 3. 函数中可以使用 print 输出
def add1(a,b):
    c = a+b
    print(c)            # 终端显示输出
    return c            # 函数返回值
    # return c
    # print(c)        # 无效

# add1(3,4)       # 有返回值，只是调用函数，没有输出返回值

print(add1(2,3))