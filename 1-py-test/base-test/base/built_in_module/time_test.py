"""
time 模块
"""

import time

# 与时间相关的各种函数。主要用于获取当前时间、测量时间间隔、暂停程序等
''' 1. time() 获取当前时间戳，以浮点数表示，单位为秒。从 1970 年 01月 01日开始 '''
print('当前时间戳：', time.time())

'''
2. ctime() 返回当前时间，以字符串表示，精度到秒
'''
print('当前时间：', time.ctime())

'''
3. localtime() 返回当前时间的元组时间，以北京时间显式
'''
print(time.localtime())

'''
4. perf_counter() 返回 CPU 计数器的精确时间，以浮点数表示，单位为秒
'''
print(time.perf_counter())

'''
5. sleep() 休眠时间，表示进程挂起的时间，单位为秒，也可以是浮点数
'''
time.sleep(1)  # 表示休眠 1 秒
#


''' strftime(格式化, 时间) 格式化时间 '''
print("\n---------------------------")
# 默认时间为当前时间
print(time.strftime("%H:%M:%S"))  # 13:31:42
print(time.strftime("%H:%M:%S", time.localtime()))

# 格式化输出当前的日期 2025-04-28
print(time.strftime("%Y-%m-%d", time.localtime()))

# 格式化输出上下午
print(time.strftime("%p", time.localtime()))  # PM

# 格式化输出星期几
print(time.strftime("%A ", time.localtime()))  # Saturday
