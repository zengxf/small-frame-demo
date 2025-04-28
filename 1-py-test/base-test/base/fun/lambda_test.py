"""
lambda (匿名函数) 函数测试
"""

# 用法1 : 将 lambda 函数赋值给 变量，通过这个变量间接调用该 lambda 函数
# 案例：使用 lambda 计算 x 的 x 次方, 返回给变量
num_pow = lambda x: x ** x  # lambda 函数的参数是 x, 函数的主体是 x*x
# 调用函数 num_pow, 传入参数 5, 打印结果
print(num_pow(5))

# # 案例：表达式嵌套 if 语句，输入一个字符串，判断长度，如果小于 6 位，全部转为大写
# s = lambda: str1 if len(str1 := input('请输入一个字符串：')) > 6 else str1.upper()
# # lambda 没有参数, 直接调用
# print(s())

# 案例：表达式嵌套 for (只能使用推导式)，从输入参数中, 找出大于 20 的元素，返回列表
new_list = lambda *args: [i for i in args if i > 20]  # lambda 使用可变参数
print(new_list(23, 14, 17, 27, 43, 3, 7, 29))  # [23, 27, 43, 29]


# 用法2 : 将 lambda 函数作为其他 函数的返回值，返回给调用者
# 案例 : print 输出 - * 50
def fun_print(c):
    return lambda x: c * x


comment = fun_print('-')  # 调用函数 : fun_print 赋给变量 comment (函数)
print(comment(50))


# 案例 : 计算 f(x) = 2 * a * x + b 的值
def gradient(a, b):
    return lambda x: 2 * a * x + b


grad = gradient(2, 3)
print(grad(5))
