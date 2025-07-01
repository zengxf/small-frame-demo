from functools import reduce
arr_2d = [[1, 2], [3, 4, 8], [5]]
flat_arr = reduce(lambda x, y: x + y, arr_2d, [])
print(flat_arr)
