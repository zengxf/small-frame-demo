
# is 比较内存地址
a = b = 1;
print(a is b)  # True

# 可变类型地址不同
x, y = [1, 2, 3], [1, 2, 3];
print(x is y)  # False

# in 是否在集合中；也叫成员运算符
print(1 in (1, 2, 3, 4, 5))
