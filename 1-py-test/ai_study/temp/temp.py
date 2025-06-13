import numpy as np

arr2 = np.random.uniform(1, 10, (2, 3))
print(arr2)
print('*' * 50)
print('截取整数', np.trunc(arr2))
print('*' * 50)
print(np.round(arr2, 2))
print('*' * 50)
print(np.ceil(arr2))
