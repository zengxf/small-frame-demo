"""
__name__ 是所有模块的内置属性，主要用来控制程序的运行方式
 在 Python 文件中经常可以看到 if __name__ == '__main__': 这样的代码，有什么作用呢？
 其意思就是说：模块既可以被导入使用，也可以作为脚本文件独立执行
"""


def add(x, y):
    return x + y


def sub(x, y):
    return x - y


if __name__ == '__main__':  # 运行当前代码，执行下面的 add(2,3), sub(5,8)
    print(add(2, 3))
