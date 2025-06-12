''' numpy 索引和切片： ndarray[索引和切片]'''
import numpy as np

#      0  1  2  3  4
lst = [1, 2, 3, 4, 5]
print(lst[3])
print(lst[1: 4])

#             0                 1
lst1 = [[1, 2, 3, 4, 5], [11, 12, 13, 14, 15]]
print(lst1[1][::2])

# shape = (d1, d2, d3, ..., dn)    n维
# axis =   0,   1 , 2, ..., n-1    轴

''' 1. 整数索引 ：ndarray[索引值] '''
arr = np.arange(24).reshape(3, 2, 4)
print(arr.shape)  # (3, 2, 4)
#                    axis= 0  1  2
#                 时间  面  层  行  列
print(arr.size)  # 24

# axis=0 轴上的索引值
print(arr[1])
print(arr[2][1])
print('#' * 100)

''' 2. 切片 ：ndarray[起始: 结束: 步长, 1轴, :, ] '''
# 起始默认是 0，结束默认 len，步长默认 1
print(arr[:2])
print('#' * 100)

print(arr[::2])
print('#' * 100)

# 切片索引时，可以使用逗号，对轴上进行切片
# shape =（3，2，4）
print(arr[1, 1])  # [12 13 14 15]

print(arr[1, 1, 2])  # 1 层第 1行，取2列

print(arr[:, 0, :])

print('#' * 100)
print(arr[1:3, :, ::2])
print('#' * 100)
# 三个点只能出现一次，简化多维
# print(arr[..., 1::2])
print(arr[:, :, 1::2])

# shape =（2，3，4，5，6，7）
#       [:, :2, ..., :3, :]

# 切片索引[: , :, :]  连续，1:2 ,  0:1:2
''' 3. 花式索引 ：与切片索引不同的是，索引数组可以不连续 '''
# ndarray[索引数组1, 索引数组2,...]
print('#' * 100)

arr1 = np.arange(9)
print(arr1)  # [0 1 2 3 4 5 6 7 8]
index1 = [[1, 2]]
print(arr1[index1])
print('#' * 100)

print(arr1[[1, 3, 4]])

index2 = [[1, 3, 4], [2, 3, 1]]
print(arr1[index2])

print('#' * 100)
# print(arr1[[[2, 4, 6], [1, 1]]])
# 每个索引数组必须 shape 相同的


arr2 = np.arange(6).reshape(2, 3)
print(arr2)
# [[0 1 2]
#  [3 4 5]]
print('#' * 100)
index3 = [[1, 0, 1]]
index4 = [1, 0, 1]  # ==> [[1, 0, 1]]
# print(arr2[[1, 0, 1], [1, 0, 1]])

print(arr2[index3, index4])
# 1.shape = (2,3)  二维
# 2.索引数组的个数不能超过维度 shape
# 3.索引数组可以多维，如果多个索引数组，维度必须是一致（特殊广播机制）
# 4.广播机制，列数相同（特殊整数-）  2，3

print(arr2[index3, 1])  # 1==》 [[1, 1, 1]]，后：列 0 1 2
print(arr2[index3, 0])  # 0==》 [[0, 0, 0]]，
print(arr2[index3, 2])  # 2==》 [[2, 2, 2]]，

print('#' * 100)
print(arr2[0, index3])  # 0==》 [[0, 0, 0]]，前：行 0 1
print(arr2[1, index3])

print('#' * 100)
arr3 = np.arange(60).reshape(4, 3, 5)
# 索引数组的个数 3
print(arr3.shape)  # (4, 3, 5)
# print(arr3[[1, 2, 0]])                # 单个索引，axis=0 取 1，2，0
# print(arr3[[1,2], [0,2]])              # 2个 索引，对应轴取 10, 22
# print(arr3[[1, 0, 1], [2, 1, 0], [3, 2, 4]])  # 3个索引，对应轴取 123，012，104
# print(arr3[[1],[2],[1],[3]])        # IndexError 索引数组个数4超过维度3


arr = np.arange(360).reshape((4, 3, 5, 6))
# print(arr)
print(arr[..., [0], : ].shape)


shape=(5,4,6,3,6,8)

# [:, [1, 0], ..., 1, :]      # shape =
# [:, [1, 0], ..., [1, 1], :]

# [[1, 0], ..., 1, :]         # shape =

arr6 = np.random.randint(1,5,(5,4,6,3,6,8))
# 花式索引：shape计算 6 维
print(arr6[:, [1, 0], ...].shape)
# 1.单个索引数组 axis=1，对该轴计算个数，shape，1轴变   （5，2，6，3，6，8）

print(arr6[:, [1, 0], ..., 1, :].shape)
# 2. 多个索引数组，不变的轴(0,2,3,5)，shape后面放  (变轴2个数= （2 ,5,6,3,8)
# 0, 1, 3, 5      2,4                 (3, 2 5,4,3,8)
print(arr6[:, :, [[1, 0], [1, 1], [1,2]], ..., 1, :].shape)
  # 0,1,3,5 轴没有变 (5,4,3,8)         (1,2,5,4,3,8)
  # 4 轴上 1，广播与 2 轴shape，值 1 （不能超过6）
  # 2，4 数组维度，外面括号里面有几个元素len(index_arr)，升维就是几，在看里面元素的个数len(index_arr[0])，当前维就是 2


arr4 = np.random.randint(1,4,(4, 3, 5, 6))
print(arr4[[[1,3]], 1, :].shape)
# [[1,1]]

print(arr4[[[1,3],[1,3],[1,3]], 1, :].shape)
# [[1,1],[1,1],[1,1]]

print(arr4[[[1,3,2],[1,3,2],[1,3,2]], [2], :].shape)
# [[1,0],[1,0],[1,0]]



