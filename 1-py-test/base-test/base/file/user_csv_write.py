"""
CSV 写入 (列表数据)
"""

import csv

# 实例操作：user.csv
lst1 = ['name', 'age', 'position']
lst2 = [['大师兄', 72, 'CV'], ['二师兄', 32, 'CV']]

cvs_file = r'user.csv'

# writer(文件) 函数，写入 csv 文件，返回一个对象，
with open(cvs_file, 'w', newline='', encoding='utf-8') as f:
    write = csv.writer(f)  # 返回 wirte 对象
    # writerow 写入一行数据
    write.writerow(lst1)
    # writerows 写入多行数据
    write.writerows(lst2)
