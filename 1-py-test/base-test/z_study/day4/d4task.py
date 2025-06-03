# 基础练习题（共 20 题）
# 1. 判断奇偶数
# 输入一个整数，判断是奇数还是偶数。
num = 3
print('偶' if num % 2 == 0 else '奇')

# 2. 比较两个数的大小
# 输入两个整数，输出较大的一个。
n1 = 32
n2 = 43
print(max(n1, n2))

# 3. 判断正负数
# 输入一个数，判断它是正数、负数还是零。
num = 0
print('正' if num > 0 else '负' if num < 0 else '0')

# 4. 考试成绩评级
# 输入成绩（0~100），输出对应的等级（A/B/C/D/F）。
score = 56
level = ''
if score >= 90:
    level = 'A'
elif score >= 80:
    level = 'B'
elif score >= 70:
    level = 'C'
elif score >= 60:
    level = 'D'
else:
    level = 'F'
print(level)

# 5. 判断是否是闰年
# 输入年份，判断是否为闰年（能被4整除但不能被100整除，或能被400整除）。
y = 2025
print('闰' if y % 4 == 0 and y % 100 != 0 or y % 400 == 0 else '非')

# 6. 1~100 所有奇数打印出来
# 用 for 或 while 循环实现。
for i in range(1, 101):
    if i % 2 != 0:
        print(i, end=' ')
print()

# 7. 求 1~100 所有偶数的和
# 用循环加判断语句完成。
sum1 = 0
for i in range(1, 101):
    if i % 2 == 0:
        sum1 += i
print(sum1)

# 8. 计算阶乘
# 输入一个正整数 n，输出 n 的阶乘（n!）。
n = 4
s = 1
while n > 0:
    s *= n
    n = n - 1
print(s)

# 9. 输出九九乘法表
# 使用两个 for 循环打印乘法表。
for i in range(1, 10):
    for j in range(1, i + 1):
        print(f'{i} * {j} = {i * j} ', end=' ')
    print()

# 10. 输入多个数字，输出最大值
# 用户输入一串空格分隔的数字，输出其中最大值。
str = "23 34 54 56 13 4"
arr = str.split(' ')
print(max(arr))

# 11. 简单密码判断器
# 输入一个密码，如果长度小于8或者不包含数字，则提示不安全。
pwd = 'abc'
pwd = 'ab434343c4'
print('不安全' if len(pwd) < 8 or not any(c.isdigit() for c in pwd) else '安全')

# 12. 简单的猜数字游戏
# 系统设置一个 1~100 的随机数，用户不断输入，直到猜对为止，提示“大了”或“小了”。

# 13. 统计输入中正数和负数的个数
# 用户输入多个数，统计正数和负数各有多少个。
arr = [32, 34, -32, 54, -324]
dict = {'正': 0, '负': 0}
for i in arr:
    if (i > 0):
        dict['正'] += 1
    if (i < 0):
        dict['负'] += 1
print(dict)

# 14. 打印三角形图案
# 输入一个整数 n，打印一个高为 n 的星号三角形。
n = 5
for i in range(1, n + 1):
    print(' ' * (n - i), end='')
    print('*' * (i * 2 - 1))

# 15. 求列表中所有奇数的和
# 定义一个整数列表，求其中奇数的和。
arr = [32, 34, 321, 54, 241]
sum1 = 0
for i in arr:
    if i % 2 == 1:
        sum1 += i
print(sum1)

# 16. 找出 100 以内所有 3 的倍数
# 用循环打印所有能被 3 整除的数字。
for i in range(1, 101):
    if i % 3 == 0:
        print(i, end=' ')
print()

# 17. 输入五个学生成绩，求平均分
# 用循环读入 5 个成绩，输出平均值。
# list=[]
# for i in range(1, 6):
#     str=input(f'请输入第 {i} 个人的成绩: ')
#     list.append(int(str))
# print(sum(list)/5)

# 18. 找出最小公倍数
# 输入两个整数，输出它们的最小公倍数。

# 19. 判断一个数是否是质数
# 输入一个正整数，判断是否是质数（只能被 1 和自身整除）。
n = 17
b = False
for i in range(2, n // 2 + 1):
    if n % i == 0:
        b = True
        break
print(f'{n} 为非质数: {b}')

# 20. 计算数字的位数
# 输入一个正整数，输出它有多少位（例如 1234 有 4 位）。
n = 435423
n1 = n
w = 0
while True:
    if n == 0:
        break
    n = n // 10
    w += 1
print(f'{n1} {w}位')
