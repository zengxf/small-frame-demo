import numpy as np

arr = np.arange(1, 25).reshape((2, 3, 4))
print(arr)
# [[[ 1  2  3  4]
#   [ 5  6  7  8]
#   [ 9 10 11 12]]
#
#  [[13 14 15 16]
#   [17 18 19 20]
#   [21 22 23 24]]]

print('#' * 100)
index1 = np.array([[1, 0], [0, 1]])
# print(arr[index1])
print(arr[[1, 0], [0, 1]])