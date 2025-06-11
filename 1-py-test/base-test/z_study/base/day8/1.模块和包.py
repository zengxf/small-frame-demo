# 模块是一个 .py 文件，模块主要用来封装函数、类、参数变量

''' 模块分3种'''
''' 1. 内置模块：random、time、sys '''
''' 2. 第三方模块：需要安装才可以使用 numpy （pip install 模块）'''
''' 3. 自定义模块：封装自己的函数、类，别人导入模块 '''


''' 模块的使用方式：导入模块 '''
''' 1. import math     # 使用模块下的函数：模块.函数     数学上运算 '''
import math
# print(dir(math))
print(len(dir(math)))

print(math.ceil(-3.5),math.floor(4.5),math.pow(3,2),math.sqrt(9))
# 向上取整           向下取整           求幂              开根号


''' 2. from math import 函数  # 直接到模块下的函数 '''
from math import pi,e
print(pi, e)


''' 3. from math import 函数 as 取别名   # 直接到模块下的函数 '''
'''    import 模块 as 取别名     # 直接到模块下的函数 '''
import math as m
# 三角函数：正弦、  余弦、    正切
print(m.sin(1), m.cos(1), m.tan(1))
print(m.asin(1), m.acos(1), m.atan(1))

# 双曲函数：正弦、  余弦、    正切
print(m.sinh(1), m.cosh(1), m.tanh(1))
# print(m.asinh(1), m.acosh(1), m.atanh(1))

''' 4. form 模块 import *   # 不要使用，与内置函数冲突 '''
# from math import *
# pow(3,4)        与内置函数


# 包是指一个目录，这个目录下必须有 __init__.py 文件，封装模块
# from 包.子包 import 模块
# from day8 import add