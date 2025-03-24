"""
测试包

----------------------------------------------------

module 有 __init__.py 相当于是一个包，
pck1 有 __init__.py 也相当于是一个包，
所以要用 module.pck1 引用。
"""

from module.pck1 import function_a, function_b
from module.pck1 import module1, module2  # 导入包中的特定模块

module1.function_a()
module2.function_b()
module2.function_c()

function_a()
function_b()
