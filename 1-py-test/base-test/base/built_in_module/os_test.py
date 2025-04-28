"""
os 模块

os 模块（operating system的缩写），os 模块提供程序与操作系统进行交互的接口。
"""
import os

''' 1. os.listdir(目录) 获取当前目录的文件，返回到列表中 '''
print(os.listdir('.'))  # . 表示当前目录
print(os.listdir('../'))  # ../表示上一级目录
print(os.listdir('../..'))  # ../.. 上上一级目录

# ''' 2. os.mkdir('文件目录', mode = 0o777) 创建单个目录，777表示读写执行 '''
# # 当前目录下创建新目录 test。如果当文件已存在时，报错
# os.mkdir('./test', mode=0o777)  # 在已有目录 test 下创建新目录 abc
# os.mkdir('./test/abc', mode=0o777)

# ''' 3. os.makedirs('文件目录', mode = 0o777, exist_ok = False) 创建多级目录 '''
# # 当前目录下创建 a/b/c，如果当文件已存在时，报错
# os.makedirs('a/b/c')  # 当 exist_ok = true，如果文件存在，则忽略错误
# os.makedirs('a/b/c', mode=0o777, exist_ok=True)

''' 4. os.environ 获取环境变量，返回字典对象(key:value) '''
print("\n---------------------------")
print(os.environ)  # 返回字典对象
print(os.environ.items())  # 返回字典视图：key:value
print(dict(os.environ.items()))  # 返回字典 key:value
print(os.environ.keys())  # 返回字典视图 key
print(os.environ.values())  # 返回字典视图 value
print(os.environ['path'])  # 返回环境变量path的值，以分号分隔

''' 4. os.path 模块，主要用来处理文件路径的操作函数 '''
print("\n---------------------------")
# 1) exists(路径) 判断路径是否存在，存在返回 True
print(os.path.exists(r'a/b/c'))  # True
# 2) isdir(目录) 判断目录是否存在，存在返回 True
print(os.path.isdir(r'a/b/c'))  # True
path = r'D:\Study'
print(os.path.isdir(path))
# 3) isfile(文件) 判断文件是否存在，存在返回 True
print(os.path.isfile(r'D:\Study\人工智能介绍.mp4'))  # True
# 4) abspath(路径) 输出当前文件的绝对路径
print(os.path.abspath(r'./calc.py'))  # D:\Study\Python\day5\calc.py
print(os.path.abspath(r'day5'))  # D:\Study\Python\day5\day5

print("\n---------------------------")
# 5) dirname(path) 输出当前路径文件的目录
print(os.path.dirname(r'D:\Study\Python\day5\calc.py'))  # D:\Study\Python\day5

# 7) splitext(文件后缀) 分隔文件路径与后缀名，返回元组（文件路径,后缀名）
print(os.path.splitext(r'D:\Study\Python\day5\calc.py'))  # ('D:\\Study\\Python\\day5\\calc', '.py')
print(os.path.splitext(r'calc.py'))  # ('calc', '.py')

# 8) split(文件路径) 分隔文件路径与文件名，返回元组（路径,文件名）
print(os.path.split(r'D:\Study\Python\day5\calc.py'))  # ('D:\\Study\\Python\\day5', 'calc.py')
print(os.path.split(r'D:\Study\Python\day5\calc.test.py'))  # ('D:\\Study\\Python\\day5', 'calc.test.py')
print(os.path.split(r'calc.py'))  # ('', 'calc.py')
