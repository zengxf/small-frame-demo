import os


all_files = os.listdir(r"F:\data_source\age_face\crop_part1")

result = []
for p in all_files:
    s = p.split('_')[0]
    result.append(int(s))
dd = set(result)
dd = list(dd)
dd.sort()
print(dd)
#
full_set = set(range(0, 111))
# # 缺失的数字: [0, 94, 97, 98, 102, 103, 104, 105, 106, 107, 108, 109]
missing_numbers = sorted(full_set - set(dd))
print("缺失的数字:", missing_numbers)

def age_to_group(age):
    """将年龄转换为分组标签（0-9），左闭右开区间"""
    age = int(age)  # 确保 age 是整数
    if age < 8:
        group = 0  # 0-7 岁 → 组 0
    elif 8 <= age < 16:
        group = 1  # 8-15 岁 → 组 1
    elif 16 <= age < 22:
        group = 2  # 16-21 岁 → 组 2
    elif 22 <= age < 30:
        group = 3  # 22-29 岁 → 组 3
    elif 30 <= age < 38:
        group = 4  # 30-37 岁 → 组 4 (补全缺失区间)
    elif 38 <= age < 48:
        group = 5  # 38-47 岁 → 组 5
    elif 48 <= age < 58:
        group = 6  # 48-57 岁 → 组 6
    elif 58 <= age < 68:
        group = 7  # 58-67 岁 → 组 7
    elif 68 <= age < 78:
        group = 8  # 68-77 岁 → 组 8
    elif 78 <= age < 88:
        group = 9  # 78-87 岁 → 组 9
    else:
        group = 10  # 88+ 岁 → 组 10 (原代码漏了88-110)
    return group


all_files = os.listdir(r"F:\data_source\age_face\crop_part1")
#
result = {x: 0 for x in range(11)}
for p in all_files:
    s = p.split('_')[0]
    ss = int(s)
    result[age_to_group(ss)] = result[age_to_group(ss)] + 1
print(result)
