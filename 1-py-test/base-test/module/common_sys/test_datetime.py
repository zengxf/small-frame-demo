# datetime 模块
# 处理日期和时间。

from datetime import datetime

now = datetime.now()

print("-----------------------------------")
print(now)
print(now.strftime("%Y-%m-%d %H:%M:%S"))
