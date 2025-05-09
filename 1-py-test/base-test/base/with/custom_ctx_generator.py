"""
自定义上下文管理器

使用生成器实现上下文管理器
"""

from contextlib import contextmanager


# 使用 @contextmanager 装饰器可以将一个生成器函数转换为上下文管理器。
@contextmanager
def my_context():
    print("1. Entering the context")
    try:
        print("1. 1. Try ...")
        yield  # yield 语句之前的代码在进入 with 代码块时执行，yield 之后的代码在退出 with 代码块时执行。
        print("1. 2. Try ...")  # 没异常时，进入此
    except Exception as e:
        print(f"3. An exception occurred: {e}")
        # raise  # 重新抛出异常
    finally:
        print("4. Exiting the context")  # 无论是否发生异常，finally 块中的代码都会被执行。


# --
print("\n****************************")

# 使用生成器上下文管理器
with my_context():
    print("2. Inside the with block")
    raise ValueError("An error occurred")
