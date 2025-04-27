# 装饰器
# 装饰器用于修改或增强函数的功能。

def decorator(func):
    def wrapper():
        print("装饰器开始")
        func()
        print("装饰器结束")

    return wrapper


@decorator
def say_hello():
    print("Hello!")


say_hello()
