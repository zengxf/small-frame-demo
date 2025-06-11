import numpy as np


arr3 = np.arange(36).reshape(2, 3, 2, 3)
print(arr3)
print('#' * 100)
print(arr3[:, [1, 0, 2], [0, 1, 0], :].shape)  # shape= 2, 3, 3
print(arr3[:, [1, 0, 2], [0, 1, 0], :])
print('#' * 100)
print(arr3[:, [1, 0, 2], :, [0, 1, 0]].shape)  # shape= 3, 2, 2
print(arr3[:, [1, 0, 2], :, [0, 1, 0]])