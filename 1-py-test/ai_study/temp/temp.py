def two_num(arr, target):
    # 定义列表，保存所有的组合的数组和下标
    result = []
    save_num_index = {}
    for i, num in enumerate(arr):
        temp = target - num
        if temp in save_num_index:
            result.append({'nums': [temp, num], 'index': [save_num_index[temp], i]})
        save_num_index[num] = i
    return result


arr = [21, 11, 23, 25, 33, 28]
target = 44
print(two_num(arr, target))
