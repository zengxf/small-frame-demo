"""
控制结构测试
"""

print("\n-----------------------------")
print('条件语句')
x = 10
if x > 0:
    print("x 是正数")
elif x == 0:
    print("x 是零")
else:
    print("x 是负数")

print("\n-----------------------------")
print('循环语句 for 循环')
for i in range(5):
    print(i)

print("\n-----------------------------")
print('循环语句 while 循环')
count = 0
while count < 5:
    print(count)
    count += 1

print("\n-----------------------------")
print('函数')


# 定义函数
def greet(name):
    return f"Hello, {name}!"


# 调用函数
print(greet("Alice"))  # 输出: Hello, Alice!
