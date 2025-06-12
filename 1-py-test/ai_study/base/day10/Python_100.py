
''' 21. 判断 {"name": "yoyo", "age": 20} 用户 yoyo 是否成年（年龄大于18岁），格式输出：{用户}是否成年 '''

''' 22. 输入密码，长度小于 6 为弱；否则字母开头，不含特殊字符为中；字母开头，含特殊字符为强（使用三元运算） '''

''' 23. 输入字符串，如果长度大于5，格式化输出：{字符串}长度为{长度}，否则提示长度不足5（使用三元运算+海象）'''

''' 24. 输入薪资，小于 8000 为初级、8000 ~ 15000 为中级、15000 以上为高级（使用三元运算+海象） '''

''' 25. 删除 lst = [0.5, 0.8, -0.2, 0.6, -0.7] 中绝对值较大的元素（提示三元+海象 + max） '''

###### 三、循环语句：range生成整数序列、for 循环、while 循环、break 退出循环、continue 退出当次循环 '''
''' 26. 输入一个包含 1 ~ n 中部分数字的列表（无重复），找出缺失的所有数字（如 [3,5] → [1, 2, 4]）'''
# range 用法，max，列表只能+，集合-
# nums = list(map(int, input('输入多个数字用空格分割：').split()))
# print(list(set(range(1, max(nums))) - set(nums)))


''' 27. 提取 www.python.org 中的每个单词首字母的大写，组成的新字符串 "WPO" '''
# 思路：
# 1. 把 www.python.org 字符串，分割转为列表
# 2. 定义变量保存提取的字符''
# 3. 循环从列表中取元素 www 、 python、 org
# 4. '' + 字符串索引[0]


''' 28. 输入一个列表 ['a', 'b', 'a', 'c']，返回每个元素出现的次数 {'a':2, 'b':1, 'c':1} '''
# 思路：利用字典，更新新值
# 1. 定义空字典，存放 'a':2, 'b':1, 'c':1
# 2. 循环从列表中取元素
# 3. 判断取出的元素，是否在字典中，利用字典索引 = 新值，不在初值，再更新值


''' 29. 统计数组 [[2,5,3,6,3], [1,3,5,1,8], [2,5,9,2,8]] 相同元素出现次数，以字典形式存储 '''

''' 30. 从 100 ~ 500 开始之间找出前 20 个质数，并计算他们的和（被除数/除数） '''
# 思路：做了 11 是否质数--> num = 11
# 可以保存到列表中，通过切片[:20]
# for num in range(100, 501):

''' 31. 利用循环生成二维数组 [[1, 2, 3], [4, 5, 6], [7, 8, 9]] '''
# [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
# 思路：
# 1. 定义空列表 【】
# 2. 外循环3次添加 3行  0，1，2
# 3. 定义空列表 【1，2，3】
# 4. 嵌套循环填充值，1，2，3    3*外+1
# 5. 循环中列表，添加到最外的列表


''' 32. 将二维数组 [[1, 2, 3], [4, 5, 6], [7, 8, 9]] 进行转置（行、列互换） '''
# 思路：
# 1. 定义空列表 【】
# 2. 循环确定外面列表中的元素个数 3   len([[1, 2, 3], [4, 5, 6], [7, 8, 9]])  i
# 3. 循环内的空列表 【1，4，7】
# 4. 内循环打印每个元素（列表）中又含几个元素  len([1, 2, 3]) j
# 5. 往内循环的列表中添加元素 [j][i]
# 6. 把内部的列表，添加外部列表


''' 33. 冒泡排序，对列表 [5, 3, 8, 6] 进行升序排序 '''
# 思路：
# 1. 循环取元素
# 2. 两两比较


''' 34. 杨辉三角：输入行数 n，生成前 n 行杨辉三角 '''
# 思路：1，   1，1
# 1. 利用列表的索引，直接加
# 2. 最后记得添加 1
#      1
#     1 1
#    1 2 1
#   1 3 3 1

''' 35. 有一个嵌套列表，将其中所有元素扁平化为一个列表，比如：[1,2,[3,4,5],6]  ==> [1,2,3,4,5,6] '''
# 思路：
# 1. 从列表中取元素
# 2. 判断元素是否为列表 isinstance(i, list)
#    取元素
# 3. 利用列表+


''' 36. 计算 [5,32,24,22,35,12] 整数数组中的最大值、最小值、及他们的最大公约数 '''
# 思路：
# 1. 取最大、最小值
# 2. 利用 while 最大值!=0:  最大，最小 = 最大%最小，最大


''' 37. 输入一个正整数，用 while 循环分离每个数，并求和（如 123 → 1+2+3=6） '''
# 1. 123 % 10  = 3
# 2. 12 % 10   = 2
# 3. 1 % 10 = 1
num = 1234
total = 0
while num:  # num 非零进入循环，等于零 False
    total += num % 10
    num = num // 10
print(total)

# lst = []
# for n in str(num):
#     lst.append(int(n))
# print(lst, sum(lst))

# total = 0
# for n in str(num):
#     total += int(n)
# print(total)


''' 38. 输入字符串，用 while 循环逐个字符反转 '''

''' 39. 字符串压缩，输入 "aaabbcccca"，输出压缩后的字符串 "a3b2c4a1"，若压缩后更长则返回原字符串 '''
# 思路：
# 1. 定义变量统计个数
# 2. 定义空字符存放结果  ''
# 3. 从字符串，循环取，
# 4. 判断 s[0] == s[1]，计数+1   else ：
#    '' + 元素+str(计数)


''' 40. 零钱兑换：硬币 coins = [1, 2, 5]，输入金额 amount，返回：{硬币：个数} '''
# 思路：
# amount = 13
# 1. 对零钱降序排序
# 2. 嵌套 for 取零钱，大
#     循环 while ：
#     amount - 最大 5   负数， amount // 5 = 整数个数
#     循环第二个 2


###### 四、推导式：元组、列表、字典、集合 '''
''' 41. 找出所有水仙花数3位（个位^3+十位^3+百位^3=本身），计算他们的和 '''
# 思路：100 ~ 1000
print(sum([num for num in range(100, 1000) if num == ((num // 100) ** 3 + (num // 10 % 10) ** 3 + (num % 10) ** 3)]))

print([b * 100 + s * 10 + g for b in range(1, 10) for s in range(0, 10) for g in range(0, 10) if
       b ** 3 + s ** 3 + g ** 3 == b * 100 + s * 10 + g])

''' 42. 从 text 文本中 "python pip,conda is anaconda pycharm."，提取长度为偶数的单词，返回到列表中 '''

''' 43. 将二维数组扁平化 [[2, -1, 3], [-5, 3, -2], [6, 2, 4]]，并过滤负值 '''
l = [[2, -1, 3], [-5, 3, -2], [6, 2, 4]]
# 思路：for 外面【】， 内 [2, -1, 3]  if 过滤
print([col for row in l for col in row if col > 0])

''' 44. 构造一个 3 * 3 的矩阵：[[1, 2, 3], [2, 4, 6], [3, 6, 9]] '''

''' 45. 统计 "python pip,conda is anaconda pycharm code." 每个单词首字母出现的次数，以字典形式返回 {字母：次数} '''
# {p:3, c:2, i:1, a:1} 使用集合推导式，去重键
s = "python pip,conda is anaconda pycharm code."
words = s.replace(',', ' ').split()
print(words)
dct = {}
for word in words:
    if word[0] in dct:
        dct[word[0]] += 1
    else:
        dct[word[0]] = 1
print(dct)

# print({word[0]:word.count(word[0]) for word in words})
print(list(word[0] for word in words))  # a = ['p', 'p', 'c', 'i', 'a', 'p', 'c']

print({c: list(word[0] for word in words).count(c) for c in list(word[0] for word in words)})

print({k: sum(1 for word in words if word.startswith(k)) for k in set(word[0] for word in words)})

###### 五、自定义函数 '''
''' 46. 输入一个字符串，找出字符串中出现次数最多的元素，返回到列表（若有多个返回所有） '''

''' 47. 检测密码强度，规则长度<6-->弱。长度≥6且包含字母和数字-->中。长度≥8，包含字母、数字和特殊符号-->强 '''

''' 48. 计算 2 个矩阵的乘积（行列相乘再相加）（乘积符合：m1的列与m2的行一致，返回m1的行，m2的列，
    如：2*3 @ 3*4 => 2*4） '''

''' 49. 输入 [[1,2],[3,4]] 返回转置后的矩阵 [[1,3],[2,4]] '''

''' 50. 找出 100 以内的卡普雷卡数，如：45 的平方 2025 = 20 + 25 '''

###### 六、lambda函数（sorted、filter、map、all/any、max/min、sum、reduce）'''
''' 51. 对 [-5, 3, -2, 1] 列表的元素，按绝对值升序排序 → [1, -2, 3, -5] '''

''' 52. 对字典列表的多级排序：[{'name':'yoyo','age':25}, ...] 按 age 降序、name 升序排序 '''

''' 53. 从列表 ["madam", "hello", "level"] 中筛选回文 → ["madam", "level"] '''

''' 54. 从元组列表 [(1,'a'), (2,'b')] 中提取第二个元素 → ['a','b'] '''

''' 55. 统计 ["hello", "python", "conda", "pycharm"] 列表中的元素，按长度分类成字典。如：{1: ...,2: ...} '''

''' 56. 实现递归阶乘（提示：需使用海象运算符 或者 reduce） '''

''' 57. 将 [[1,2],[3,4],[5]] 二维列表扁平化为 [1,2,3,4] '''

''' 58. 判断超级素数，如：23 有 2 和 3 两个素数组成 '''

''' 59. 输入一个数字，生成多个幂函数。如：10，返回[10，100，1000，10000，100000] '''

''' 60. 自定义一个高级函数 fun(data,func)，从列表中过滤满足条件的元素（筛选偶数：lambda x: x%2==0） '''


###### 七、递归函数、迭代器 iter、生成器 yield、闭包函数、函数装饰器 '''
''' 61. 使用递归实现：输入字符串，返回字符串所有可能的排列，如 "abc" → ["abc", "acb", ...]）'''

# 1. 终止条件：len(s) = 1  返回 s
# 2. 递归的规则：把大问题-->小问题-->小问题-->终止条件
#    abcdef: a + 组合，b + 组合，  c + 组合 ，1
def str_sort(s):
    if len(s) == 1:
        return s
    res = set()  # 定义空集合，目的出现相同字符
    # 通过索引提取
    for i, c in enumerate(s):  # i 索引值 0，c 字符
        # print(i,c)
        for j in str_sort(s[:i] + s[i + 1:]):
            res.add(c + j)
    return res


s = "aab"
print(str_sort(s))

''' 62. 使用递归实现：判断是否为回文字符串，忽略大小写，以及非字母数字。返回是 True、False'''


# 回文：字母和数字，首尾相同   'abba'  'a1a'  '121'
# 比如：s = abc1cba      s[0]==s[-1]   s[1]==s[-2]
def is_huiwen(s1):
    # 预处理：处理非数字和字母，'abc，1 cba#' ==> 'abc1cba'
    s1 = ''.join(i.lower() for i in s1 if i.isalnum())
    if len(s1) <= 1:
        return True
    if s1[0] != s1[-1]:
        return False

    return is_huiwen(s1[1:-1])


s1 = 'abc，11 cba#'
print(is_huiwen(s1))

''' 63. 使用 iter 创建迭代器对象：判断字典列表中，用户的密码复杂度，返回字典{用户名：复杂度}
    规则：字母、数字、特殊字符。只含1种 -> 弱。含2种 -> 中。只含3种 -> 强 '''


def get_pwd(pwd):
    n = sum([any(c.isdigit() for c in pwd),
             any(c.isalpha() for c in pwd),
             any(not c.isalnum() for c in pwd)])
    print(n)
    return '弱' if n == 1 else '中' if n == 2 else '强'


user_info = [{'name': 'li', 'pwd': '123'}, {'name': 'liu', 'pwd': '123qwe'}, {'name': 'zhang', 'pwd': '123qwe@'}]
iter_user = iter(user_info)
res = {d['name']: get_pwd(d['pwd']) for d in user_info}
print(res)

# print(all(1 for c in '123' if c.isdigit()))




''' 66. 使用 yield 生成器实现密码生成函数，长度8，必须包含大写、小写字母、数字、特殊字符 '''
# import string
# import random
# random.shuffle()    先随机打乱列表，''.join
# random.choice()     从数字集 0 ~ 9 抽取 1 个
#
# # 构造字符集：数字、小写、大写、特殊
# 长度 8 = [数字、小写、大写、特殊 + 4位字符串]


''' 67. 创建闭包，实现数据集的划分，用于演示训练结果（train、test 或 train、val、test）
    数据集：100  train-80%、test-20%         train-80%*80%、val-80%*20%、test-20% '''
lst = [1, 2, 3, 4, 5, 6]
n = int(len(lst) * 0.8)
# print(lst[:n])
# print(lst[n:])

# def 闭包(数据集，方式=2):
#
#     def seq_2():
#         return train, test
#
#     def seq_3()
#         return train, val, test
#
#     return seq_2 if 方式=2 else seq_3
#
# 闭包(lst，方式=2)

''' 68. 用 lambda 创建闭包，实现计数器功能（每次调用返回值递增 1） '''


''' 70. 函数装饰器：记录函数参数不定的运行时间，以及每次调用日志（函数名、参数、返回值、调用次数）并保存到 logs 文件中 
    如：fun(1, 2, 3, 4, 5, divisor=5)  return (1+2+3+4+5) * divisor '''


###### 八、文件操作 with、shutil、json格式、csv格式、图片文件 '''
''' 71. 创建目录 txt_files，生成 100个文本，并向文件中随机写入20个字母（含大小写），文件名格式为 test_001.txt '''

''' 72. 备份上面的 100 个文件，并删除文件内容中不含字母 a 的文件，返回删除文件的路径 '''

''' 73. 从上面 100 个文件内容中，统计字母（忽略大小写）出现的次数，保存为 json 格式，并修改文件名为 json 文件 '''

''' 74. 模拟训练 10 个 epoch 数据（如loss,accuracy,precision,recall），将结果保存到 csv 中 '''

''' 75. 统一图片文件的格式 png：将（'.png', '.gif', '.bmp', '.jpg', '.jpeg'） '''


###### 九、类、类装饰器、魔法函数 __iter__、__getitem__、__call__'''
''' 76. 定义类，实现从一个 5×5 的二维数组中滑动窗口提取所有子集，要求支持任意窗口以及步长 '''

# [[5, 19, 3, 9, 4],   [0:2] == 0, 1    [5, 19, 3, 9, 4], [16, 15, 16, 21, 13],
#  [16, 15, 16, 21, 13],
#  [7, 4, 16, 1, 13],
#  [14, 20, 1, 23, 15],
#  [9, 24, 8, 19, 4]]

# 定义窗口大小 size=(2, 2), 步长(1, 1)
class ArrWindow:

    def __init__(self, array, size=(2, 2), step=(1, 1)):
        self.array = array
        self.arr_w, self.arr_h = len(self.array), len(self.array[0])
        self.size = size
        self.size_w, self.size_h = self.size
        self.step = step

    def wins(self):
        # 计算 w 宽，移动 self.arr_w - self.size_w + 1, step=1
        for w in range(0, self.arr_w - self.size_w + 1):
            # h 高，移动
            for h in range(0, self.arr_h - self.size_h + 1):
                yield [[col for col in row[h: h+self.size_h]] for row in self.array[w : w + self.size_w]]

import random
random.seed(1)
data = [[random.randrange(1,25) for _ in range(5)] for _ in range(5)]
# size = (2, 2)
# step = (1, 1)
print(data)

allwins = ArrWindow(data)
all_win = allwins.wins()

for i, win in enumerate(all_win, 1):
    print(f'窗口 {i}')
    for j in win:
        print(j)
    print()



''' 77. 定义父类：标准归一化的类 DataNormal，方法是实现数据归一化 normal。
    定义 MaxMin 子类，继承父类，重写实现归一化。 max_min归一化，公式：(x − min) / (max − min)
    定义 ZScores 子类，继承父类，重写实现归一化。  Z-scores动态归一化，公式：1 / (1 + x) '''

class DataNormal:

    def __init__(self, data):
        self.data = data

    def normal(self):
        return self

class MinMax(DataNormal):

    def __init__(self, data):
        super().__init__(data)

    def normal(self):
        data = [col for row in self.data for col in row]
        max_, min_ = max(data), min(data)
        return [[(x - min_)/(max_ - min_) for h in w] for w in self.data]

class ZScores(DataNormal):

    def __init__(self, data):
        super().__init__(data)

    def normal(self):
        return [[round(1 / (1 + h), 8) for h in w] for w in self.data]


data = [[random.randrange(1,25) for _ in range(5)] for _ in range(5)]
print([col for row in data for col in row])
mm = MinMax(data)
print(mm.normal())

zs = ZScores(data)
print(zs.normal())


''' 78. 定义类装饰器，记录函数运行时间 '''
import time
class Timer:
    def __init__(self, func):
        self.func = func

    def fun_run_time(self): # 闭包函数

        def run_time(*args, **kwargs):
            start = time.perf_counter()
            res = self.func(*args, **kwargs)
            end = time.perf_counter()
            print(f'耗时：{end-start}')
            return res

        return run_time

@Timer      # 修饰符
def sum_():
    return sum([i for i in range(1000000)])

print(sum_.fun_run_time()())

# 类实例对象，func
# sum_time = Timer(sum_)
# sum_time.fun_run_time()()


''' 79. 训练过程的迭代器 iter ：在训练模型时，可以创建一个迭代器来模拟训练过程中的每个 epoch 
#     一个 epoch 是指完整遍历一次数据集，数据集可分多个 batch '''

''' 80. 数据批处理迭代器：在训练模型时，通常需要按批次处理数据，划分 batch 。
#     比如训练 data 为 [1 ~ 20]，每隔 batch_size 为 5 次输出一组数据 '''


''' 81. 改进第 80 题，将数据改为无限循环给 batch 喂数据（数据随机打乱）。'''


''' 82. 定义数据集类：通过索引访问单个样本，每个样本是由一个元组（x, x^2）组成 '''



''' 83. 定义图像数据集类：从 image_dir 目录中加载图像数据，并可以调整图片的大小 
#     再训练时，有时需要对图片进行裁剪大小（不同的图片格式，必须使用 PIL 库统一格式） '''


''' 84. 定义损失函数类：便于计算损失时，直接使用损失函数（方差） call
#     方差：如 [1,2,3,4] = ((1-2.5)^2 + (2-2.5)^2 + (3-2.5)^2+ (4-2.5)^2)/4 = 1.25 '''
class LossFun:
    def __init__(self, data):
        self.data = data

    def __str__(self):
        return '实例对象可以当函数使用'

    def __call__(self):
        mean = sum(self.data) / len(self.data)
        sd = sum((x - mean) **2 for x in self.data) / len(self.data)
        return sd


data = [1,2,3,4]

# 实例化对象，使用 call ，对象可以当作函数使用
loss = LossFun(data)
print(loss)
print(loss())

# 函数装饰器：给函数增加功能
# 类装饰器：给类增加类的实例-函数   @类

# def loss_fun(data):     # 单一功能
#     mean = sum(data) / len(data)
#     sd = sum((x - mean) ** 2 for x in data) / len(data)
#     return sd


''' 85. 定义线性模型类：计算 y = w * x + b 的结果，其中 x = [1, 2,3,4,5] , w = 2.0, b = 0.1 '''
class LineMode:

    def __init__(self, w, b):
        self.weight = w
        self.base = b

    def __call__(self, x):
        return [self.weight * i + self.base for i in x]
        pass


if __name__ == '__main__':
    # 实例对象
    l = LineMode(2.0, 1.0)

    # l 当函数使用， x数据
    x = [1, 2, 3, 4, 5]
    print(l(x))


''' 86. 实现一个神经网络模型类，支持线性计算 y = w * x + b，通过参数选择激活函数（relu 或 sigmoid），并可像函数一样进行前向传播。
        激活函数 relu ==> def relu(x): return max(0, x)
        激活函数 sigmoid ==>  def sigmoid(x): return 1 / (1 + math.exp(-x)) '''
import math

class NN:
    def __init__(self, w, b, func):
        self.w = w
        self.b = b
        self.func = func

    def __call__(self, v):
        return self.func(v * self.w + self.b)

def relu(x):
    return max(0, x)

def sigmoid(x):
    return 1 / (1 + math.exp(-x))

if __name__ == '__main__':
    n1 = NN(2, 1,func=relu)
    x = 20
    print(n1(x))

    n2 = NN(3, 2, func=sigmoid)
    print(n2(10))


''' 87. 定义特征提取器类：用来提取数据 data 的最大值，平均值，标准差，以字典格式返回 '''


''' 88. 定义类装饰器：实现统计函数调用次数 '''


''' 89. 定义类装饰器：实现统计函数运行时间 '''



###### 十、 面试笔试题 '''
''' 91. 给定一个整数数组 arr1 和一个整数目标值 target，请你在该数组中找出所有和为目标值的那两个整数，并返回下标
#     比如：arr=[21, 11, 23, 25, 33, 28]	   target=44		输出：{num: [21, 23],index: [0, 2] '''


''' 92. 给定一个字符串 s ，请你找出其中不含有重复字符串的最长子串的长度.
#     比如：s = abcabcdacbdebc    输出：acbde '''


''' 93. 计算从 0 到 n (含 n) 中数字 2 出现的次数。比如：输入 25，次数 9。 [2,12,20,21,22,23,24,25] '''


''' 94. 使用双指针：验证字符串是否为回文 '''
s = 'python'        # 双指针在python中就相当于定义 2 个变量 ，初始
#    012345
left, right = 0, len(s) - 1
while left <= right:
    if s[left] == s[right]:
        left += 1
        right -= 1





''' 95. 二分查找：在有序列表中查找目标值的索引，没找到返回 -1。
    如：nums = [1, 3, 5, 7, 9] target = 7 返回 3 。nums = [1, 3, 5, 7, 9] target = 11 返回 -1'''

''' 96. 二分查找：给定两个大小分别为 arr2 和 arr3 的数组（从小到大）。请你找出并返回这两个数组的中位数
#     比如：arr1 = [1, 3, 6]，arr2 = [2, 5, 7, 9]  中位数：5 '''

''' 97. 输入股价列表 [7, 1, 5, 3, 6, 4]，计算一次交易的最大利润。输出：，在 1 买 6 卖，最大利润 5。'''

''' 98. 输入列表 [1,3,5,1,0,3,1,1,5,2,8]，返回最长递增子序列的长度 4，序列 [0, 1, 2, 8]'''

''' 99. 动态规划：实现零钱兑换，硬币 coins = [1,2,5] ，金额 amount = 9，返回 [2,2,5]'''


''' 100. 接雨水：给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
#     height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1] ， res = 6 '''
