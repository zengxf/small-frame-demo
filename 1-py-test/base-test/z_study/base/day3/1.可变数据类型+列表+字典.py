'''
1. 列表索引\切片
    lst[index]
    lst[start: end :step] '''

# 1. 列表的修改
lst = [1, 2, 3, 4, 5]
print(id(lst))
print(lst[3])

lst[3] = 10
print(lst, id(lst))

''' 
t = (1,2,[3,4])
print('元组t',t, id(t))
t[2][0] = 5
print('修改t',t, id(t))
'''

# 2. 添加列表的元素: append(对象) \ extend(可迭代对象) \ insert(索引位置, 对象)
l1 = []
l1.append('hello')  # 添加一个元素
l1.append((1, 2, 3))
l1.append(1)
print(l1)

l1.extend('python')  # 扩展可迭代对象
l1.extend({'name': 'li', 'age': 20})  # 键
print(l1)

l1.insert(3, 'abc')
print(l1)

# 3. 列表删除: remove(值), pop(索引值)
l2 = ['hello', (1, 2, 3), 1, 'abc', 'p', 'y', 't', 'h', 'o', 'n', 'name', 'age']
l2.remove('p')
print(l2)
# l2.remove('x')      # 如果不存在,抛出异常 ValueError
# print(l2)

l2.pop()  # 指定索引值删除元素, 默认删除最后一个索引 -1, 索引超过范围报错
print(l2)

l2.clear()  # 清空列表元素,返回空列表
print(l2)

# 4. 列表的拷贝( 对象.copy 属于浅拷贝 \ 深拷贝 )  创建新对象-id不同
# 深拷贝 : 完全独立, 与原对象无关
# 浅拷贝 : 外层独立, 内层共享 ID

lst = [1, 2, 3, ['name', 'li', 'age', 20]]
print(f'原列表     {lst}, id={id(lst)}')

l3 = lst.copy()
print(f'拷贝后的列表{l3}, id={id(l3)}')

# print(l3[3])        # ['name', 'li', 'age', 20]
# l3[3][1] = 'wang'
# print('修改l3的元素: li --> wang',l3)
# print('原列表lst', lst)
l3[1] = 10
print(l3, id(l3))
print(lst)
lst[3][2] = 100
print(lst)
print(l3)

# 5. 列表反转: reverse
lst1 = [1, 2, 3, 4, 5]
# print(lst1[::-1])
lst1.reverse()
print(lst1)

# 6. 列表排序 sort(key=自定义函数, reverse=False)  对原列表进行排序
l = [3, 2, 5, 3, 7, 6, 4, 2]
l.sort()  # 默认排序升序 reverse=False
l.sort(reverse=True)  # reverse=True 降序
print(l)

l4 = [('a', 20), ('d', 30), ('c', 10)]
l4.sort(key=lambda x: x[1])
print(l4)

# 7. 列表 + 列表.   zip打包列表,以最短的列表元素进行打包,元组对
l5 = [1, 2, 3, 4, 5]  # 5
l6 = ['a', 4, 'hello']  # 3
l7 = ['x', 'y', 'z', 'c']  # 4
print(list(zip(l5, l6, l7)))  # zip 对象: list\ tuple \ dict

l8 = [('c', 10), ('a', 20), ('d', 30, 40)]
a, b = zip(*l8)
print(a)
print(b)

l9 = [1, 2, 3, 4, 5]
x, y, *z = l9
print(x, y, z)

l8 = [('c', 10, 1, 2, 3), ('a', 20), ('d', 30, 40)]
a, b = zip(*l8)
print(a)
print(b)

################# 字典 dict
# 以键:值 对组成的元素,使用{}大括号
# d = {'key1':'value1', 'key2':'value2',...}
'''
1) 键必须是不可变的数据类型: 数字 \ 字符串 \元组
2) 值数据类型不限
3) 键可以重复, 重复键, 获取最后一个键的值
4) 如果没有键值, 空字典
'''
d = {'name': 'xiaoli', 'name': 'wang', 'age': 20}
print(d)

d1 = {}
d2 = {1: 'a', 2: 1234}
d3 = {'name': 123, 'age': [1, 2, 3, 4], 'sal': {'ai': 10000, 'bi': 2000}}
d4 = {(1, 2): [1, 2, 3, 4]}  # 通常不使用

d5 = {'name': 123, 'age': [1, 2, 3, 4], 'sal': {'ai': 10000, 'bi': 2000}}
print(d5)

# 使用内置函数 dict 创建字典
# 1. dict(关键字=值)
d6 = dict(a=1, b=2, c=3)
print(d6)

# 2. dict(元组对-元素成对)
d7 = dict([(1, 'x'), (2, 'y'), (3, 'z')])
d8 = dict([[1, 'x'], [2, 'y'], [3, 'z']])
print(d8)

l1 = [1, 2, 3]
l2 = [4, 5, 6]
print(dict(zip(l1, l2)))

print(dict.fromkeys('hello'))

print(dict.fromkeys('hello', 100))

# 3. 字典的索引:  字典[键]   键不存在 KeyError
print('#' * 50)
d6 = {'name': 123, 'age': [1, 2, 3, 4], 'sal': {'ai': 10000, 'bi': 2000}}
print(d6['age'])
# print(d6['a'])


d9 = d6.copy()  # 浅拷贝
print(d9['age'])  # [1, 2, 3, 4]
# d9['age'] = 100
# print(d9)
d9['age'][2] = 100
print(d9)
print(d6)

# 字典查找, keys \ values \ items
print(list(d9.keys()))  # 字典视图 : dict_keys(['name', 'age', 'sal'])
print(tuple(d9.items()))
print(list(d9.values()))  # [123, [1, 2, 100, 4], {'ai': 10000, 'bi': 2000}]

# 字典删除 : pop(键)      popitem()      clear()
print('#' * 50)
# d9.pop('name')     # 键不存在报错 KeyError
# print(d9)

print(d9.popitem())  # 返回删除后键值的元组对 ('sal', {'ai': 10000, 'bi': 2000})
print(d9)

d9.clear()  # 清空键值, 返回空

# 获取字典键的值: get(key,[value]) \ setdeafult
print('#' * 50)
d10 = {'name': 123, 'age': [1, 2, 3, 4], 'sal': {'ai': 10000, 'bi': 2000}}
print(d10.get('name'))
print(d10.get('names', 'yoyo'))  # names 不存在, 返回 None, 指定值返回 yoyo
print(d10)

print('#' * 50)
print(d10.setdefault('name'))  # 添加到原字典中
d10.setdefault('sex')  # sex 不存在,添加 None
d10.setdefault('names', 'yoyo')
print(d10)

# 字典的更新,添加
d10.update({'sex1': 'man'})
print(d10)

# * 解包元组或列表,   ** 解包字典
d11 = {'name': 'liu', 'age': 20}
print({**d11, 'sal': 1000})

l = [1, 2, 3]
print([*l, 4, 5, 6])

''' 练习 '''
# 1. 将内容 "13510311528,男,28,2024"，转换成列表。
s = "13510311528,男,28,2024"
print(s.split(','))

# 2. 有一个元组 t = ('w',('pycharm','python','anaconda'),['cn','com','org']) 输出 www.python.org
t = ('w', ('pycharm', 'python', 'anaconda'), ['cn', 'com', 'org'])
print(t[0] * 3 + '.' + t[1][1] + '.' + t[2][2])
print(t[0] * 3, t[1][1], t[2][2], sep='.')

# 3. 有一个列表是 lst = [1,2,3,4,5]，要求输出为 ['a','b','c',4,5] 怎么做？
lst = [1,2,3,4,5]
lst[:3] = ['a','b','c']
print(lst)

# 4. 有一个列表是 l = [21,33,29,20,18,24,9]，取出里面最大的三个数
l = [21,33,29,20,18,24,9]
# l.sort(reverse=True)        # 对原列表排序
# print(l[:3])
l.sort(reverse=False)        # 对原列表排序
print(l[-3:len(l)])

# 5. 请将内容 ['a','b','c','d','e'] ，输出为 'abcde'
l1 = ['a','b','c','d','e']
s = ''.join(l1)
print(s)

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
print(dict1['data'][1]['name'],dict1['data'][3]['age'],dict1['data'][0]['love'][0]['2012'])

# 2) 向 dict1 这个字典中增加一个字段 msg，且内容为空
dict1.setdefault('msg',None)
print(dict1)

# 3) 请将 data 中的数据按 hurt 升序排序。   # key=lambda x:x['hurt']
# 匿名函数 : lambda 形参 : 表达式
data1 = dict1['data']
data1.sort(key = lambda d:  d['hurt'])
print(data1)
# d 参数, 传 data 中的元素-字典 {"name": "鲁班", "age": 22, "hurt": 2500, "login": "2021-04-27"}

# 4) 向字典 data 数据增加 {"name": "鲁班", "age": None, "hurt": None, "login": "2021-04-27"}
data2 = dict1['data']
data2.append({"name": "鲁班", "age": None, "hurt": None, "login": "2021-04-27"})
print(data2)

# 5) 再对 dict1 按 age 年龄排序升序（注意 None 的处理）     # key=lambda x:x['hurt']
# None 在过滤时,必须先过滤

data3 = dict1['data']
data3.sort(key = lambda d:  d['age'] if d['age'] is not None else float('-inf'))
print(data3)

float('-inf')  # 负无穷
float('inf')   # 正无穷