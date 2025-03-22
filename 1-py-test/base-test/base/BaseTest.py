# --------------------------------------------------------------------------------
# 基础语法测试
# ref: https://github.com/MiniMax-AI/MiniMax-01
# --------------------------------------------------------------------------------

import numpy as np
import array

# -----------------------------------------
# 快速创建数组 (实际上是 list)
#
print("\n------------------------------- 1")
layers = [2, 3, 4, 5, 6, 7]
arr1 = [f"test-a index: {i}" for i in range(2, 5)]
print(arr1)
arr2 = [f"test-b index: {i}" for i in layers]
print(arr2)
arr3 = range(2, 5)  # 只是一个对象
print(arr3)
arr4 = [i for i in range(2, 5)]  # list [2, 3, 4]
print(arr4)
arr5 = np.array(arr4)  # 虽说是数组，只不过是 NumPy 封装的一种类型
print(arr4)
arr6 = array.array('i', [1, 2, 3])  # 仅允许整型
print(arr6)
print("arr6 长度:", len(arr6))

print("-------------------------------")
arr7 = (["lm_head", "embed_tokens", "----"]
        + [f"test-1.{i}" for i in range(2)] + ["----"]
        + [f"test-2.{i}" for i in range(3)]
        )  # 多个 list 拼接成一个
print(arr7)

# 数组切片
print("-------------------------------")
in_arr = [101, 102, 103, 104, 105, 106]
out_arr1 = in_arr[:4]  # res: [101, 102, 103, 104]
out_arr2 = in_arr[4:]  # res: [105, 106]
print("源数组:", in_arr)
print("(前)切片后的数组:", out_arr1)
print("(后)切片后的数组:", out_arr2)

fruits = ['apple', 'banana', 'cherry', 'date', 'elderberry', 'fig', 'grape']
print(fruits[0:3])  # 提取前三个元素：  输出: ['apple', 'banana', 'cherry']
print(fruits[1:5])  # 提取从第二个元素到第五个元素：  输出: ['banana', 'cherry', 'date', 'elderberry']
print(fruits[2:])  # 提取从第三个元素到末尾：  输出: ['cherry', 'date', 'elderberry', 'fig', 'grape']
print(fruits[:4])  # 提取从开头到第四个元素：  输出: ['apple', 'banana', 'cherry', 'date']
print(fruits[:])  # 复制整个列表：  输出: ['apple', 'banana', 'cherry', 'date', 'elderberry', 'fig', 'grape']

# 数组切片-使用负数索引
numbers = [10, 20, 30, 40, 50, 60, 70]
print(numbers[-3:])  # 提取最后三个元素：  输出: [50, 60, 70]
print(numbers[-4:-1])  # 提取倒数第四个到倒数第二个元素：  输出: [40, 50, 60]

# 数组切片-使用步长
letters = ['a', 'b', 'c', 'd', 'e', 'f', 'g']
print(letters[::2])  # 每隔一个元素提取一次：  输出: ['a', 'c', 'e', 'g']
print(letters[1::3])  # 从第二个元素开始，每隔两个元素提取一次：  输出: ['b', 'e']
print(letters[::-1])  # 反向提取整个列表：  输出: ['g', 'f', 'e', 'd', 'c', 'b', 'a']

data = [1, 2, 3, 4, 5, 6, 7, 8, 9]
print(data[-3::-2])  # 从倒数第三个元素开始，每隔两个元素提取一次：  输出: [7, 5, 3, 1]

# -----------------------------------------
# 除法测试
#
print("\n------------------------------- 2")
v1 = 256
v2 = v1 / 7  # 带小数
v3 = v1 // 7  # 取整数
print(v2, v3)

# -----------------------------------------
# zip 元组
#
print("\n------------------------------- 3")
names = ['Alice', 'Bob', 'Charlie']
ages = [25, 30, 35]
zipped = zip(names, ages)
print(list(zipped))

print("-------------------------------")
names = ['Alice', 'Bob', 'Charlie']
ages = [25, 30, 35]
cities = ['上海', '深圳', '广州']
zipped = zip(names, ages, cities)
print(list(zipped))

# 使用 zip() 解压
print("-------------------------------")
zipped = [('Alice', 25), ('Bob', 30), ('Charlie', 35)]
names, ages = zip(*zipped)
print(names)  # 输出: ('Alice', 'Bob', 'Charlie')
print(ages)  # 输出: (25, 30, 35)

# 使用 zip() 和 dict() 创建一个字典
print("-------------------------------")
names = ['Alice', 'Bob', 'Charlie']
ages = [25, 30, 35]
name_age_dict = dict(zip(names, ages))
print(name_age_dict)

# 解包
print("-------------------------------")


def add(a, b, c):
    return a + b + c


numbers = [1, 2, 3]
result = add(*numbers)
print(result)  # 输出: 6

print("-------------------------------")
names = ['Alice', 'Bob', 'Charlie']
ages = [25, 30, 35]
cities = ['上海', '深圳', '广州']
testList = [names, ages, cities]
zipped = zip(*testList)
print(list(zipped))

# -----------------------------------------
# 除法测试
#
