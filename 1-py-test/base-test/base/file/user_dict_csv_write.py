"""
CSV 写入 (字典列表数据)
"""

import csv

# 实例操作：user_dict.csv
dic1_lst = {'name': 'xiaozhao', 'age': 25, 'job': 'AI'}
dic2_lst = [{'name': '吕布', 'age': 25, 'job': 'AI'},
            {'name': '鲁班', 'age': 25, 'job': 'AI'}]

cvs_file = r'user_dict.csv'

# writer(文件) 函数，写入 csv 文件，返回一个对象，
with open(cvs_file, 'w', newline='', encoding='utf-8') as f:
    write = csv.DictWriter(f, fieldnames=['name', 'age', 'job'])
    write.writeheader()  # writeheader 写入列标题

    # writerow 写入一行数据
    write.writerow(dic1_lst)

    # writerows 写入多行数据
    write.writerows(dic2_lst)
