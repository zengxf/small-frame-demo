i = 3
print(f'{i:03d}.txt')

import string
import random

print(string.ascii_lowercase)

# 获取所有小写字母
letters = string.ascii_lowercase
# 随机选择指定长度的字母并拼接成字符串
random_string = ''.join(random.choice(letters) for _ in range(10))
print(random_string)
print(range(10))
