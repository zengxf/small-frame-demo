# 递归函数：定义函数时，在函数体内调用函数本身
"""
def fun(n):
    '1.递归终止的条件'
    return '调用函数本身fun()'
"""
# 1. 递归函数必须要有终止的条件：当 n 等于某个值，终止 return
# 2. 递归的规则 fun(n) --> fun(n-1或n+1)

# 案例：计算阶乘 5! = 5*4*3*2*1
n = 5
res = 1
for i in range(1, n + 1):
    res *= i


# print(res)

# 函数
def fun(n):
    # n = 5
    res = 1
    for i in range(1, n + 1):
        res *= i
    return res


# print(fun(5))

# 递归：5! = 5*4! = 5*4*3! = 5*4*3*2! = 5*4*3*2*1
def fun1(n):
    if n == 1:  # 1!=1
        return 1  # 函数终止
    return n * fun1(n - 1)  # 2=2*1!    堆栈


print(fun1(5))


# 求第 n 个斐波那契数列数：1  1  2  3  5  8 ...
def fib(n):
    # if n == 1:
    #     return 1
    # if n == 2:
    #     return 1
    # return fib(n - 1) + fib(n - 2)        # 3 = fib(2) + fib(1)
    return 1 if n <= 2 else fib(n - 1) + fib(n - 2)

print(fib(20))


# 输入一个数，返回二进制：10 --> 0b1010，递归写
n = 10
res = '1'
while n//2:     # 0 == False
    res += str(n%2)
    n = n // 2      # 改变进入循环的条件
print(res)

# 0001
#   +1
# 0010

def binary(n):
    if n == 1:
        return '0b1'
    return binary(n//2) + str(n%2)

print(binary(10))


# 汉诺塔，移动盘子
# n = 1，终止条件 直接移动目标
# n > 1 , 分三步：
# 1. 把第一个盘子从源位置 移动 临时位置
# 2. 把剩余的盘子，从源位置 移动到 目标位置（迭代）
#    源位置 --》 临时位置
# 3. 把第一个盘子（临时位置），移动到目标位置
def han(n, source, target, temp):
    if n == 1:
        # n = 1，直接移动
        print(f'将 {n} 从 {source} 移动到 {target}')
        return
    # n = 2，不能直接移动，n-1表示剩下的盘子
    han(n - 1, source, temp, target)
    print(f'将 {n} 从 {source} 移动到 {target}')

    han(n - 1, temp, target, source)

han(3,'源位置','目标位置', '临时位置')