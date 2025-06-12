''' 三角函数、双曲函数、指数函数 '''
import numpy as np

arr3 = np.arange(12).reshape(2,3,2)
print(arr3)
# print(np.sin(arr3), np.cos(arr3), np.tan(arr3))
# print(np.sinh(arr3), np.cosh(arr3), np.tanh(arr3))
# print(np.asin(arr3), np.acos(arr3), np.atan(arr3))
# print(np.log(arr3), np.log2(arr3), np.log10(arr3))

''' round、ceil、floor、trunc、sqrt '''
np.random.seed(1)
arr2 = np.random.uniform(1, 10,(2,3))
print(arr2)
print('*'*50)
print('截取整数',np.trunc(arr2))
print('*'*50)
print(np.round(arr2, 2))
print('*'*50)
print(np.ceil(arr2))


''' 1. cumsum 累加和 '''
print('*'*50)
arr = np.arange(1, 13).reshape(2,3,2)
# print(arr)
print(np.cumsum(arr))   # [ 1  3  6 10 15 21 28 36 45 55 66 78]
# print(np.cumsum(arr, axis=2))

# print(np.cumsum(arr, axis=1))


''' 2. 算术运算-+*/、比较运算-返回布尔值 （二元操作）'''
arr1 = np.arange(1, 13).reshape(2,3,2)

arr2 = np.arange(101, 113).reshape(2,3,2)
# print(arr2)
# print(arr1 + arr2)

# print(arr1 / arr2)

# print(arr2 % 3)

print(arr1 > arr2)

print(arr1 > 10)


print(np.add(arr1, arr2))