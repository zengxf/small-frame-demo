# 1. 将 1，2，3，4 四个数字，组成不相同且无重复数字的三位数
# 思路：1，2，3，4 循环取，百 十 个
lst = []
for b in range(1, 5):
    # print('百',b)
    for s in range(1, 5):
        # print('十', s)
        for g in range(1, 5):
            if b != s and s != g and g !=b:
                lst.append(b * 100 + s * 10 + g)
                # print(b * 100 + s * 10 + g)
print(lst)

# 2. 计算出 1 ~ 100 之间所有能被 3 整除的整数的和
# 思路：1 ~ 100  循环取数字,
# lst1 = []
total = 0
for i in range(1, 101):
    # i 从 1 ~ 100，取 % 3
    if i % 3 ==0:
        total = total + i
print(total)


# 3. 只提取 '1.Good gooD Study，2.day day UP!' 中的字母，返回到列表中（忽略大小写）
s = '1.Good gooD Study，2.day day UP!'
set1 = set()
for c in s:
    if c.isalpha():
        # 保存 c
        set1.add(c.lower())
print(list(set1))


# 4. 统计数组 [[2,5,3,6,3], [1,3,5,1,8], [2,5,9,2,8]] 相同元素出现次数，以字典形式存储
arr = [[2,5,3,6,3],
       [1,3,5,1,8],
       [2,5,9,2,8]]
dct = {}
for row in arr:
    # print(row)
    for col in row:     # 从 行 row 取值
        # print(col)
        if col in dct:
            dct[col] += 1
        else:
            dct[col] = 1
print(dct)


# l = [2,3,3,4,5,6,5]
# d = {}
# for k in l:
#     # i 取 2
#     if k not in d:      # d 字典，默认读取键
#         d[k] = 1
#     else:
#         d[k] = d[k] + 1
# print(d)
#     # print(d[i])


# 5. 输入每个字母对应的ASCII码值，保存到一个元组列表中。如 [(a, 97), (b, 98), …]
# string 字符集合模块
import string
s = string.ascii_letters
lst = []
for i in s:
    lst.append((i, ord(i)))
print(lst)

# 6. 找出 3 位的水仙花数: 153 = 1**3 + 5**3 + 3**3 = 1 + 125 + 27 = 153
# 思路：取 100~999
for sxh in range(100,1000):
    if sxh == (sxh//100)**3 + (sxh%10)**3 + (sxh//10%10)**3:
        print(sxh)

# 7. 九九乘法表
# 1*1=1
# 1*2=2  2*2=4
# ...
# 1*9=9  2*9=18 ... 9*9=81

for row in range(1, 10):
    for col in range(1, row + 1):
        print(f'{col}*{row}={row * col}', end='\t')
    print()


# 用星号打印下面，n=4
# 思路：             行=n=4       星号      空格
#    *                1           1       3
#   ***               2           3       2
#  *****              3           5       1
# *******             4           7       0
n = 4
for i in range(1, n+1):
    # print(f"{'*'*(2*i-1)}".center(2*n))
    for j in range(n-i):        # 空格
        print(' ',end='')
    for k in range(2*i-1):        # 星号
        print('*',end='')
    print()