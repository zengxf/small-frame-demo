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

# 关键规则：配对花式索引
# https://chat.deepseek.com/a/chat/s/37f7c121-b69a-469a-be97-260dea64e214
#
# 当两个花式索引列表（[1, 0, 2] 和 [0, 1, 0]）在同一个索引操作中出现时：
# 它们会配对组合，而不是独立作用。
# 实际选取的索引对是：(1,0), (0,1), (2,0)（按位置配对）。
# 这合并了原始的第二维和第三维，生成一个新的单一维度，大小为花式索引列表的长度（即 3）。
#
# 最终形状计算
# 第一维：全选 → 大小 2。
# 合并后的新维度：花式索引配对生成 → 大小 3。
# 最后一维：全选 → 大小 3。
# 因此，输出形状为 (2, 3, 3)。
#
arr3 = np.arange(36).reshape(2, 3, 2, 3)
print(arr3)
print('#' * 100)
print(arr3[:, [1, 0, 2], [0, 1, 0], :].shape)  # (2, 3, 3)
print(arr3[:, [1, 0, 2], [0, 1, 0], :])

# 整数数组索引的广播配对
# https://chat.deepseek.com/a/chat/s/37f7c121-b69a-469a-be97-260dea64e214
# https://yuanbao.tencent.com/chat/naQivTmsDa/de25380e-c659-44bf-a2c4-1878e05632fc
#
# [1, 0, 2]（花式索引）
# 作用于 axis1（索引1）
# 索引列表长度 = 3，会生成一个新维度
# [0, 1, 0]（花式索引）
# 作用于 axis3（索引3）
# 索引列表长度 = 3，会生成一个新维度
#
# 关键规则：花式索引的广播
# 两个花式索引 [1, 0, 2] 和 [0, 1, 0] 长度相同（均为3），因此直接配对组合：
# 这会在结果中合并为一个新维度，大小为花式索引的长度 3
#
# 切片维度 axis0 和 axis2 被保留，但置于花式索引生成的维度之后
#
arr3 = np.arange(36).reshape(2, 3, 2, 3)
print(arr3[:, [1, 0, 2], :, [0, 1, 0]].shape)  # (3, 2, 2)

'''
6. np.argsort  排序后返回索引值

ref: https://chat.minimaxi.com/?type=chat&chatID=389509790736633857

排序后的索引
'''
# 多维数组
a = np.array([[3, 1, 2],
              [9, 7, 8]])

# 对每一行进行排序，并获取索引
sorted_indices = np.argsort(a, axis=1)
print("排序后的索引:\n", sorted_indices)
# 输出:
# 排序后的索引:
# [[1 2 0]
#  [2 1 0]]
# 使用索引重新排列数组
sorted_a = np.take_along_axis(a, sorted_indices, axis=1)
print("排序后的数组:\n", sorted_a)
# 输出:
# 排序后的数组:
# [[1 2 3]
#  [7 8 9]]

# ------------------------------
'''
2. np.sort 默认按最后一维排序

https://chat.minimaxi.com/?type=chat&chatID=389581149328183300
'''
np.random.seed(1)
arr3 = np.random.randint(1, 12, (3, 4))
print(arr3)
print("*" * 20)
# print(np.sort(arr3))  # axis=-1  只对最里面进行排序
# print(np.sort(arr3, axis=0))  # 对列进行排序
# print(np.sort(arr3, axis=None))  # 展平排序

# ----------------------------------
#
# 多维数组 是怎么排序的
# https://chat.minimaxi.com/?type=chat&chatID=389583517784539139
arr3 = np.array([[[3, 1, 4],
                  [1, 5, 9]],

                 [[2, 6, 5],
                  [9, 2, 7]]])

sorted_arr3_axis0 = np.sort(arr3, axis=0)
print("原始数组:\n", arr3)
print("沿着轴=0排序后:\n", sorted_arr3_axis0)

# ----------------------------------
#
#
''' 条件1：第一个列必须与第二个行，相等，返回（第一个行, 第二个的列）'''
# shape1=(行, 列)    @ shape2=(行, 列) = shape(1行, 2列)
arr1 = np.array([[1, 2, 3], [4, 5, 6]])
print(arr1.shape)  # (2, 3)
arr2 = np.array([[1, 2, 3, 4], [4, 5, 6, 8], [7, 6, 8, 9]])  # （3, 4)
print(arr2.shape)  # (3, 4)
print(arr1 @ arr2)

# ----------------------------------
# 多维矩阵相乘
#
# 广播解释的好
# https://chat.minimaxi.com/?type=chat&chatID=389592300996861958
# 只有一个明细示例
# https://yuanbao.tencent.com/chat/naQivTmsDa/4bf83ae0-ff0c-4e73-9f67-84687ea7a0d5
#
# 解释得更详细
# https://chat.deepseek.com/a/chat/s/70c03cb7-ac4c-4441-b6dc-99cefbec08b8
''' 条件2：多维矩阵相乘：必须满足（1列=2行），shape=(剩余的必须满足广播, 1行，2列)'''
arr3 = np.arange(1, 25).reshape(1, 2, 3, 4)
print(arr3)
print("*" * 20)
arr4 = np.arange(1, 13).reshape(3, 1, 4, 1)
print(arr4)
print("*" * 20)
arr6 = arr3 @ arr4
print(arr6)
print(arr6.shape)

# ------------------------------------
#
#
''' 4. pad 指定位置填充值，选择填充模式 '''
# 格式：np.pad(数组, 宽度, mode=模式)
# 1) 宽度是整数：上下左右填充宽度，具体值与模式有关
# 2) 宽度是：
arr2 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
arr5 = np.array([[1, 1, 1], [1, 1, 1], [1, 1, 1]])
print(np.pad(arr5, pad_width=[(1, 1), (3, 3)], mode='constant', constant_values='6'))
# [(0, 0),(0, 1)]  = 上，下，左，右
