"""
内置模块 random

random 模块主要用来生成随机数。使用前需要 import random 导入该模块，
"""
import random

''' 1. randint(a, b) 生成 [a , b] 之间的整数，包含边界 a 和 b，a 和 b 为整数 '''
print(random.randint(1, 10))  # 2

''' 2. random() 生成的时 [0, 1) 之间的浮点数，包含 0 不包含 1 '''
print(random.random())  # 0.8341704091457043

''' 3. randrange(起始, 结束, 步长) 生成[起始, 结束) 之间的整数，步长默认为 1 '''
print(random.randrange(1, 10))  # 6
print(random.randrange(1, 10, 2))  # 1 3 5 7 9
print(random.randrange(10, 1, -2))  # 10 8 6 4 2

''' 4. uniform(a, b) 生成 [a, b] 之间的浮点数 '''
print(random.uniform(1, 5.6))  # 1.1392305673053071


''' 5. sample(序列, 指定长度) 随机抽取序列（如列表、元组等）中指定长度的独立元素，返回列表 '''
lst = ['a', 'b', 'c', 'd', 'e', 'f']
print(random.sample(lst, 4))  # ['d', 'f', 'c', 'b']

''' 6. shuffle(列表) 随机将原列表的元素打乱（训练模型）'''
print('原列表的元素：', lst)  # ['a', 'b', 'c', 'd', 'e', 'f']
random.shuffle(lst)
print('打乱后的元素：', lst)  # ['c', 'd', 'b', 'e', 'f', 'a']

''' 7. seed(数字) 随机种子数，默认当前时间戳。如果设置种子数相同，随机生成的序列数就相同 '''
random.seed(1)
print('设置种子数为 1，生成随机数', random.random())  # 0.13436424411240122
random.seed(1)
print('种子数相同时 1，再次生成随机数', random.random())  # 0.13436424411240122
