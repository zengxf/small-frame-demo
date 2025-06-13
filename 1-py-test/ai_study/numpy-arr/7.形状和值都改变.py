''' 1. # 矩阵相乘 @ '''
''' 条件1：第一个列必须与第二个行，相等，返回（第一个行, 第二个的列）'''
import numpy as np

# shape1=(行, 列)    @ shape2=(行, 列) = shape(1行, 2列)
arr1 = np.array([[1, 2, 3], [4, 5, 6]])
print(arr1.shape)  # (2, 3)

arr2 = np.array([[1, 2, 3, 4], [4, 5, 6, 8], [7, 6, 8, 9]])  # （3, 4)
print(arr2.shape)  # (3, 4)

print(arr1 @ arr2)
# [[ 30  30  39  47] [ 66  69  90 110]]
# [[1*1 + 2*4 + 3*7 = 30,  1*2 + 2*5 + 3*6 = 30, ..]
# [4*1 + 5*4 + 6*7 = 66,  ....]]

''' 条件2：多维矩阵相乘：必须满足（1列=2行），shape=(剩余的必须满足广播, 1行，2列)'''
# arr3 = np.arange(1, 9).reshape(1, 2, 3, 4)
# print(arr3)
# arr4 = np.arange(1, 13).reshape(3, 1, 4, 1)
# print(arr4)

# print(arr3 @ arr4)

# 1*10 + 2*12 + 3*14 + 4*16 = 10+24+42+64 = 140,  1*11 + 2*13 + 3*15 + 4*17 = 11+26+45+68 = 150
#
# 5*10 + 6*12 + 7*14 + 8*16 = 50+72+98+128 = 348,  ...

# 9*10 + 10*12 + 11*14 + 12*16 = 90+120+154+192 = 556


''' 2. stack 叠加数组多个数组，升维，shape=改指定的轴进行操作 '''
# 格式：np.stack('数组', axis=0)
arr3 = np.arange(24).reshape(2, 3, 4)
arr4 = np.arange(100, 124).reshape(2, 3, 4)

# print(arr4)
print('#' * 50)
# print(np.stack((arr3, arr4), axis=0))       # 层 2, 2，3，4

# print(np.stack((arr3, arr4), axis=1))       # 行 2, 2, 3, 4

# print(np.stack((arr3, arr4), axis=2))  # 列 2, 3, 2, 4


''' 3. concatenate 连接多个数组，维度不变，形状改变根据轴连接。 '''
# print(np.concatenate((arr3, arr4),axis=0))     #shape = 2，3，4 + 2，3，4 = 4，3，4
# print(np.vstack((arr3, arr4)))      # 垂直叠加（连接axis=0）

# print(np.concatenate((arr3, arr4),axis=1))     #shape = 2，3，4 + 2，3，4 = 2，6，4
# print(np.hstack((arr3, arr4)))          # 水平叠加（连接 axis =1）

# print(np.concatenate((arr3, arr4),axis=2))     #shape = 2，3，4 + 2，3，4 = 2，3，8


''' 4. pad 指定位置填充值，选择填充模式 '''
# 格式：np.pad(数组, 宽度, mode=模式)
# 1) 宽度是整数：上下左右填充宽度，具体值与模式有关
# 2) 宽度是：
arr2 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
print(np.pad(arr2, pad_width=1, mode='constant', constant_values='8'))

print('#' * 50)
print(np.pad(arr2, pad_width=2, mode='edge'))

print('#' * 50)
print(np.pad(arr2, pad_width=4, mode='maximum'))

print('#' * 50)
arr5 = np.array([[1, 1, 1], [1, 1, 1], [1, 1, 1]])
print(np.pad(arr5, pad_width=[(1, 1), (3, 3)], mode='constant', constant_values='6'))
# [(0, 0),(0, 1)]  = 上，下，左，右

print('#' * 50)
print(np.pad(arr5, pad_width=(0, 2), mode='constant', constant_values='6'))
# (2, 0)    上，左，下，右

arr6 = np.ones((3, 3, 3))
print(arr6.shape)
# print(arr6)
# print(np.pad(arr6, pad_width=1, mode='constant', constant_values='6'))
print('#' * 50)
print(np.pad(arr6, pad_width=[(0, 0), (0, 1), (0, 1)], mode='constant', constant_values='6'))
#                              上，下，上，下，左，右


''' 5. np.resize(数组，shape) 裁剪，可升维，降维 ，返回新数组
       把原数组拉平，填充 
       ndarray.resize()  在原数组进行裁剪 '''
print('#' * 50)
arr5 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])  # 3 * 3 = 9
# print(np.resize(arr5, new_shape=(2, 2, 2)))   # 8

print(np.resize(arr5, new_shape=(2, 2, 2, 3)))  # 24

print(np.resize(arr5, new_shape=(2, 2)))

# 数组对象打点进行 resize，size 不够时，填充0
print('#' * 50)
arr5.resize(4, 4)
print(arr5)

''' 6. split(数组，分割的数量必须axis的整除)'''
print('#' * 50)
arr6 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
print(np.split(arr6, 3, axis=0))

arr7 = np.random.randint(1, 5, (6, 4, 4, 8))
# print(np.split(arr7,2,axis=0))
print(np.split(arr7, 3, axis=0))

print('#' * 50)
print(np.dsplit(arr7, 2))
print('#' * 50)

arr8 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9], [7, 8, 9]])  # 4, 3
# print(np.hsplit(arr8,3))
# print('#' * 50)
print(np.vsplit(arr8, 2))
print(np.split(arr8, 2, axis=0))

print('#' * 50)
arr9 = np.arange(24).reshape(2, 4, 3)
# print(np.split(arr9,2,axis=0))
# print('#' * 50)
# print(np.vsplit(arr9,2))

print(np.split(arr9, 2, axis=1))
print('#' * 50)
print(np.hsplit(arr9, 2))

'''7 tile（数组，重复次数） 重复次数=整数或元组 '''
arr10 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])  # 3 * 3
# print(np.tile(arr10, 4))

print(np.tile(arr10, reps=(2, 2, 3)).shape)  # 元组对应 shape 相乘 (2, 2, 3) * (3, 3) = (2, 6, 9)

''' 8. repeat(数组, 重复次数, axis=轴)  指定轴重复'''
arr11 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
print('#' * 50)
# print(np.repeat(arr11,2, axis=0))
# print(np.repeat(arr11,3, axis=1))

# repaets=(轴的个数)
print(np.repeat(arr11, (3, 1, 2), axis=0))


m = np.zeros((8,8))
m[1::2 , ::2] = 1
m[0::2 , 1::2] = 1
print(m)
