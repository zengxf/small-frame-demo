'''
def 定义函数():
    return  一个值,多个值,不能使用多个 return
'''
from traceback import format_list

# 不需要使用 def 定义, 使用 lambda 关键字, 匿名函数，做简单运算，即用即仍。
''' 格式: lambda *args: 表达式 
    意思: 把表达式的结果的就是 lambda 函数的返回值, 表达式可以嵌套 if else三元\ for 推导式, 返回单行 '''
''' 
1. lambda 函数当做参数赋值给变量, 通过变量使用
    变量名 = lambda *args: 表达式 
    变量名()   调用lambda函数
'''
# 案例: 使用 lambda 可以自定义一个求数字本身幂次方,输入一个 4, 4**4
a = lambda x: x ** x  # x 是形参
print(a(10))

# 案例: 输入多个数字, 从数字中找大于 10 的数字, 返回列表
# lst = input('使用空格输入多个数字:').split()      # lst 中元素都是数字字符
# new_lst = lambda l : [int(i) for i in l if int(i)>10]
# print(new_lst(lst))


''' 2. lamabda 函数可以当作函数 def 返回值, return lambda 函数 '''


# 定义一个函数实现使用特殊符号,做脚本中控制台输出的一个分割内容, 打印输出 30#  20-
def print_(c):
    return lambda n: c * n


print(print_('-')(50))

# 1. 使用 lambda ，计算 v1 = [1,2,3,4,5] 和 v2 = [2,5,9,2,8] 的内积（位置相乘，再相加）
v1 = [1, 2, 3, 4, 5]
v2 = [2, 5, 9, 2, 8]
p1 = lambda x, y: sum(v1[i] * v2[i] for i in range(len(v1)))
print(p1(v1, v2))

p2 = lambda x, y: sum(i * j for i, j in list(zip(v1, v2)))
print(p2(v1, v2))

''' 3. lambda 函数, 当 None, 暂停时间, 统计日志 '''


# 案例 : 输入一个数, 计算因子数, 使用 lambda 只记录因子
def fun(num):
    lst = []
    lambda_fun = lambda x: (lst.append(x), print(f'因子数: {x}'))
    for i in range(1, num + 1):
        if num % i == 0:
            # 调用 lambda 函数实现, lst.append(i)
            lambda_fun(i)  # 函数调用
    return lst


print(fun(10))

''' 4. lambda 函数当参数使用, 结合内置函数 sort  sorted  filter max|min|sum  any|all  map  reduce '''
lst = [('a', 22), ('c', 32), ('b', 15), ('d', 12)]
lst.sort()  # 按列表中每个元素中的元组的第一个元素排序
print(lst)  # [('a', 22), ('b', 15), ('c', 32), ('d', 12)]

lst.sort(key=lambda x: x[1])
print(lst)  # [('d', 12), ('b', 15), ('a', 22), ('c', 32)]

list_data = [{'name': '宁采臣', 'age': 25, 'salary': 6000},
             {'name': '聂小倩', 'age': 24, 'salary': 4000},
             {'name': '燕赤霞', 'age': 32, 'salary': None},
             {'name': '姥姥', 'age': 45, 'salary': 8000},
             {'name': '黑山老妖', 'age': 45, 'salary': 8000}]

# 1. 年龄升序
list_data.sort(key=lambda d: d['age'])  # 默认按元素, key,  元素字典 {} lambda 参数字典
print(list_data)

# 2. 薪资降序
list_data.sort(key=lambda d: d['salary'] if d['salary'] is not None else float('-inf'),
               reverse=True)  # 默认按元素, key,  元素字典 {} lambda 参数字典
print(list_data)

##### python 内置函数
''' 1. all|any 判断函数, 与 lambda 函数常用的内置函数 '''
# 3. 使用 lambda，输入一个数，判断是否为质数  all/any
a = lambda x: True if all(x % i for i in range(2, x)) else False
b = lambda x: True if not any(x % i for i in range(2, x)) else False
print(a(11))
print(all([1,2,3,4,False]))

print([11%i for i in range(2,11)])

''' 2. sorted(可迭代对象, key=lambda函数, reverse=False) 排序函数, 返回排序后的结果 '''
new_list = sorted(list_data, key=lambda x: x['age'])
print(new_list)

''' 3. filter(函数, 可迭代对象) 过滤函数, 返回生成器对象, 过滤的内容 '''
filter_list = filter(lambda d: d['salary'] is None, list_data)
print(list(filter_list))  # [{'name': '燕赤霞', 'age': 32, 'salary': None}]

''' 4. map(函数, 可迭代对象) 映射函数, 返回 map 迭代器对象 
    可迭代对象 iterables: 可循环
    迭代器对象 iterator: 不可以循环,只能往前走
'''
# lst = ['1', '2', '3', '4']
# print(list(map(int, lst)))    # <map object at 0x000002C40F66B850>

# nums = map(int,input('输入多个值:').split())
# print(nums)

lst = ['1', '2', '3', '4', 5, 'a', 'h']
print(list(map(lambda x: ord(str(x)) if str(x).isalpha() else int(x), lst)))
# print(list(map(int, lst)))


''' 5. max|min(可迭代对象, key=lambda) 最大,最小 '''
list_data = [{'name': '宁采臣', 'age': 25, 'salary': 6000},
             {'name': '聂小倩', 'age': 24, 'salary': 4000},
             {'name': '燕赤霞', 'age': 32, 'salary': None},
             {'name': '姥姥', 'age': 45, 'salary': 8000},
             {'name': '黑山老妖', 'age': 45, 'salary': 8000}]

# 找出薪资最大的用户信息, max 返回的是 一个
print(max(list_data, key=lambda d: d['salary'] if d['salary'] is not None else float('-inf')))

print(list(filter(lambda d: d['salary'] == max(i['salary'] for i in list_data if i['salary'] is not None), list_data)))

''' 6. reduce(函数, 可迭代对象, 初值) 累积计算, 返回累积计算的结果 . 累加和\积 累积拼接字符串 '''
from functools import reduce

lst = [1, 2, 3, 4, 5, 6]
print(reduce(lambda x, y: x + y, lst))
print(reduce(lambda x, y: x + y, lst, 100))

# 计算阶乘: 5! = 5*4*3*2*1
print(reduce(lambda x, y: x * y, lst))


# 2. 使用 lambda，提取字符串 'abcd5#aa2dasd@dsd0%' 中的数字，输出整数520
s = 'abcd5#aa2dasd@dsd0%'
print(int(''.join(list(filter(lambda x:x.isdigit(), s)))))

# 5. 使用 lambda，给第 4 题的字典列表中，每个用户增加新的键 job，值为 job:ai
list_data = [{'name': '宁采臣', 'age': 25, 'salary': 6000},
             {'name': '聂小倩', 'age': 24, 'salary': 4000},
             {'name': '燕赤霞', 'age': 32, 'salary': None},
             {'name': '姥姥', 'age': 45, 'salary': 8000},
             {'name': '黑山老妖', 'age': 45, 'salary': 8000}]

new_dict = list(map(lambda d:{**d, 'job':'ai'}, list_data))
print(new_dict)
# **d 相当于去掉大括号 'name': '宁采臣', 'age': 25, 'salary': 6000
print(list_data)

# print(dict(map(lambda d:d.setdefault('job', 'ai') ,list_data)))  # 迭代器对象
# print(list_data)

