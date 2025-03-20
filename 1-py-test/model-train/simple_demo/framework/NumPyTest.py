# ---------------------------
# 多维数组操作与矩阵运算
# ---------------------------

import numpy as np

i_arr = [1, 2, 3, 4]
print(i_arr)
print("------------------------------- 1")

# 从列表创建数组
arr = np.array(i_arr)  # 一维数组
print(arr)  # 输出: [1 2 3 4]
print("------------------------------- 2")

# 创建全零/全一数组
zeros_arr = np.zeros((2, 3))  # 2行3列全零矩阵
ones_arr = np.ones(5)  # 长度为5的全一数组
print(zeros_arr)  # [[0. 0. 0.], [0. 0. 0.]]
print("------------------------------- ")
print(ones_arr)  # [1. 1. 1. 1. 1.]

# 生成随机数组
print("------------------------------- 4")
random_arr = np.random.rand(2, 3)  # 2行3列的0~1均匀分布随机数
print(random_arr)

# 查看数组属性
print("------------------------------- 5")
arr = np.array([[1, 2], [3, 4]])
print("形状:", arr.shape)  # 输出: (2, 2)
print("维度:", arr.ndim)  # 输出: 2
print("元素总数:", arr.size)  # 输出: 4

# 数学运算
print("------------------------------- 6")
arr = np.array([1, 2, 3])
print(arr + 10)  # 每个元素加 10 → [11 12 13]
print(arr * 2)  # 每个元素乘 2 → [2 4 6]

# 统计函数
print("------------------------------- 7")
arr = np.array([1, 2, 3])
print("总和:", np.sum(arr))  # 6
print("均值:", np.mean(arr))  # 2.0
print("标准差:", np.std(arr))  # 0.8165

# 访问元素
print("------------------------------- 8")
arr = np.array([[1, 2, 3], [4, 5, 6]])
print(arr[1, 2])  # 访问第2行第3列 → 6

# 切片操作
print("------------------------------- 9")
arr = np.array([[1, 2, 3], [4, 5, 6]])
sub_arr = arr[0:2, 1:3]  # 截取前两行的第2~3列
print(arr)
print(sub_arr)  # [[2 3], [5 6]]

# 改变形状
print("------------------------------- 10")
arr = np.array([[1, 2, 3], [4, 5, 6]])
reshaped = arr.reshape(3, 2)  # 将2x3数组转为3x2
print(reshaped)  # [[1 2], [3 4], [5 6]]

# 拼接数组
print("------------------------------- 11")
arr1 = np.array([1, 2])
arr2 = np.array([3, 4])
concatenated = np.hstack((arr1, arr2))  # 水平拼接 → [1 2 3 4]
print(concatenated)

# 矩阵运算
print("------------------------------- 12")
matrix_a = np.array([[1, 2], [3, 4]])
matrix_b = np.array([[5, 6], [7, 8]])
dot_product = np.dot(matrix_a, matrix_b)  # 矩阵点乘
print(dot_product)  # [[19 22], [43 50]]

# 图像处理基础
print("------------------------------- 13")
# 模拟RGB彩色图像（3通道）
image = np.random.randint(0, 256, (100, 100, 3), dtype=np.uint8)
red_channel = image[:, :, 0]  # 提取红色通道[2,4](@ref)
print(image)
print("------------------------------- ")
print(red_channel)
