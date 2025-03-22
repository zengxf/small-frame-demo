"""
异常处理
"""

print("\n-----------------------------")
print("try-except")
try:
    result = 10 / 0
except ZeroDivisionError:
    print("除以零错误")

print("\n-----------------------------")
print("try-except-else-finally")
try:
    result = 10 / 2
except ZeroDivisionError:
    print("除以零错误")
else:
    print("结果是:", result)
finally:
    print("执行完毕")
