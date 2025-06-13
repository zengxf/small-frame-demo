import numpy as np

''' 8. repeat(数组, 重复次数, axis=轴)  指定轴重复'''

m = np.zeros((8, 8))
m[1::2, ::2] = 1
m[0::2, 1::2] = 1
print(m)
