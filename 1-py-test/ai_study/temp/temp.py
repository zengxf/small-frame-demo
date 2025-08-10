import numpy as np

arr2 = np.array([[1, 2, 3],
                 [4, 5, 6],
                 [7, 8, 9]])

print('#' * 50)
# [[8 8 8 8 8]
#  [8 1 2 3 8]
#  [8 4 5 6 8]
#  [8 7 8 9 8]
#  [8 8 8 8 8]]

print('#' * 50)
print(np.pad(arr2, pad_width=2, mode='edge'))
np.pad(arr2, pad_width=2, mode='constant', constant_values=0)