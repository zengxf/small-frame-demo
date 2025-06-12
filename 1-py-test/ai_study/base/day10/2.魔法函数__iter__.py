# 魔法函数 __iter__ 把对象当作迭代器使用、__next__ 取值
'''
lst = [1,2,3,4,5]
lst_iter = iter(lst)

for i in lst_iter:
    print(i)
'''

'''
class 类:

    def __init__(self, 形参):
        self.属性 = 形参
        self.index = 0

    def __iter__(self):
        return self

    def __next__(self):
        return res

实例对象迭代器使用 = 类('参数')

'''
''' 80. 数据批处理迭代器：在训练模型时，通常需要按批次处理数据，划分 batch 。
#     比如训练 data 为 [1 ~ 20]，每隔 batch_size 为 5 次输出一组数据 '''


# lst = list(range(1, 21))
# size = 5
# lst_iter = iter(lst)
# for _ in range(4):
#     print([next(lst_iter) for _ in range(5)])

# [1, 2, 3, 4, 5,     6, 7, 8, 9, 10,     11, 12, 13, 14, 15,    16, 17, 18, 19, 20]

class DataIter:
    def __init__(self, data, size=5):
        self.data = data
        self.size = size
        self.start = 0

    def __iter__(self):
        return self

    def __next__(self):  # 必须判断，大于长度抛出 StopIteration
        if self.start >= len(self.data):
            raise StopIteration
        res = self.data[self.start: self.start + self.size]
        self.start += self.size
        return res


data = list(range(1, 23))
print(data)
print(data[20:25])
print('*' * 20)

d = DataIter(data)
for i in d:
    print(i)
