'''
1. iter 创建迭代器
 格式：迭代器对象 = iter(可迭代对象)

可迭代对象 iterable：是该对象含义内置的__iter__和__getitem__两个方法的对象
    其中 getitem 方法从索引 0 开始。
迭代器对象 iterator：是该对象中含有内置的 __iter__和__next__两个核心方法
    其中使用 next 函数来获取下一个元素，直到出现 StopIteration 异常。
'''
import traceback

# 使用字符串创建迭代器
iter_str = iter('python')  # 6个字符
print(iter_str)  # str_iterator object

# 使用列表创建迭代器
iter_list = iter([1, 2, 3, 4, 5])  # 5个元素
print(iter_list)  # list_iterator object

# 使用字典创建迭代器
iter_dict = iter({'name': 'yoyo', 'age': 30})  # 2个键，默认获取键
print(iter_dict)  # dict_keyiterator object

print(dir(list))  # __getitem__ 和 __iter__
print(dir(iter_list))  # __iter__ 和 __next__
print("--------------------------\n")

'''
2. next(迭代器对象) 获取迭代器下一个元素，如果没有元素抛出异常 
'''

# next 第一次获取元素
print(next(iter_dict))  # name
# next 第二次获取元素，记住第一次的位置
print(next(iter_dict))  # age

# 注意：iter_dict 只有 2 个键，使用完成后，没有 iter_dict 迭代器，继续执行 next，报错
# print(next(iter_dict)) # 没有元素时，StopIteration

print([_ for _ in iter_str])  # ['p', 'y', 't', 'h', 'o', 'n']

while True:
    try:
        print(next(iter_list))
    except:
        # sys.exit()  # 优雅的结束
        print('出错')
        break
