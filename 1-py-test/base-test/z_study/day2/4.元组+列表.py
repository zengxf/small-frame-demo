''' 元组:使用一对小括号,元素之间使用逗号分割 '''
# 不可变数据类型: int \ float \ str \ tuple \ bool
# 可变数据类型: list \ dict \ set

''' 元组属于不可变的数据类型, 不能修改(特殊: 当元素为可变类型时,可以修改,id不变) '''
# 1. 元组定义: tuple
t = (1, 2, 3, 4)
print(type(t))      # <class 'tuple'>

t1 = ()     # 空元组
print(type(t1), t1)

t2 = ('a',)         # 定义一个元素的元组时,注意元素后面有逗号
print(t2, type(t2))     # a <class 'tuple'>

t3 = (1, 3.14, 'a', 'hello', (1,2,3), [1,2,3], {1,2,3},{'name':'yoyo'})
#    int   float  str  str    tuple   list      set     dict
print(type(t3))     # <class 'tuple'>


''' 使用内置函数: tuple(可迭代对象) 
    可迭代对象(可循环): 字符串  元组   列表   字典   集合
'''
t4 = tuple()        # 空元组
print(type(t4))     # <class 'tuple'>

t5 = tuple('a')
print(t5)

t6 = tuple('python')
print(t6)           # ('p', 'y', 't', 'h', 'o', 'n')
            # 键       值
t7 = tuple({'name':'yoyo', 'age':20})        # 可迭代 dict, 返回 key
print(t7)       # ('name', 'age')


''' 2. 元组的索引和切片 与字符串额索引切片用法相同 '''
# 元组[索引值]         元组[起始: 结束: 步长]
t8 = (1, 3.14, True, 'hello', (1,2,3), [1,2,3], {1,2,3},{'name':'yoyo'})
#     0   1     2     3         4       5       6       7
#                                      -3       -2        -1
print(t8[3])
print(t8[-3])       # [1, 2, 3]
print(t8[1::2])     # (3.14, 'hello', [1, 2, 3], {'name': 'yoyo'})


''' 3. 元组的内置函数 '''
print(t8.count(1))      # 统计相同的元素个数
print(t8.index('hello',1,len(t8)))      # len 统计元素的个数



''' 列表: list 使用一对中括号 [] 
    列表可变的数据类型:  增加\删除\修改\查询
'''
l1 = []    # 6维
l2 = [1]
l3 = [1, 3.14, True, 'hello', (1,2,3), [1,2,3], {1,2,3},{'name':'yoyo'}]
print(type(l3))

l4 = list('abcd')
print(l4)           # ['a', 'b', 'c', 'd']

l5 = list({'name':'yoyo', 'age':20})
print(l5)           # ['name', 'age']

# 索引和切片 与 字符串\元组 一样
l6 = [1, 3.14, True, 'hello', (1,2,3), [1,2,3], {1,2,3},{'name':'yoyo'}]
print(l6[3])
print(l6[3][::2][1])











