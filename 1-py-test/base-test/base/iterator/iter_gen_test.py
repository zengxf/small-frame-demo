# 迭代器和生成器

# 迭代器
# 实现了 __iter__() 和 __next__() 方法的对象。

my_list = [1, 2, 3]
iterator = iter(my_list)

print(next(iterator))  # 输出: 1
print(next(iterator))  # 输出: 2
print(next(iterator))  # 输出: 3
print("****************************")


#
# 生成器
# 使用 yield 关键字的函数，用于创建迭代器。

def my_generator():
    yield 1
    yield 2
    yield 3


gen = my_generator()
print(next(gen))  # 输出: 1
print(next(gen))  # 输出: 2
print(next(gen))  # 输出: 3
