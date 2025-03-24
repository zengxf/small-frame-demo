# module2.py


from .module1 import function_a  # 相对导入：在包内部，可以使用相对导入来导入同一包中的其他模块。


def function_b():
    print("Function B from module2")


def function_c():
    print("------------------------")
    function_a()
    print("Function C from module2")
    print("------------------------")
