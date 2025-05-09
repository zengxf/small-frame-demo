"""
关键字参数

接收可变参数列表
"""


def dict_fun_test1(a, b, **kw_args):
    print('\n**************************** 1')
    print(f"a: {a}")
    print(f"b: {b}")
    print(f"kw-args: {kw_args}")


# def dict_fun_test2(a, b, **kwargs, d):  # 编译出错
def dict_fun_test2(a, b, **kwargs):
    print('\n**************************** 2')
    print(f"a: {a}")
    print(f"b: {b}")
    print(f"kw-args: {kwargs}")


dict_fun_test1(1, 2)
dict_fun_test1(2, 3, c=3, d=4, e=5)
dict_fun_test2(3, 4, str='abc', int=334, bol=True)
