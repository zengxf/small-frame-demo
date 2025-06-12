# while 与 for 的区别：for 自动停止，while 必须手动停止
''' wihle 格式 '''
"""
变量 = '初值'
while '条件表达式为真':   # 是否进入循环
    '循环体'
    '必须有条件，改变条件表达式为假'
else:
    '循环结束后执行的代码'
"""
# 1. 初始化变量
# 2. 是否进入循环的条件表达式
# 3. 循环体中，必须有能初始变量的值，是的条件表达式为假

# 1 + 2 + 3 + ... + 100 =?
total = 0
num = 1
while num<=4:
    total += num
    num += 1
# print(total)

# 循环退出的语句：break 语句，退出循环, continue 结束当前循环，继续下一次循环
# 完美数：6 = 1 + 2 + 3 = 6
num = 7
while True:
    lst = []
    for i in range(1,num):
        if num % i == 0:
            lst.append(i)
    if sum(lst)==num:
        print('完美数')
        break
    else:
        num += 1
print(num)



for k in range(10):
    if k == 3:
        continue        # 跳过当次 i = 5
    if k == 5:
        break           # 结束循环
    print(k)


# 输入字符串，用 while 循环逐个字符反转  abc ==> cba
new_str = ''
s = input('请输入字符串：')
n = len(s)
while n:
    new_str += s[n-1]
    n -= 1
print(new_str)

print(dict(a=1,b=2))
print(dict([('a',1),('b',2)]))

