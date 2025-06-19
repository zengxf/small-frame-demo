"""
语法：
[表达式 for 变量A in 可迭代A for 变量B in 可迭代B]

等效逻辑：
result = []
for 变量A in 可迭代A:
    for 变量B in 可迭代B:
        result.append(表达式)
"""
matrix = [[1, 2], [3, 4]]

flat1 = [num for
         row
         in matrix
         for num
         in row]
# [1, 2, 3, 4] (先左后右)

flat2 = [[num + 10 for num in row] for row in matrix]
# [[11, 12], [13, 14]] (先外后内)
