"""
sys 模块

sys 模块是 Python 标准库中的一个重要模块，提供了对解释器使用环境的访问
"""

import sys

print('获取 Python解释器版本：', sys.version)  # 3.10.11
print('获取当前运行平台：', sys.platform)  # win32
print('获取当前运行平台：', sys.argv)  # ['D:\\Study\\Python\\sys模块.py']
print('输出模块搜索路径：', sys.path)  # ['D:\\Study\\Python',...]
print('获取对象的内存大小', sys.getsizeof('python'))  # 55
print('获取已加载的模块', sys.modules.keys())  # 字典视图 dict_keys(['sys',...])
# sys.exit('优雅的结束程序')  # 默认为 0
