import numpy as np

np.random.seed(1)
arr3d = np.random.randint(1, 20, (2, 3, 4))

# print(arr3d)

# 指定 axis 轴进行操作
''' 1. min-最小, max-最大, mean-平均, sum-和
    格式：np.min(ndarray, axis=) 
         ndarray.min() '''

print('最小值：', np.min(arr3d))  # 返回拉平后最小值        # 2, 3, 4
print('axis = 0, 最小值：\n', np.min(arr3d, axis=0))  # shape = 3, 4

print('axis = 1, 最小值：\n', np.min(arr3d, axis=1))  # shape = 2, 4
print(arr3d.min(axis=1))

print('axis = 2, 最小值：\n', np.min(arr3d, axis=2))  # shape = 2, 3

''' 2. argmax、argmin 返回索引值 '''
print(np.argmax(arr3d))  # 返回拉平后最大值的索引值

print(np.argmax(arr3d, axis=0))  # 返回该轴上的索引值 0，1  （2，3，4）
print(arr3d.argmax(axis=0))

print(np.argmax(arr3d, axis=2))  # 0，1，2，3

''' 3. median-中位数：偶数中间2个/2,  '''
arr2 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])  # shape = 3*3
print(np.median(arr2))  # 排序后找中位数。奇数 5
print(np.median(arr2, axis=0))  # [4. 5. 6.]
print(np.median(arr2, axis=1))  # [2. 5. 8.]

arr43 = np.array([[1, 5, 2, 3], [4, 5, 2, 6], [7, 4, 8, 9]])  # 12 ，4，5
print(np.median(arr43))  # 排序后找中位数。偶数 中间2个数相加 /2 = 4.5
print(np.median(arr43, axis=1))  # 2.5, 4.5  7.5

''' 4. average-加权平均，weights 权重必须与数组的shape一样 '''
arr44 = np.array([[1, 5, 2, 3], [4, 5, 2, 6], [7, 4, 8, 9]])  # 3*4
#                [4.         4.66666667 4.         6.        ]
print(np.average(arr44, axis=0, weights=[[1, 1, 1, 1], [2, 1, 1, 1], [3, 1, 1, 1]]))
# 1 * 1/6  + 4 * 2/6  + 7 * 3/6 =  5

''' 5. var-方差：每个数与均值的加权平方和/数量, std-标准差：方差开方, cov-协方差'''
arr2d = np.array([[1, 2, 3], [4, 5, 6]])
print(np.var(arr2d))
print(np.var(arr2d, axis=0))    # ((1-(1+4)/2)**2+(4-(1+4)/2)**2) / 2


''' 6. bincount(数组, weights=权重) 统计非负整数出现的次数 '''
arr1 = np.array([1, 5, 4, 8, 1, 8, 20])
#               [0 2 0 0 1 1 0 0 2]
#                0 1 2 3 4 5 6 7 8 最大
print(np.bincount(arr1))


''' 7. prod 累积积'''
print('*'*50)
arr = np.arange(1, 13).reshape(2,3,2)
# print(arr)
print(np.prod(arr))   # 默认拉平
print(np.prod(arr, axis = 1))       # shape 2*2

# shape=(3,4,5)
# shape=(5) -- > 3,4,5
#
# [1,2,3] + 1  --> [1,2,3] + [1,1,1]
#
# 2,3   2,1
# [[1,2,3],[4,5,6]] + [[2],[3]]  --> [[1,2,3],[4,5,6]] + [[1,1,1],[1,1,1]]
# [[1,2,3],[4,5,6]] + [[2,2,2],[3,3,3]]