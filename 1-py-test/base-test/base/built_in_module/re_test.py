"""
re 模块

re 模块：正则表达式 Regular Expression 是一种文本模式，可以用来描述和匹配字符串的特定模式。

1. match函数：从字符串的开始位置进行匹配，如果匹配成功，返回一个 re.match 对象。
    如果不是从开始位置匹配成功的话，返回 None。

flags 参数说明，用于控制正则表达式的匹配方式，常用的操作如下：
 ① re.I：忽略大小写
 ② re.M：多行模式，改变【^】和【$】的行为
 ③ re.S：即为 . 的任意字符，改变【.】的行为不能出现换行符
 ④ re.L：表示特殊字符集 \w、\W、\b、 \B、\s、\S 依赖于当前环境
 ⑤ re.U：表示特殊字符集 \w, \W, \b, \B, \d, \D, \s, \S 依赖于 Unicode 字符属性数据库
 ⑥ re.X：为了增加可读性，忽略空格和 # 后面的注释
"""

import re

# 基本格式：re.match(pattern正则表达式,string字符串[,flags标志位])
res = re.match('hello', 'hello python')
print(r'res: ', res)
#

"""
flags 参数测试
"""
print("\n---------------------------")
url = 'Hello\nhello Python'
res = re.match(r'h.*', url, re.S | re.I | re.M)
print(res.group())
#

"""
search函数：与 match 一样，如果匹配成功，返回一个 re.match 对象，如果匹配不成功，返回 None。
不同的是 search 从整个字符串中查找。
"""
print("\n---------------------------")
str = 'Hello Python'
res = re.search('p.+', str, re.I)
print(res)
print(res.group(), res.span())
#

"""
findall函数：从字符串中匹配内容，匹配成功，将匹配内容返回到一个列表中。
否则，返回空列表。
"""
print("\n---------------------------")
str = 'Hello 2023 python3.8'
res = re.findall('\d+', str)
print(res)  # 结果：['2023', '3', '8']
res = re.findall('\d+?', str)
print(res)  # 结果：['2', '0', '2', '3', '3', '8']
#

"""
sub 函数：用来查找并替换字符串中的匹配内容，返回替换后的内容

subn 用法与 sub 一致，只是返回结果不同，subn 返回元组（替换后的内容，替换的次数）
"""
print("\n---------------------------")
url = 'Www.Python.Org.Com'
res = re.sub('\W+', '+', url)
print(res)  # 结果： Www+Python+Org+Com
res = re.subn('\W+', '-', url)
print(res)  # 结果：('Www-Python-Org-Com', 3)
#

"""
split 函数：按照匹配的字符对字符串进行切割，将切割内容返回到一个列表中。
    如果匹配失败，将原字符串返回到列表中
"""
print("\n---------------------------")
str = 'I love python, I live study; How about you?'
res = re.split(r'[,;\s]+', str)
print(res, '单词数量为：', len(set(res)))
# 结果： ['I', 'love', 'python', 'I', 'live', 'study', 'How', 'about', 'you?'] 单词数量为： 8
#

"""
compile 函数：用来编译优化正则表达式，将正则表达式转换为对象，可供 match、search、 findall 使用。
"""
print("\n---------------------------")
reg = re.compile('\w*o\w*')
str = 'I love python, I live study; How about you?'
res = reg.findall(str)
print('带字母"o"的单词有', len(res), '分别为:', res)
