''' re 正则表达式：爬虫 '''
import re
from re import findall

''' 正则表达式符号：.  *  +  ?  |  ^  $   ()  []  {}  \dD  \sS  \mM '''
''' 
1. . 除换行符，匹配任意一个字符
2. * 匹配 0 或多个字符
3. + 至少匹配一次
4. ? 匹配一次，非贪婪匹配
5. | 选择，or
6. ^ 开头
7. $ 结尾
8. \dD-数字  \mM-任意标识符   \sS-特殊字符-换行、制表
9. () 整体做为一个字符
10. [0~9] 匹配数字 
11. {m, n}  从 m 到 n
'''

# 标记位 ： re.I 忽略大小写  |re.M 多行 |re.S 任意字符

# 1. re.match(匹配正则表达式, 字符串, 标记位)   返回 match 对象
print(re.match('.*','py\nthon'))

print(re.match('.+','py\nthon'))

print(re.match('.+?','python'))

print(re.match('^h', 'hello'))

print(re.match('.*n$','python'))

print(re.match('^[hp]','python'))

print(re.match('^p{0,9}','python pip'))

print(re.match('^[0123456789]','1python'))

print(re.match('^p','Python', (re.I|re.M|re.S)))

print(re.match('.*','py\nthon', re.S))

# 2. re.sub(正则表达式, 替换字符, 原字符串, 次数, 标记位)     替换，替换后的内容
print(re.sub('\W', '-', 'www.python.org', re.S))
# www-python-org

# 3. re.split(正则表达式, 原字符串, 次数, 标记位)   分割，返回列表
print(re.split('\W', 'www.python.org', 1, re.S))
# ['www', 'python.org']

# 4. findall(正则表达式, 原字符串, 标记位)    查找，返回列表
print(re.findall('\w*o\w*', 'www.python.org'))
# ['python', 'org']


import copy
# 1. copy.copy()  # 浅拷贝
lst = [1,2,[3,4],5]
new_lst = copy.copy(lst)
print('浅拷贝',new_lst)
print('原列表',lst)

# lst[2][:] = ['a','b','c']
# print('#'*50)
# print('浅拷贝,修改后',new_lst)

# 2. copy.deepcopy()  # 深拷贝
new_list = copy.deepcopy(lst)
print('深拷贝', new_list)
print('#'*50)
lst[2][:] = ['a','b','c']
print('修改后，深拷贝', new_list)
print('#'*50)


import uuid
print(uuid.uuid4())
# 唯一的随机序列：a3a176df-4c37-47be-88cd-269c3ca8c7e6

