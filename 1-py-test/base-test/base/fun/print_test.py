name, age = 'yoyo', 25
print('我叫', name, '，今年', age, '岁')  # 我叫 yoyo ，今年 25 岁

# 设置多个对象之间为 .
print('www', 'python', 'org', sep='.')  # www.python.org

# 输入内容设置结尾符为 !
print('hello python', end='!')  # hello python!
print('')
# # 将输出的内容保存到 love.txt 文本文件中，并立即刷新缓存
# print('i love you', file=open(r'./love.txt', 'w'), flush=True)


num1 = 12345
num2 = 3.14159
str1 = 'python'

# 可以指定宽度为 10，默认右对齐
print('%10d' % num1, '<')   #      12345 <
print('%10f' % num2, '<')   #   3.141590 <
# 可以指定宽度为 10，左对齐
print('%-10d' % num1, '<')  # 12345      <
# 可以指定宽度为 10，零补位
print('%010d' % num1, '<')  # 0000012345 <
# 输出多种格式：%10d, %10.2f, %10s
print('%f，%10.2f，%10s' % (num1, num2, str1), '<')
# 12345.000000，      3.14，    python <
