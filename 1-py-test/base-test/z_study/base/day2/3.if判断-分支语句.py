# 程序运行90%: 从上到下 --> 从左到右 --> 由内到外 --> 遇到函数跳过

# if 格式1: if . else
# if '条件表达式':
#     print('条件为真 True, 执行的操作')
# else:
#     print('条件为假 False, 执行的操作')


# 例子: 用户输入数字,如果等于 520 ,输出我爱你
# num = int(input('请输入数字:'))       # input 返回字符串
# if num == 520:
#     print('我爱你')
# else:
#     print('你是个好人')
# pass


# if 格式 2: if . elif . else
# if '条件1表达式':
#     print('条件1为真 True, 执行的操作1')
# elif '条件1为假-条件2表达式':
#     print('条件1为假, 条件2为真 , 执行的操作2')
# # elif ..
# #     ...
# else:
#     print('条件1为假, 条件2为假 , 执行的操作3')


# 例子: 用户输入数字, 判断是否为数字. 如果等于 520 ,输出我爱你
# num = input('请输入数字:')       # input 返回字符串
# if not num.isdigit():
#     print('输入错误,请输入数字.')
# elif int(num) == 520:
#     print('我爱你')
# else:
#     print('你是个好人')
#
# pass

# 缩进的快捷键: Tab键-4个空格, 取消缩进按 shift+tab
# if 格式 2: if . elif . else
# if '条件1表达式':
#     print('条件1为真 True, 执行的操作1')
#     if '条件1为真-条件2表达式':
#         print('条件1为真, 条件2为真 , 执行的操作2')
#     else:
#         print('条件1为真, 条件2为假 , 执行的操作2')
# else:
#     print('条件1为假, 执行的操作3')


# 例子: 用户输入数字, 判断是否为数字. 如果等于 520 ,输出我爱你
# num = input('请输入数字:')       # input 返回字符串
# if num.isdigit():
#     num = int(num)
#     if num == 520:
#         print('我爱你')
#     else:
#         print('你是个好人')
# else:
#     print('输入错误,请输入数字.')


# 1. 判断输入的整数是否既是 5 又是 7 的整倍数。若是则输出 yes；否则输出 no。
# num = int(input('请输入数字:'))
# if num % 5 == 0 and num % 7 == 0:
#     print('yes')
# else:
#     print('no')


# 2. 输入年、月，输出本月有多少天。合理选择分支语句完成设计任务。
# 闰年口诀: 四年一闰, 百年不闰, 四百年再闰   1900 不是
''' 思路先行, 再写代码 : 只有2月时候, 才会考虑是否为闰年 '''

year, month = input('请输入年和月').split(' ')  # '1234 5'
if int(month) == 2:
    if (int(year) % 4 == 0 and int(year) % 100 != 0) or int(year) % 400 ==0:
        print(29)
    else:
        print(28)
elif int(month) in (1,3,5,7,8,10,12):
    print(31)
elif int(month) in (4,6,9,11):
    print(30)
else:
    print('输入错误')

# if year % 4 == 0 and year % 100 != 0 or year % 400 ==0:
#     if month in (1,3,5,7,8,10,12):
#         print(31)
#     elif month in (4,6,9,11):
#         print(30)
#     elif month == 2:
#         print(29)
#     else:
#         print('请输入正确的月份')
# else:
#     if month in (1,3,5,7,8,10,12):
#         print(31)
#     elif month in (4,6,9,11):
#         print(30)
#     elif month == 2:
#         print(28)
#     else:
#         print('请输入正确的月份')


# 3. 输入星期的首字母，输出英文单词，如果首字母重复，根据用户输入的第二个字母来输出单词
# "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
# 思考: 输入字母, 输出对应的星期单词,  当字母为T, 输入第二个字母,再判断
first = input('请输入第一个字母:').lower()
if first == 'm':
    print('Monday')
elif first == 'w':
    print('Wednesday')
elif first == 't':
    two = input('请输入第二个字母:')
    if two == 'u':
        print('Tuesday')
    elif two == 'h':
        print('Thursday')
    else:
        print('第二个必须输入 u 或 h')
elif first == 'f':
    print('Friday')
elif first == 's':
    two = input('请输入第二个字母:')
    if two == 'a':
        print('Saturday')
    elif two == 'u':
        print('Sunday')
    else:
        print('第二个必须输入 u 或 a')
else:
    print('首字母输入错误')









