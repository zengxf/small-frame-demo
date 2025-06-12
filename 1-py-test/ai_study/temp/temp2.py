import numpy as np

A = np.array([[1, 2], [3, 4]])  # (2,2)
print(A)
v = np.array([10, 20])  # (2,)

c=A * v  # v广播为[[10,20], [10,20]]，结果：[[10,40], [30,80]]
print(c)