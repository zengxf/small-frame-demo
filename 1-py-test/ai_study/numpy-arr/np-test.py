import numpy as np

arr3 = np.arange(12).reshape(3, 4)
print('*' * 50)
print(arr3)
# [[ 0  1  2  3]
#  [ 4  5  6  7]
#  [ 8  9 10 11]]

''' 1. np.reshape()  升降维度，size 大小不变 '''

print('*' * 50)
print(np.reshape(arr3, shape=(2, 6)))
# [[ 0  1  2  3  4  5]
#  [ 6  7  8  9 10 11]]

print('*' * 50)
print(np.reshape(arr3, shape=(2, 2, 1, 3)))
# [[[[ 0  1  2]]
#
#   [[ 3  4  5]]]
#
#
#  [[[ 6  7  8]]
#
#   [[ 9 10 11]]]]

''' 2. np.sort 默认按最后一维排序'''
np.random.seed(1)
arr3 = np.random.randint(1, 12, (3, 4))
print('*' * 50, 'sort')
print(arr3)
# [[ 6  9 10  6]
#  [ 1  1  2  8]
#  [ 7 10  3  5]]

print('*' * 50)
print(np.sort(arr3))  # axis=-1
# [[ 6  6  9 10]
#  [ 1  1  2  8]
#  [ 3  5  7 10]]

print('*' * 50)
print(np.sort(arr3, axis=0))
# [[ 1  1  2  5]
#  [ 6  9  3  6]
#  [ 7 10 10  8]]

print('*' * 50)
print(np.sort(arr3, axis=None))  # 展平排序
# [ 1  1  2  3  5  6  6  7  8  9 10 10]