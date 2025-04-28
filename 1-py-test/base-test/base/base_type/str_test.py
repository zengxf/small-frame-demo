"""
字符串测试
"""

# 字符串的加法
print('python' + '3.10')  # python3.10

# 字符串的乘法
print('python-' * 3)  # pythonpythonpython

print(len('python'))  # 6

# 设置多个对象之间为 .
print('www', 'python', 'org', sep='.')  # www.python.org

# 输入内容设置结尾符为 !
print('hello python', end='!\n')  # hello python!

# # 将输出的内容保存到 love.txt 文本文件中，并立即刷新缓存
# print('i love you', file=open(r'./str_test.md', 'w'), flush=True)

# 对象为字符串
print('-'.join('abcde'))  # a-b-c-d-e
# 对象为元组、列表
print('+'.join(('1', '2', '3')))  # 1+2+3
print('+'.join(['a', 'b', 'c']))  # a+b+c
print('+'.join({'1', '3', '5', '1', '0', '3'}))  # 3+0+5+1
# 对象为字典
print('、'.join({'name': 'yoyo', 'age': 30}))  # name、age
print("--------------------------\n\n")

str6 = ' abc.bb cc.aaa '
# 去除空格，只能去除两边
print(str6.strip())  # abc.bb cc.aaa
# 指定去除 abc以及空格
print(str6.strip('abc '))  # .bb cc.

''' rstrip 从右边开始、lstrip 从左边开始 '''
print('#', str6.rstrip(), '#')  # abc.bb cc.aaa
print('#', str6.lstrip(), '#')  # abc.bb cc.aaa

print('12345'.isdigit())  # 是否数字，含字节数 b'123'
print('12一Ⅲ'.isnumeric())  # 是否数字，含汉字数字
print('abcABC'.isalpha())  # 是否为字母
print('abc'.islower())  # 是否小写
print('ABC'.isupper())  # 是否大写
print('Www.Python.Org'.istitle())  # 每个单词首字母是否大小
print('abcABC1234'.isalnum())  # 是否字母+数字
# 以下不常用的，了解即可
print('q1!@#'.isascii())  # ascii码 print('qeQ132_'.isidentifier())  # 是否符合标识符
print(' '.isspace())  # 是否为空格
print('qwe123!@#'.isprintable())  # 判断字符串所有字符是否都可以打印，除 \n \t \r 等
