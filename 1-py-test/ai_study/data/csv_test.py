import csv  # pandas

with open(r'.\data.csv', mode='r', encoding='utf-8') as f:
    c = csv.reader(f)
    for row in c:
        print(row)
# ['name', 'age', 'sal', 'job']
# ['yoyo', '20', '2000', 'AI']
# ['wang', '24', '3000', 'BI']
# ['liu', '26', '4000', 'ST']
# ['li', '33', '2000', 'AI']