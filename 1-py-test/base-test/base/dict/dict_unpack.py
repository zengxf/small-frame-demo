"""
字典解包
"""


def example_function(a, b, c):
    print('a:', a, 'b:', b, 'c:', c)


print("\n****************************")
kwargs = {'a': 1, 'b': 2, 'c': 3}
example_function(**kwargs)

print('kwargs:', kwargs)
# print('kwargs:', **kwargs)  # 运行出错 >>> TypeError: print() got an unexpected keyword argument 'a'


# 合并测试
defaults = {"a": 0.25, "b": 0.45, "save": False}
final_args1 = {**defaults, **kwargs}  # 后面会覆盖前面的
final_args2 = {**kwargs, **defaults}  # 后面会覆盖前面的
print('final_args1:', final_args1)
print('final_args2:', final_args2)
