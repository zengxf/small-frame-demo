''' random 随机模块 '''
import random
import sys

print(dir(random))
print(len(dir(random)))

# 1. randint(a, b)  闭区间 [a, b]，随机取一个整数
print(random.randint(1, 10))

# 2. uniform(a, b)  闭区间 [a, b]，随机取一个浮点数
print(random.uniform(0, 1))

# 3. random()       左闭右开 [0, 1)  浮点数
print(random.random())

# 4. randrange(起始0，结束，步长1)   左闭右开
print([random.randrange(1, 10, 2) for _ in range(10)])

print('-' * 20)
# 5. seed(整数) 种子数，默认当前系统的时间戳 秒 + 6 位
# 如果种子数相同，随机序列一样的。（训练模型）
random.seed(1)
print([random.random() for _ in range(5)])
# [0.13436424411240122, 0.8474337369372327, 0.763774618976614, 0.2550690257394217, 0.49543508709194095]

random.seed(1)
print([random.random() for _ in range(5)])
# [0.9560342718892494, 0.9478274870593494, 0.05655136772680869, 0.08487199515892163, 0.8354988781294496]


# 5. sample(序列, k=整数)    k 不能超过序列的长度，序列中元素被抽到的概率一样
print(random.sample([1, 2, 3, 4, 5, 6, 7, 8], k=5))

print('-' * 20)
random.seed()
# 6. choice(序列)     从序列中随机取一个，返回元素
print(random.choice([1, 2, 3, 4, 5, 6, 7, 8]))

print('-1' * 20)
# 7. choices(序列, weights=序列长度一致, k=整数)
print(random.choices([1, 2, 3, 4, 5, 6, 7, 8]))  # 随机抽取一个元素返回列表
print(random.choices([1, 2, 3, 4, 5, 6, 7, 8], k=20))  # 元素可以重复抽取
print(random.choices(['a', 'b', 'c', 'd'], weights=[2, 4, 1, 1], k=10))
# weights=[1, 1, 1, 1] 抽到的 a,b,c,d 的概率一样 1/4

print(random.choices(['a', 'b', 'c', 'd'], weights=[1, 2, 3, 4], k=20))
# weights=[1, 1, 1, 1] 抽到的 a,b,c,d 的概率不同 a=1/10, b=2/10, c=3/10, d=4/10

# print(random.choices(['a', 'b', 'c', 'd'], cum_weights=[-1, 1, 2, 3], k=10))
# cum_weights = [sum(weights[:i + 1]) for i in range(len(weights))]

print('-2' * 20)
# 8. shuffle(序列)    在原序列中随机打乱
lst = [1, 2, 3, 4, 5, 6, 7, 8]
random.shuffle(lst)
print(lst)

# 9. gauss(均值, 标准差)  高斯，正态分布，68、95、99
print(random.gauss(0, 1))

# sys 模块，os系统交互
import sys

print(sys.platform)  # 查看os系统平台：win、linux、mac

# sys.exit()
name = 'zhangsan'
print(sys.getsizeof(name))  # 获取对象占用的内存大小

print(sys.version)
