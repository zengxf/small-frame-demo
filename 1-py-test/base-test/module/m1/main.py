"""
测试模块
"""

# main.py

import greetings  # 导入整个模块
from greetings import say_hello  # 导入模块中的特定函数

# from greetings import *  # 导入模块中的所有内容。  可能会导致命名冲突，谨慎使用。

name = "Alice"
print(greetings.say_hello(name))
print(greetings.say_goodbye(name))

print(say_hello("Alice"))
