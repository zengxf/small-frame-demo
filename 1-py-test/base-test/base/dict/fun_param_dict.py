"""
关键字参数

接收可变参数列表
"""


def dict_fun_test(a, b, **kw_args):
    print('\n--------------------')
    print(f"a: {a}")
    print(f"b: {b}")
    print(f"kw-args: {kw_args}")


dict_fun_test(1, 2)
dict_fun_test(2, 3, c=3, d=4, e=5)
dict_fun_test(3, 4, str='abc', int=334, bol=True)
