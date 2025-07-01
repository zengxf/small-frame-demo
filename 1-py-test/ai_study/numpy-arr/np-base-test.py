"""
NumPy 基础测试

常规运算操作，只是对每个元素进行公式搬套
"""

import numpy as np

a = np.array([10, 20])
a2 = a ** 2  # 对向量进行平方
print(a2)
# [100 400]

print('*' * 50)
b = np.array([[10, 20], [30, 40]])
b2 = b ** 2  # 对矩阵进行平方
print(b2)
# [[ 100  400]
#  [ 900 1600]]

print('*' * 50)
b = np.array([[4, 9], [25, 81]])
b2 = b ** 0.5  # 对矩阵进行开方
print(b2)
# [[2. 3.]
#  [5. 9.]]

print('*' * 50)
b = np.array([[10, 20], [30, 40]])
b2 = b * 2 + 1  # 对矩阵进行 * 2 + 1
print(b2)
# [[21 41]
#  [61 81]]

print('*' * 50)
b3 = b2 - 10  # 对矩阵进行 - 10
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

print('*' * 50)
Eg2 = 1e-6
Eg3 = 1e-8
print(f'Eg2(e): {Eg2},  Eg2: {Eg2:.6f},  Eg3: {Eg3:.6f}')
