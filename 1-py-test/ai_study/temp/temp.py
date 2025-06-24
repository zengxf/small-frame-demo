lst = [0.5, -0.8, -0.2, 0.6, -0.7]
lst.remove(max_num) \
    if (max_num := max(lst, key=abs)) \
    else print('err')
print(lst)  # [0.5, -0.2, 0.6, -0.7]
