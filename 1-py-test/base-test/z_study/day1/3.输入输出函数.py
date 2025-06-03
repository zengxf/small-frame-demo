''' 1. print 标准的输出函数：终端输出的内容 '''
"""
# 格式：print('值或变量或表达式',..., sep=分隔符默认空格, end=结尾符默认换行符)
# help(print)
name = '张三'
print('zhangsan', sep=' ', end='\n')
print(name, sep=' ', end='\n')
print(1+2)
print(1,2,3,4,5,sep='.')
print(1,2,3,4,5,sep='.',file=open('test.txt','w'),flush=True)

# 控制符：\n-换行符   \t-制表符    \r回退行首    \b退一格   \转义符
print('0123456789')
print('0123\\n456789')
print(r'0123\n456789')
print(R'0123\t45\t6789')
print(r'0123\r456789')
print(R'0123\b456789')
"""

# r 或 R 字母，r'后面的内容按原字符输出'


''' 2. input 标准的输入，读取键盘，返回的是字符串 '''
pwd = input('请输入密码：')
print(pwd, type(pwd))
# 1234 <class 'str'>