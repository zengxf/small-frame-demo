# for 的格式
'''
[::]
else:
if 条件:
for 条件:
while 条件:
def 函数():
class 类:
'''
''' 
for 循环变量 in '可迭代对象':
    '循环的代码块'  # 缩进
# else:     # 通常不写
#     '循环结束后,执行的代码'
print('执行的代码')
'''

# 可迭代对象: 字符串  元组  列表  字典(键)  集合  range
lst = []
for i in 'hello':
    lst.append(i)

print(lst)
# print(list('hello'))

# 列表
for i in ['a','b','c']:
    print(i, end='\t')

print()

# 字典, 默认取键  for 循环变量可以使用多个
dct = {'dog':'30','cat':'40','pig':'20'}
new_dct = {}
for k, v in dct.items():
    new_dct[v] = k

print(new_dct)

# d1 = {}
# d1['a'] = 1
# print(d1)


# 内置函数: range(起始值, 结束值, 步长)  生成可迭代的整数序列
# 起始值可以不写, 默认0开始
# 范围是左闭右开, 结束值-1
# 步长默认 1, 也可以负数

l = list(range(10, 1, -2))
print(type(l), l)

# 计算 1+2+3+...+10=?
total = 0
for i in range(1, 11):
    total += i    # total = total + i
    # print(total)
print(total)


# 计算数量和:
dct = {'dog':'30','cat':'40','pig':'20'}
nums = 0
for _, v in dct.items():
    nums += int(v)

print(nums)


for m in 'abc':     # 外控制行
    print(m, end='\t')
    for h in '123':     # 每一行里面列
        print(h, end='\t')
    print()

# a 1 2 3
# b 1 2 3
# c 1 2 3


# 1. 将 1，2，3，4 四个数字，组成不相同且无重复数字的三位数

# 2. 计算出 1 ~ 100 之间所有能被 3 整除的整数的和

# 3. 只提取 '1.Good gooD Study，2.day day UP!' 中的字母，返回到列表中（忽略大小写）

# 4. 统计数组 [[2,5,3,6,3], [1,3,5,1,8], [2,5,9,2,8]] 相同元素出现次数，以字典形式存储

# 5. 输入每个字母对应的ASCII码值，保存到一个元组列表中。如 [(a, 97), (b, 98), …]

# 6. 找出3位的水仙花数: 153 = 1**3 + 5**3 + 3**3 = 1 + 125 + 27 = 153

# 7. 九九乘法表



