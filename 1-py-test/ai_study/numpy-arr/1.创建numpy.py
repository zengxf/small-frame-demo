''' numpy 提供多维数组 '''

''' 安装：pip install numpy -i https://mirrors.tuna.tsinghua.edu.cn/ '''
'''
1. 索引和切片，花式索引、布尔索引、伪索引（轴理解）
2. 基础操作：算术运算、比较运算
   一元操作就是一个数组操作：max(arr1)
   二元操作就是二哥数组操作：arr1 + arr2
3.形状不变、值改变的
4.形状改变、值不变的
5.形状改变、值改变的
'''

# 1. 创建 numpy 数组：
''' 
1）标量就是常数： 3
2）向量就是列表：[1,2,3]
3）矩阵就是二维：[[1,2,3],[4,5,6]]
4）张量就是多维：[[[[[1]]]]]
'''

import numpy as np
''' 1. array 通过列表创建 numpy 数组 '''
# 格式： np.array(列表对象)
# arr1 = np.array([1,2,3])

list = [1,2,3]
arr2 = np.array([[1,2,3],[4,5,6]])
print(arr2)

arr3 = np.array([4,5,6], dtype=np.int8)   # int8-(-128~127)
print(arr3)

# numpy 创建 ndarray 的多维数组对象，
# dtype 数据类型 int64\float32
# shape 维度 =（1，2，。。。），1个数字1维，2个数字2维
# size 数组的元素的个数 = shape (内积) 2*3


''' 2. 创建值为 0 的数组 '''
# 格式 ： np.zeros(shape或size)        shape = （时间，面，层，行，列）形状大小
print(np.zeros(10))     # [0. 0. 0. 0. 0. 0. 0. 0. 0. 0.]  一行10列
print(np.zeros((1, 4)))     # [[0. 0. 0. 0.]]
print(np.zeros((2,3,2,3)))      # size = 2*3*2*3 = 36
# [[[[0. 0. 0.]
#    [0. 0. 0.]]
#
#   [[0. 0. 0.]
#    [0. 0. 0.]]
#
#   [[0. 0. 0.]
#    [0. 0. 0.]]]
#
#  [[[0. 0. 0.]
#    [0. 0. 0.]]
#
#   [[0. 0. 0.]
#    [0. 0. 0.]]
#
#   [[0. 0. 0.]
#    [0. 0. 0.]]]]

'''# 3. 创建值为 1 的数组 '''
# 格式： np.ones(size或shape)     size=整数，shape=(d1,d2,d3,..dn)
arr1 = np.ones(5)
print(arr1)

print(np.ones((3, 4)))


''' 4. 指定值创建数组 '''
# np.full(shape形状, fill_value=值, dtype=int64|float32)
arr2 = np.full((2,3),fill_value=3.14)

# full_like 匹配指定数组的维度创建新数组
# np.full_like(数组, fill_value=值)
print(np.full_like(arr2, fill_value=5))


''' 5. 创建未初始化的数组（第一次创建形状，值随机），值取决于内存的状态 '''
arr43 = np.empty((4,3,5))
# print(arr43)

# print(np.empty((2,3)))

print(np.empty_like(arr43))


''' 6. 指定 range 范围创建一维数组（range的范围的个数必须等于 size）  '''
# 格式：np.arange(开始，结束，步长)
print(np.arange(1,10,2))        # 返回：[1 3 5 7 9]

print(np.arange(1,10,2).reshape(5,1))

print(np.arange(1,10).reshape(3,3))
# [[1 2 3]
#  [4 5 6]
#  [7 8 9]]

''' 7. linspace 返回一维数组，将范围内[,]的数进行等份'''
# np.linspace(开始，结束，等分数)
print(np.linspace(1, 10, 4))        # [ 1.  4.  7. 10.]

print(np.linspace(1, 15, 7))
# 从 1 ~ 15 等分为 7 份


''' 8. 随机生成数组，random '''
# 1. randint 整数：[low, high)
arr4 = np.random.randint(1,9, (5,5))   # [low,high)
# [[8 1 7]
#  [8 4 6]]
# print(np.random.randint(1, 3,10))

# 2. uniform 浮点数：[low, high)
# print(np.random.uniform(1, 4,(2,4)))

# 3. random 浮点数，0 ~ 1之间
# print(np.random.random((3,4)))

# 4. np.random.seed()  # 种子数


# 5. shuffle 打乱原数组
np.random.shuffle(arr4)
# print(arr4)

# 6. normal 指定范围的正态分布
# print(np.random.normal(0,1,(2,3)))

# randn(d1,d2,d3) 标准的正态分布
# print(np.random.randn(2, 3, 4))


''' 9, 生成网格数 '''
# 格式：np.indices(d1,d2,d3,...,dn)   np.indices(r, c)
# 生成：shape = (n, d1,d2,d3,...,dn)   shape(2个数, r, c) -- 三维

print(np.indices((2, 3)))       # shape =(2, 2, 3)
# [[[0 0 0]
#   [1 1 1]]        行索引：0，1
#
#  [[0 1 2]
#   [0 1 2]]]       列索引：0  1  2

# 花式索引：
arr4 = np.arange(120).reshape(2, 3, 4, 5)
print(arr4[..., [1, 2, 3], :].shape)            # (2, 3, 3, 5)
# 1. 单个索引数组（某一个轴），该轴去改变大小，其他轴不变
# 对 axis=2 轴：【1, 2, 3】，值范围（0，1，2，3）
# shape=(2, 3, ?, 5)   len(索引数组)=3个   shape=(2, 3, 3, 5)

print(arr4[[1, 0, 1], ..., [1, 2, 3], :].shape) # (3, 3, 5)
# 对 axis=0 轴：【1，0，1】，值范围（0，1）
# 对 axis=2 轴：【1, 2, 3】，值范围（0，1，2，3）
# 2. 多个索引数组(一维)，不操作的轴 1，3， shape=( 3, 5)
# shape=(?, 3, 5)   len(索引数组)=3个   shape=(3, 3, 5)

print(arr4[:, [[1, 0, 1]], :, [1, 2, 3]].shape)     # (1, 3, 2, 4)
# 对 axis=1 轴：[[1, 0, 1]]，值范围（0，1）
# 对 axis=3 轴：[1, 2, 3]，值范围（0，1，2，3）   [1, 2, 3] 广播==》[[1, 2, 3]]
# 3. 多个索引数组（二维），不操作的轴 0，2， shape=(len([索引数组])=1，len(索引数组[0])=3, 2, 4)

print(arr4[:, [[1, 0, 1]], :, 4].shape)     # (1, 3, 2, 4)
print(arr4[:, [[1, 0, 1]], :, [3]].shape)     # (1, 3, 2, 4)
# 一个数，广播后与索引数组的 shape 一样，值为数字（axis=3 , 0,1,2,3,4）


print(arr4[:, [[1, 0, 1], [1, 2, 1]], ...].shape)
# 对 axis=1 轴：[[1, 0, 1]]，值范围（0，1, 2）
shape=(2, len([[1, 0, 1], [1, 2, 1]]), len([1, 0, 1]), 4, 5)
# shape=(2, '单个索引的shape', 4, 5) = (2, 2, 3, 4, 5)
print(arr4[:, [[[1, 0, 1], [1, 2, 1]],[[1, 0, 1], [1, 2, 1]]], :, [1, 1, 1]].shape)
# shape (2, 2, 3, 2, 4)
# [[[1, 0, 1],
#   [1, 2, 1]],
#
#  [[1, 0, 1],
#   [1, 2, 1]]]

# 2,2,3,2,4


