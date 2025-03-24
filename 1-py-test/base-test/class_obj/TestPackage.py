"""
module 有 __init__.py 相当于是一个包，
pck1 有 __init__.py 也相当于是一个包，
所以要用 module.pck1 引用。
"""

# 测试包

from module.pck1 import function_a, function_b

function_a()
function_b()
