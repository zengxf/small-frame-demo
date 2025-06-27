
def infinite_sequence():
    num = 0
    while True:
        yield num
        num += 1

# 使用生成器
for item in infinite_sequence():
    if item > 10:
        break
    print(item)

# 使用生成器
for item in infinite_sequence():
    if item > 15:
        break
    print(item)