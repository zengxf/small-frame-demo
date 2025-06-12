import numpy as np

arr = np.array([[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]])
print(arr)
# arr 是二维数组，axis=1 的个数为 4 列，整数只能是 2 或 4，索引值 0，1，2，3
# print(np.split(arr, 2, axis=1))
# # [array([[ 1, 2], [ 5, 6], [ 9, 10]]), array([[ 3, 4], [ 7, 8], [11, 12]])]
# print(np.split(arr, [1], axis=1))
# [array([[1], [5], [9]]), array([[ 2, 3, 4], [ 6, 7, 8], [10, 11, 12]])
print(np.hsplit(arr, 2))