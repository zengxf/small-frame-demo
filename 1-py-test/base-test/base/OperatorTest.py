"""
运算符测试
"""

# 算术运算符：加 +、减 -、乘 *、除 /、整除 //、取余 %、幂 **
print('\n------------------------')
print('算术运算符：加 +、减 -、乘 *、除 /、整除 //、取余 %、幂 **')
a = 10
b = 3
print(a + b)  # 13
print(a / b)  # 3.3333333333333335
print(a // b)  # 3
print(a ** b)  # 10^3 => 1000

# 逻辑运算符：与 and、或 or、非 not
print('\n------------------------')
print('逻辑运算符：与 and、或 or、非 not')
print('a b: ', a, b)
print(a > 10 and b < 4)
print(a > 10 or b > 4)
print(not b > 4)
