"""
数据结构测试
"""

print("\n****************************")
print('数据结构 - 列表（List）')
fruits = ["apple", "banana", "cherry"]
print(fruits[0])  # 输出: apple
fruits.append("date")
print(fruits)

print("\n****************************")
print('数据结构 - 元组（Tuple）')
coordinates = (10.0, 20.0)
print(coordinates)

print("\n****************************")
print('数据结构 - 字典（Dictionary）')
person = {
    "name": "Alice",
    "age": 25,
    "city": "New York"
}
print(person["name"])  # 输出: Alice

print("\n****************************")
print('数据结构 - 集合（Set）')
unique_numbers = {1, 2, 3, 2}
print(unique_numbers)  # 输出: {1, 2, 3}
