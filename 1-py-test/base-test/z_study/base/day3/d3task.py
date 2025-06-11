# 1. 将内容 "13510311528,男,28,2024"，转换成列表。
arr = "13510311528,男,28,2024".split(',')
print(arr)

# 2. 有一个元组 t = ('w',('pycharm','python','anaconda'),['cn','com','org']) 输出 www.python.org
t = ('w', ('pycharm', 'python', 'anaconda'), ['cn', 'com', 'org'])
print(t[0] * 3, t[1][1], t[2][1], sep=".")

# 3. 有一个列表是 lst = [1,2,3,4,5]，要求输出为 ['a','b','c',4,5] 怎么做？
lst = [1, 2, 3, 4, 5]
lst[0] = 'a'
lst[1] = 'b'
lst[2] = 'c'
print(lst)

# 4. 有一个列表是 l = [21,33,29,20,18,24,9]，取出里面最大的三个数
l = [21, 33, 29, 20, 18, 24, 9]
l.sort(reverse=True)
print(l[0:3])

# 5. 请将内容 ['a','b','c','d','e'] ，输出为 'abcde'
print(''.join(['a', 'b', 'c', 'd', 'e']))

# 6. 已知列表字典：
dict1 = {"code": 0,
         "data": [{"name": "吕布", "age": 22, "hurt": 2400, "login": "2021-04-01",
                   "love": [{"2012": "girl", "2014": "boy"}]},
                  {"name": "鲁班", "age": 22, "hurt": 2500, "login": "2021-04-27"},
                  {"name": "貂蝉", "age": 18, "hurt": 2800, "login": "2022-05-11"},
                  {"name": "亚瑟", "age": 38, "hurt": 2400, "login": "2023-04-07"},
                  {"name": "李白", "age": 28, "hurt": 2600, "login": "2022-07-13"}],
         'count': 5}
# 1) 从 data 中提取值，输出： "鲁班, 38, girl"
data = dict1['data']
str = ", ".join([data[1]["name"], str(data[3]["age"]), data[0]["love"][0]["2012"]])
print(str)

# 2) 向 data 这个字典中增加一个字段 msg，且内容为空
dict1['msg'] = ''
print(">", dict1['msg'], "<")

# 3) 请将 data 中的数据按 hurt 升序排序。
data.sort(key=lambda d: d['hurt'] if d['hurt'] is not None else float('-inf'))
print(data)

# 4) 向字典 data 数据增加 {"name": "鲁班", "age": None, "hurt": None, "login": "2021-04-27"}
data.append({"name": "鲁班", "age": None, "hurt": None, "login": "2021-04-27"})
print(data)

# 5) 再对 dict1 按 age 年龄排序（注意 None 的处理）
data.sort(key=lambda d: d['age'] if d['age'] is not None else float('-inf'))
print(data)

# 2. 计算出 1 ~ 100 之间所有能被 3 整除的整数的和
sum = 0
for d in range(1, 101):
    if d % 3 == 0:
        sum += d
print(sum)
