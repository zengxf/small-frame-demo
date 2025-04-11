"""
字典解包
"""


def example_function(a, b, c):
    print('a:', a, 'b:', b, 'c:', c)


print("\n-----------------------------------")
kwargs = {'a': 1, 'b': 2, 'c': 3}
example_function(**kwargs)

print('kwargs:', kwargs)
# print('kwargs:', **kwargs)  # 运行出错 >>> TypeError: print() got an unexpected keyword argument 'a'
