# print 格式化输出

''' 1. %占位符：%d-整数%i， %f-浮点数（默认6位）， %s-字符串
    %0nd, %n.mf，%ns        n数字表示宽度
'''
name = 'yoyo'
age = 20
sal = 123.456
print('姓名：%s，年龄：%d，薪资：%f' % (name, age, sal))

print('123456789')
print('%05d' % age)
print('%010.2f' % sal)
print('%10s' % name)

''' 2. f表方式 f"{替换内容} " '''
# f'{"替换内容": [填充符][对齐方式][宽度].[精度][类型sdf]}'

print(f'{name:*>20s}')  # 字符串默认左对齐：>右  <左  ^居中
print(f'{name:^20s}')  # 填充符。空格
print(f'{age:20d}')  # 数字默认右对齐
print(f'{sal:020.4f}')
print(f'{sal:<20.4f}')

''' 3. '{}{}'.format格式化 '''
# '位置索引:[填充符][对齐方式][正负数][宽度].[精度][类型sdf]').format(位置参数)
i = 5
f = 5.678
print("#" * 50)
print('{:10d}'.format(i))  # 默认位置索引从 0开始
print('{1:20.2f}\n{1:010f}'.format(i, f))

''' 4. format 内置函数：注意格式化必须使用单、双引号'''
# format('值', '[填充符][对齐方式][宽度].[精度][类型sdf]')
name = 'yoyo'
age = 20
sal = 123.456
print(format(name, '>10s'))
print(format(name, "^10s"))
print(format(age, "^010d"))

# Python语言与其他语言不同的是，没有其他符号。代码必须顶格

''' 练习 '''
# 1. 列出字符串的常用 5 个处理函数，并说明使用方法？
# ''.split('分隔符', '次数')   返回列表：[]
# ''.strip('指定去除字符')  默认两边去除空格
# '连接符'.join('可迭代对象')
# ''.endswith('指定结尾字符')   是否以字符结尾
# ''.replace('旧','新','次数')
# f'{'值或变量':[填充符][对齐方式][宽度].[精度][类型dfs]}'

# 2. 计算定义 2 个变量分别保存 100 除以 3 得到的商、余数，并显示结果。
n1 = 100
n2 = 3
商, 余数 = divmod(n1,n2)
print(商, 余数)

# 3. 输入一个三位以上的整数，输出其百位以上的数字。
# num = input('请输入三位以上的整数：')      # input 返回字符串
# print(int(num) // 100)

# 4. 已知 a = 2, b = 3 。要求将 a 和 b 的值调换，并打印结果。
a = 2
b = 3

a, b = b, a
print(a,b)

# 5. 统计字符串 “hello world,I like python” 中单词的数量并打印结果。
# len('对象')
# 如果对象是字符串，返回的长度。
# 如果对象是列表、元组，返回元素的个数


words = 'hello world,I like python'
print(len(words))
print(len(words.replace(',',' ').split(' ')))



# 基本类型之间的转化的函数：int、float、str、bool、chr、ord
n = 10
f = 12.345
s1 = '123'      # 整数字符
s2 = "3.456"

# 1. 浮点数 或 整数字符可以转 int
print(int(f))
print(int(s1))
# print(int(s2))      # 浮点字符不能转整数

# 2. 转 float
print(float(n))
print(float(s1))
print(float(s2))

# 3. 转字符 str
print(str(n))
print(str(f))

# 4. chr(整数)   ord(单个字符)
print(chr(20000))       # 丠

print(ord('爱'))         # 29233
print(ord('a'))