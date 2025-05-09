"""
copy 模块
"""

import copy

lst = [1, 2, [3, 4, 5]]
print(lst, '原列表的 id:', id(lst))

''' copy.copy() 对 Python 中的任意对象进行浅拷贝。 '''
copy_lst = copy.copy(lst)  # 创建新对象，但与原列表共享嵌套的可变对象
print(copy_lst, '浅拷贝后 id:', id(copy_lst))

''' copy.deepcopy() 对 Python 中的任意对象进行深拷贝 '''
deepcopy_lst = copy.deepcopy(lst)  # 创建完全独立的新对象
print(deepcopy_lst, '深拷贝后 id:', id(deepcopy_lst))

print("\n****************************")
lst[2][0] = 'python'
print(lst, '修改后原列表 id:', id(lst))
print(copy_lst, '修改后浅拷贝 id:', id(copy_lst))
print(deepcopy_lst, '修改后深拷贝 id:', id(deepcopy_lst))
