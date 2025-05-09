"""
自定义上下文管理器

使用类实现上下文管理器
"""


class MyContextManager:
    def __enter__(self):
        print("1. Entering the context")
        return self  # 可以返回任何对象

    def __exit__(self, exc_type, exc_value, traceback):
        print("3. Exiting the context")
        if exc_type:
            print(f"4. An exception occurred: {exc_value}")
        # 返回 False 会重新抛出异常，返回 True 则抑制异常
        # return False
        return True


# --
print("\n****************************")

# 使用自定义上下文管理器
with MyContextManager() as cm:
    print("2. Inside the with block")
    raise ValueError("An error occurred")
