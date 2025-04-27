"""
CSV 读取
"""

import csv

# 实例操作：user.csv
cvs_file = r'user.csv'

# reader(文件) 函数，读取 csv 的数据，以列表形式返回每一行数据
with open(cvs_file, 'r', newline='', encoding='utf-8') as f:
    content = csv.reader(f)
    for row in content:
        print(row)

# DictReader(文件) 函数，读取 csv 的数据，以字典形式返回每一行数据
with open(cvs_file, 'r', newline='', encoding='utf-8') as f:
    content = csv.DictReader(f)
    for row in content:
        print(row)
