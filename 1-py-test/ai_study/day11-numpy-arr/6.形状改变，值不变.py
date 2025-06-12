''' 1. 升维，降维 : size 一致 '''
import numpy as np

arr3 = np.arange(12).reshape(3, 4)
''' 1. np.reshape()  升降维度，size大小不变 '''
print(np.reshape(arr3, shape=(2, 6)))  # size
print(np.reshape(arr3, shape=(2, 2, 1, 3)))

''' 2. np.sort 默认按最后一维排序'''
np.random.seed(1)
arr3 = np.random.randint(1, 12, (3, 4))
print(np.sort(arr3))  # axis=-1

print(np.sort(arr3, axis=0))

print(np.sort(arr3, axis=None))  # 展平排序

''' 3. ravel 展平数组一维'''
np.random.seed(2)
arr4 = np.random.randint(1, 12, (3, 4))
print(arr4)
print(np.ravel(arr4))

''' 4. T 转置 '''
# print(np.transpose(arr4))
print(arr4.T)

''' 5. expand_dims(数组, axis=轴) '''
print('#' * 50)
arr2 = np.array([[1, 2, 3], [4, 5, 6]])   # 2，3
print(np.expand_dims(arr2, axis=1))     # 2, 1, 3
# print(arr2[:, None, :])
# print(arr2[:, np.newaxis, :])

print(np.expand_dims(arr2, axis=0))     # 1, 2, 3


''' 6. np.argsort  排序后的返回索引值'''
np.random.seed(4)
arr6 = np.random.randint(1, 12, (3, 4))
print('#' * 50)
print(arr6)
# print(np.argsort(arr6))
print(np.argsort(arr6,axis=0))