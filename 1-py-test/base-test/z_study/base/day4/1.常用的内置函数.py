'''
1. len(对象)
2. max(迭代序列, key=内置函数) 最大
3. min(迭代序列，key=内置函数) 最小
4. sum(迭代序列)    求和
5. any(迭代对象)    迭代对象中元素全部为 False, 返回布尔值 False
6. all(迭代对象)    迭代对象中元素全部为 True，返回布尔值 True
'''

s = 'hello'
print(len(s), max(s), min(s))

l = [1,2,3,4,5]
print(max(l), sum(l), min(l))

# 删除 lst = [0.5, 0.8, -0.2, 0.6, -0.7] 中绝对值较大的元素（提示三元 + 海象 + max）
lst = [0.5, 0.8, -0.2, 0.6, -0.9]
# print(max(lst, key=abs))
# lst.remove(max_num) if (max_num:= max(lst, key=abs)) else lst.remove(-max_num)

if (max_num:= max(lst, key=abs)):
    lst.remove(max_num)
print(lst)

print(sum([1,2,3,4], 10))
# 10 + sum([1,2,3,4]  ==> 1+2+3+4
print(sum(sum([[1],[2],[3],[4],[5]], [])))

# [] + sum([[1],[2],[3],[4],[5]]  ==> [] + [1]+[2]+[3]+[4]+[5]

sum([1,2])
# sum([[1],[2]])  列表求和

print(all([1,2,3,4,5,0]))
print(any([False,[],(),{},set(),None,0]))


# 输入一个数字判断是否为质数：11，使用 all、any
# 11: 2 ~ 10 , 每次除的结果保存列表，使用 all、any
num = 10
lst = []
for i in range(2, num):
    lst.append(num % i)
# print(lst)
print(all(lst))

# flag = True
# for i in range(2, num):
#     if num % i != 0:
#         flag = True
#         lst.append(flag)
#     else:
#         flag = False
#         lst.append(flag)
# print(all(lst))


# sum([1,2,3])  ==> 0 + 1 + 2 + 3
# sum([[1],[2],[3]])  ==> 0 + [1] + [2] + [3]
# print(sum([[1],[2],[3],[[4]]], [123]))  #  ==> [123] + [1] + [2] + [3]


l = []
if isinstance(l, list):
    l.append(1)
print(l)
