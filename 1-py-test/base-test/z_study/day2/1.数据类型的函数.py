# 数值相关的函数：整数 int、浮点数 float
'''
1. abs(数值)  求绝对值
2. pow(数值, 幂次方)     求幂
3. divmod(除数, 被除数)  求商和余数：元组（商,余数）
4. round(数值, 小数位)  四舍五入
'''
num1 = 3
num2 = 10
print(abs(num1), pow(num2, num1), divmod(num2, num1))

# round(数值, 小数位)
# 1. 小数位可以省略，对数值进行取整
print(round(3.5314))        # 4

# 2. 小数为可以正数（小数点右边）、负数（小数点左边）
print(round(3.567, 2))
print(round(55.67, -2))

# 3. round 数值为 .5 时，数值向偶数靠拢
print(round(4.5))   # 4

# math 模块，数学相关的函数


# 字符串相关的函数，Python面向对象的编程，对象.函数
# 1. 字符串中的每个字符都有2个下标，字符串的索引
# 从左到右：下标从 0 开始，依次递增
# 从右到左：下标从 -1 开始，依次递减
s = 'python'
#    012345
#       -2-1

print(len(s))   # 长度 6
# 2. 字符串的索引：字符串[索引值]
print(s[2], s[-3])

# 3. 字符串的切片：字符串[起始值 : 结尾值 : 步长]
# 起始值默认从 0 开始，结尾值默认 len(s) - 1，步长默认 1（步长为负数，从右开始）
# 索引的范围：左闭右开
s1 = 'hello python AI'
print(s1[6:12])    # 6~11
print(s1[::2])      # hlopto I
print(s1[::-2])     # I otpolh

print(s[::-1])      # nohtyp


# 4. 字符串属于不可变的数据类型，定义不能修改字符串
s2 = 'python'
s3 = 'python'
print(id(s3))
s3 = 'hello'
print(id(s3))
print(s2 is s3)     # True

# 5. replace(旧，新，次数)  替换，返回新字符串
# 格式：''.replace('旧字符','新字符', '次数-默认全部')
s4 = 'python pip'
new_s = s4.replace('p', 's', 2)
print(new_s)    # sython sip

# 6. find 查找，返回索引值，如果没有找到返回 -1
# s4.find('字符', '起始索引-默认0', '结束索引-默认len')
print(s4.find('h'))     # 3
print(s4.find('pip'))   # 7
print(s4.find('h',5, len(s4)))     # 3

# index 与 find 一样，查找字符的索引值，不同是 index 如果字符不存在，抛出异常
# print(s4.index('s'))        # ValueError: substring not found

# rfind() 与 find 一样，r(right)find
# rindex() 与 index 一样，默认从左到右，r 从右到左
'python pip'
print(s4.rfind('p'))        # 返回索引值-从左开始 9
print(s4.find('p'))         # 0

# 7. strip 默认去除两边的空格
# ''.strip('去除字符')  逐个去除字符，出现没有停止
s5 = '  hello  ni hao   '
print(s5)
print(s5.strip(' '))

print(s5.strip(' hoea'))        # llo  ni

s5.lstrip()     # l left
s5.rstrip()     # r right

# 去除 s5 全部空格：字符串.函数.函数--从左到右执行
print(s5.strip().replace(' ',''))


# 8. split 分割，返回是列表。rsplit
s6 = 'www.python.org'
# s6.split('分隔符', '分割次数')
print(s6.split('.'))
print(s6.split('python'))
print(s6.split('.',1))

path = r'C:\Study\dataset\label_voc\ai.txt'
print(path.rsplit('\\',1)[-1])

s7 = '12\n34\r567\r\n89'
print(s7.splitlines())      # \n  \r  \r\n，读取文件


# 9. join： '连接符'.join('可迭代对象')
# 可迭代对象：字符串、元组、列表、字典、集合
print('-'.join('abcde'))


# 10 . count('字符', '起始', '结尾') 统计字符出现的次数
s7 = 'www.python.org'
print(s7.count('o'))
print(s7.count('o',10, len(s7)))

print(s7.count('o', s7.find('o') + 1))

# 11. endswith('结尾字符', 起始索引值，结尾索引值 )  是否以指定的字符结束，返回 True 或 False
file = 'test.txt.png'
print(file.endswith('png'))

# ''.startswith('开头字符')     是否以指定的字符开始
print(file.startswith('txt', 5))


# 12. is 开头，通常都是判断函数
print('#'*50)
print('pthon'.isalpha())        # 是否纯字母
print('12345'.isdecimal())       # 是否为纯数字
print('python12345'.isalnum())      # 是否字母或数字
print('PYTHON'.isupper())       # 大写
print('python'.islower())        # 小写
print('python_123'.isidentifier())  # 是否为标识符-字母或数字或下划线
print('123_python'.isidentifier())  # 标识符不能以数字，纯数字
print(b'123'.isdigit())     # True，数字或字节码 b''
print('Www.Python'.istitle())       # 单词首字母大写


# 13. 大小写转化
s9 = 'PyThon www'
print(s9.lower())       # 小写
print(s9.upper())       # 大写
print(s9.title())       # 单词首字母
print(s9.capitalize())      # 字符串首字母大写
print(s9.casefold())     # 小写 python www，加入国际语法
print(s9.swapcase())       # 大小写互换


# 14. 对齐方式
s9 = 'PyThon www'
print('#'*20)
print(s9.center(20))
print(s9.center(20, '-'))

print(s9.ljust(20, '*'))
print(s9.rjust(20, '!'))
print(s9.zfill(20))     # 零填充