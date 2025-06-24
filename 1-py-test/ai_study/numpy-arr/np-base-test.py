"""
NumPy 基础测试
"""

import numpy as np

a = np.array([10, 20])
a2 = a ** 2  # 对向量进行平方
print(a2)
# [100 400]

print('*' * 50)
b = np.array([[10, 20], [30, 40]])
b2 = b ** 2  # 对向量进行平方
print(b2)
# [[ 100  400]
#  [ 900 1600]]

print('*' * 50)
b = np.array([[10, 20], [30, 40]])
b2 = b * 2 + 1  # 对向量 w 进行 * 2 + 1
print(b2)
# [[21 41]
#  [61 81]]

print('*' * 50)
b3 = b2 - 10  # 对向量 w 进行 - 10
print(b3)
# [[11 31]
#  [51 71]]

print('*' * 50)
b = np.array([[3, 8], [15, 24]])
eps = 1
b2 = np.sqrt(b + eps)
print(b2)
# [[2. 3.]
#  [4. 5.]]
