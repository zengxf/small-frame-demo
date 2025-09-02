import numpy as np

arr = np.arange(0, 24).reshape((2, 3, 4))
print(arr)

print('*' * 40)
ar1 = np.transpose(arr, (0, -1, -2))
print(ar1.shape)
print(ar1)
