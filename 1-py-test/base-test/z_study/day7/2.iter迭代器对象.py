''' iter 迭代器对象：可以记住索引的位置，不能循环，只能往前走，惰性使用，用完即焚 '''

# iterable 可迭代对象（可循环取值）：字符串、元组、列表、字典、集合、文件对象等
# dir(对象)  获取对象的属性和方法
# print(dir(list))   # __iter__ 和 __getitem__ 从 0 开始取值

# iterator 迭代器对象（不可循环，只能往前取值，取完失效）：使用 iter 创建迭代器对象，next 取值
# __iter__ 和 __next__


''' 生成迭代器对象的'''
''' 格式1：iter('可迭代对象') '''
str_iter = iter('hello')
print(str_iter)

# print(next(str_iter))
'''
for i in str_iter:
    print(i)

print(next(str_iter))       # StopIteration
'''
try:
    for i in range(6):
        print(next(str_iter))
except StopIteration:
    print('迭代器异常')


# 将 lst = [1, [2], [3, 4], 5] 扁平化输出 [1, 2, 3, 4, 5]
lst = [1, [2], [3, 4], 5]
new_lst = []
for i in lst:
    if isinstance(i, list):
        for j in i:
            new_lst.append(j)
    else:
        new_lst.append(i)

# print(new_lst)

# 定义函数，使用迭代器
lst1 = [1, [2], [3, 4], 5]

def iter_arr(lst1):
    l = []
    for i in lst1:
        if isinstance(i, list):
            l.extend(iter_arr(iter(i)))
        else:
            l.append(i)
    return l

print(iter_arr(lst1))


''' 格式2：iter('可调用对象', '标记值')  作用：简化 while 循环
    取值：从可调用对象中往前取值，值等于标记值停止 '''
# iter_ = iter(lambda :int(input('请输入一个数字：')), 10)
#
# for i in iter_:
#     print(i)

# 猜数字游戏：生成随机数，输入一个数字猜
import random
rand_num = 2 #random.randint(1,100)        # 生成 1~100 之间的随机数
print(rand_num)
guess = iter(lambda :int(input('请输入一个数字：')), rand_num)
# print(next(guess))
for i in guess:
    print(i)

# for i in range(3):
#     print(next(guess))      # 使用 next 取值，注意 StopIteration 异常

# print(rand_num)
# while True:
#     input_num = int(input('输入猜的数字：'))
#     if input_num == rand_num:
#         print('猜对了')
#         break
#     elif input_num >= rand_num:
#         print('猜大了')
#         continue
#     else:
#         print('猜小了')
#         continue