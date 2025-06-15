# 安装：pip install pandas -i 源
import pandas as pd

df = pd.read_csv('./data.csv')  # 上一级目录下的 data.csv

# print(type(df))  # <class 'pandas.core.frame.DataFrame'>
# print(df)
#    name  age   sal job
# 0  yoyo   20  2000  AI
# 1  wang   24  3000  BI
# 2   liu   26  4000  ST
# 3    li   33  2000  AI

print("*" * 20)

''' sep 分隔符，默认逗号 , '''
df1 = pd.read_csv('./data_t.csv', sep='\t')
print(df1)
print("*" * 20)

''' hearer 列名，默认第一行 '''
df2 = pd.read_csv('./data.csv', header=None)
print(df2)
print("*" * 20)

''' names 指定列名[列名1，列名2，...] '''
df3 = pd.read_csv('./data.csv', names=['姓名', '年龄', '薪资', '工作'])
print(df3)

''' skiprow 跳过行号[1,2,...] ，添加列名，本身有列名，从0'''
df4 = pd.read_csv('./data.csv', names=['姓名', '年龄', '薪资', '工作'], skiprows=[0, 2, 4])
print(df4)

''' usecols 指定列名读取，[0，2] 或 [列名1，列名3]'''
df5 = pd.read_csv('./data.csv', usecols=['name', 'sal'])
print(df5)
print("*" * 20)
df6 = pd.read_csv('./data.csv', usecols=[0, 2])
print(df6)

# Dataframe 对象的属性
df7 = pd.read_csv('./data.csv')
# print(df7)
print("*" * 20)
print(df7.shape)  # 二维表（row, col） (4, 4)
print(df7.values)
# [['yoyo' 20 2000 'AI']
#  ['wang' 24 3000 'BI']
#  ['liu' 26 4000 'ST']
#  ['li' 33 2000 'AI']]
print(df7.size)  # 大小: 16
print(df7.ndim)  # 维度: 2
print("*" * 20)
print(df7.to_string(index=False))
print("*" * 20)
print(df7.head(2))
print("*" * 20)
print(df7.tail(2))
