# enumerate 内置函数：获取迭代对象的索引，生成索引序列，元组对（索引值, 元素）
# enumerate(迭代对象, 索引的初值)

lst = ['a', 'b', 'c']
for i in enumerate(lst):
    print(i)
# (0, 'a')
# (1, 'b')
# (2, 'c')

# f(x) = 1*x1 + 2*x2 + 3*x3 + ...
l = [10, 20, 30]
for i, e in enumerate(l, 1):  # (0, 'a')
    print(i, e)

label = ['x', 'y', 'z']  # => x:0,y:1,z:2
for k, v in enumerate(label):
    print({k: v})

# 19. 判断一个数是否是质数
# 输入一个正整数，判断是否是质数（只能被 1 和自身整除）

