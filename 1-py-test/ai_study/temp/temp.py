# 输入 "aaabbcccca"，输出压缩后的字符串 "a3b2c4a1"

s = 'aaabbcccca'
zips = ''
count = 1
for i in range(1, len(s)):
    if s[i - 1] == s[i]:
        count += 1
    else:
        zips += s[i - 1] + str(count)
        count = 1  # 重置
zips += s[-1] + str(count)
print(zips) # a3b2c4a1
