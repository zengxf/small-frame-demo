# pandas 第三方库：数据分析库，csv \ execl \ json \ sql

''' 安装：pip install pandas -i 源
当前环境：.venv\Lib\site-packages
# 注意：不要有中文，不要数字开头。  字母+数字+下划线 '''
# import csv  模块
import pandas as pd
# pandas 读取后，返回 Dataframe 数据类型

'''格式：read_csv(文件路径, sep)
    文件路径：默认当前 C:\Study\python33\数据分析pandas\读取并保存cvs数据.py
'''
df = pd.read_csv('../data.csv')     # 上一级目录下的 data.csv
# df = pd.read_csv('C:\Study\python33\data.csv')
print(type(df))         # <class 'pandas.core.frame.DataFrame'>
#    name  age   sal job        列名
# 0  yoyo   20  2000  AI
# 1  wang   24  3000  BI
# 2   liu   26  4000  ST
# 3    li   33  2000  AI

''' sep 分隔符，默认逗号 , '''
df1 = pd.read_csv('../data.csv', sep='\t')
print(df1)
#   name,age,sal,job        # 第一行
# 0  yoyo,20,2000,AI
# 1  wang,24,3000,BI
# 2   liu,26,4000,ST
# 3    li,33,2000,AI


''' hearer 列名，默认第一行 '''
df2 = pd.read_csv('../data.csv', header=None)
print(df2)


''' names 指定列名[列名1，列名2，...] '''
df3 = pd.read_csv('../data.csv', names=['姓名','年龄','薪资','工作'])
print(df3)


''' skiprow 跳过行号[1,2,...] ，添加列名，本身有列名，从0'''
df4 = pd.read_csv('../data.csv', names=['姓名','年龄','薪资','工作'], skiprows=[0, 2, 4])
print(df4)


''' usecols 指定列名读取，[0，2] 或 [列名1，列名3]'''
df5 = pd.read_csv('../data.csv', usecols=['name', 'sal'])
df6 = pd.read_csv('../data.csv', usecols=[0, 2])
print(df5)


# Dataframe 对象的属性
df7 = pd.read_csv('../data.csv')
print(df7)
print(df7.shape)        # 二维表（row, col）
print(df7.values)
print(df7.size)
print(df7.ndim)
print(df7.to_string(index=False))
print(df7.head(2))
print(df7.tail(2))


# pd.read_excel()
''' to_csv '''
# df7.to_string('data.xls',encoding='gbk')
from openpyxl.workbook import Workbook
# df8 = pd.DataFrame([['a', 'b'], ['c', 'd']], index=['row 1', 'row 2'],columns=['col 1', 'col 2'])

df7.to_excel('data.xlsx',index=False, columns=['name','age','sal','job'])
