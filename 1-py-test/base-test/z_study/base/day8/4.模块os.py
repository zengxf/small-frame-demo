''' os 模块，与 win 系统交互的：对文件或目录的操作 '''

import os

# 1. mkdir(目录的路径)    创建单个目录，如果目录存在会抛出异常 FileExistsError
# os.mkdir('abc')       # 当前目录
# os.mkdir(r'C:\Study\python33\abc')      # 路径必须存在

# 2. makedirs(目录的路径, exist_ok=False)     创建多级目录，如果目录存在会抛出异常 FileExistsError
# os.makedirs(r'a\b\c', exist_ok=True)

# 3. remove(文件路径)    移除文件
# os.remove(r'C:\Study\python33\day8\day10\a.py')

# 4. renames(原文件路径，目标文件路径)   重命名文件
# os.renames(r'4.os模块.py', r'4.模块os.py')

''' os.path 子模块 '''
# print(os.path.abspath(r'4.模块os.py'))
#
# print(os.path.basename(r'C:\Study\python33\day8\4.模块os.py'))
#
# # exists(文件或目录)  判断文件或目录是否存在，存在 True
# print(os.path.exists(r'C:\Study\python33\day8\add.py'))
# print(os.path.exists(r'add.py'))
# print(os.path.exists(r'abc'))

# split 分割文件名， splitext 分割扩展名  文件中返回元组
dirs, filename = os.path.split(r'C:\Study\python33\day8\4.模块os.py')
print(dirs)
print(filename)
# files, ext = os.path.splitext(r'C:\Study\python33\day8\4.模块os.py')
# print(files)        # 文件没有后缀
# print(ext)          # 后缀扩展名 .py

print(os.path.splitext(r'C:\Study\python33\day8\4.模块os.py12345'))

# join 拼接文件的路径，为文件操作中，拼接文件路径，
# 如果出现多个反斜杠（r'\a',r'\b','c'），以最后一个为主，\b\c, a清空
# print(os.path.join('a', 'b', 'c'))  # a\b\c
# print(os.path.join(r'\a', 'b', 'c'))  # \a\b\c
# print(os.path.join(r'\a', r'\b', 'c'))  # \b\c
# print(os.path.join('D:', r'\a', r'\b', 'c'))  # d:\b\c  盘符保留

print("2--" * 20)
# 获取文件的大小 字节 1024 k\m\g
print(os.path.getsize(r'4.模块os.py'))
print(os.path.getatime(r'4.模块os.py'))
print(os.path.getctime(r'4.模块os.py'))
print(os.path.getmtime(r'4.模块os.py'))

print(os.path.isfile(r'4.模块os.py'))
print(os.path.isdir(r'abc'))

print("3--" * 20)
''' time 模块 '''
import time

print(time.time())  # 1749020204.710873 秒,精确微秒 1970-01-01 ~ 现在
print(time.time_ns())  # 返回纳秒 9
print(time.perf_counter())  # CPU的时间
print(time.perf_counter_ns())

time.sleep(1)  # 休眠，暂时，延迟

print(time.ctime())  # Wed Jun  4 15:01:38 2025
print(time.localtime())

print(time.strftime('%Y-%m-%d %H:%M:%S %p %A', time.localtime()))
