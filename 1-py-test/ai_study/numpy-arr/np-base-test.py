import numpy as np

print("+" * 40)
ar1 = np.array([[1, 2, 3], [4, 5, 6]])
print(ar1)
# [[1 2 3]
#  [4 5 6]]

print("+" * 40)
ar2 = np.arange(7, 19).reshape((3, 4))
print(ar2)
# [[ 7  8  9 10]
#  [11 12 13 14]
#  [15 16 17 18]]

print("+" * 40)
ar4 = np.dot(ar1, ar2)
print(ar4)
print(ar4.T)
# [[ 74  80  86  92]
#  [173 188 203 218]]