'''
安装：
pip install numpy -i https://mirrors.tuna.tsinghua.edu.cn/
'''

import numpy as np

''' 1. array 通过列表创建 numpy 数组 '''
arr2 = np.array([[1, 2, 3], [4, 5, 6]])
print(arr2)

''' 2. 创建值为 0 的数组 '''
print(np.zeros(10))  # [0. 0. 0. 0. 0. 0. 0. 0. 0. 0.]  一行10列

print(np.zeros((1, 4)))  # [[0. 0. 0. 0.]]

'''# 3. 创建值为 1 的数组 '''
# 格式： np.ones(size或shape)     size=整数，shape=(d1,d2,d3,..dn)
arr1 = np.ones(5)
print(arr1)

''' 4. 指定值创建数组 '''
# np.full(shape形状, fill_value=值, dtype=int64|float32)
arr2 = np.full((2, 3), fill_value=3.14)
print(np.full_like(arr2, fill_value=5))

''' 5. 创建未初始化的数组（第一次创建形状，值随机），值取决于内存的状态 '''
arr43 = np.empty((4, 3, 2))
print(np.empty_like(arr43))

''' 6. 指定 range 范围创建一维数组（range的范围的个数必须等于 size）  '''
# 格式：np.arange(开始，结束，步长)
print(np.arange(1, 10, 2))  # 返回：[1 3 5 7 9]
print(np.arange(1, 10, 2).reshape(5, 1))

'''
7. linspace 返回一维数组，将范围内[,]的数进行等份
# np.linspace(开始，结束，等分数)
'''
print(np.linspace(1, 10, 4))  # [ 1.  4.  7. 10.]

''' 
8. 随机生成数组，random 
# 1. randint 整数：[low, high)
'''
arr4 = np.random.randint(1, 9, (5, 5))  # [low,high)

# 2. uniform 浮点数：[low, high)
print(np.random.uniform(1, 4, (2, 4)))

# 3. random 浮点数，0 ~ 1之间
print(np.random.random((3, 4)))

# 4. np.random.seed()  # 种子数
np.random.seed(2)

# 5. shuffle 打乱原数组
np.random.shuffle(arr4)
# print(arr4)

# 6. normal 指定范围的正态分布
print(np.random.normal(0, 1, (2, 3)))

# randn(d1,d2,d3) 标准的正态分布
print(np.random.randn(2, 3, 4))

''' 9, 生成网格数 '''
# 格式：np.indices(d1,d2,d3,...,dn)
# 生成：shape = (n, d1,d2,d3,...,dn)
# np.indices(r, c)
# shape(2个数, r, c) -- 三维 (0轴把几个维度相加)
print(np.indices((2, 3)))  # shape = (2, 2, 3)

# --------------------------------------------
# --------------------------------------------


''' 1. 整数索引 ：ndarray[索引值] '''
arr = np.arange(24).reshape(3, 2, 4)
print(arr.shape)  # (3, 2, 4)
print(arr.size)  # 24

''' 2. 切片 ：ndarray[起始: 结束: 步长, 1轴, :, ] '''
# 起始默认是 0，结束默认 len，步长默认 1
print(arr[:2])
print(arr[::2])
print(arr[:, 0, :])
print(arr[1:3, :, ::2])

# 三个点只能出现一次，简化多维
# print(arr[..., 1::2])
print(arr[:, :, 1::2])

''' 3. 花式索引 ：与切片索引不同的是，索引数组可以不连续 '''
# ndarray[索引数组1, 索引数组2,...]
arr1 = np.arange(9)
print(arr1)  # [0 1 2 3 4 5 6 7 8]

index1 = [[1, 2]]
print(arr1[index1])

print(arr1[[1, 3, 4]])

index2 = [[1, 3, 4], [2, 3, 1]]
print(arr1[index2])

"""
花式索引
* 相当于复制 (前面复制的是 0 轴，后面复制的是 1 轴)
* [1, 2]  一维相当于单选：先从 0 轴选，再从 1 轴选
* [[1, 2], [2, 3]] 二维或多维相当于多选：先从 0 轴选多个，再从 1 轴选多个
"""

arr4 = np.arange(120).reshape(2, 3, 4, 5)
# print(arr4[:, [[1, 0, 1]], :, 4].shape)     # (1, 3, 2, 4)
print(arr4[:, [[1, 0, 1], [1, 2, 1]], ...].shape)  # (2, 2, 3, 4, 5)

# ---------------------------------------
#
# 广播规则应用
# https://yuanbao.tencent.com/chat/naQivTmsDa/4b345f7e-2c2e-47f9-8022-5430f938527c
# 这个解释比较仔细
#
# 处理高级索引的广播
# https://chat.deepseek.com/a/chat/s/4f642c25-4af1-408d-b800-f992875b646f
#
arr4 = np.arange(480).reshape(4, 6, 5, 4)
result = arr4[:, [[5, 3, 2, 4], [5, 3, 2, 4]], :, [3, 2, 1, 0]]
print(result.shape)  # 输出: (2, 4, 4, 5)

# 下面运算结果
# 4 (1,4) 5 (2,2,4)
# 4 (1,1,4) 5 (2,2,4)
# (2,2,4) 4 5
arr4 = np.arange(480).reshape(4, 6, 5, 4)
print(arr4[:, [5, 3, 2, 4], :, [[[1, 2, 3, 0], [1, 2, 3, 0]], [[1, 2, 3, 0], [1, 2, 3, 0]]]].shape)

# 高级索引规则
# https://yuanbao.tencent.com/chat/naQivTmsDa/a0132292-8ddd-4bb6-9fe1-478e298dfe8a
#
# 混合索引规则
# https://chat.deepseek.com/a/chat/s/f1b06b3d-390e-4a2d-a431-1c28b4a262f3
#
# 下面运算结果
# 4, (2, 4), 5, 4
# 4, 6, 5, 4
# 4, 2, 4, 5, 4 # 高级索引 (2, 4) 直接插入到 1 轴 (6)
arr4 = np.arange(480).reshape(4, 6, 5, 4)
print(arr4[:, [[5, 3, 2, 4], [5, 3, 2, 4]], :, :].shape)

# ----------------------------------

arr3 = np.arange(36).reshape(2, 3, 2, 3)
print(arr3)
print('#' * 100)
print(arr3[:, [1, 0, 2], [0, 1, 0], :].shape)  # (2, 3, 3)
print(arr3[:, [1, 0, 2], [0, 1, 0], :])
