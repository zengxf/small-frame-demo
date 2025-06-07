''' 在 Python 文件分 2 种：txt文件 t，二进制文件 b'''

# 文件对象操作：打开文件、读取|写入、关闭文件
# ascii、gbk（python默认）、gb2313、gb18030、unicode、utf-8(中文，占3位)

''' open 打开文件，返回文件对象 
    格式：open('文件路径', mode='文件模式', encoding='utf-8') '''

# 文件模式：r读、w写、a追加、x写、r+、w+、a+、t(默认txt)、b(二进制文件)
'''
1. r 只能读取，文件不存在报错
2. w 写入内容，文件不存在，创建并有写权限。覆盖内容写入
3. x 写入内容，文件存在会报错，不存在创建并有写权限。覆盖内容写入
4. a 追加写，在内容后面进行写入
'''

# file_path = r'C:\Study\python33\day8\5.文件操作.py'
# f = open(file_path, mode='r', encoding='utf-8')

# 文件对象进行操作：read读内容、write写内容、close关闭文件
''' 1. 读取文件： read , readline , readlines'''
# print(f.read())     # 默认读取全部，返回是字符串
# print(f.read(50))       # 读取字符数，1个汉字1个字符
# s = f.read()
# print(type(s))

# print(f.readline())     # 默认读取一行
# print(f.readline(300))       # 第一行的字符数，如果超过只返回第一行

# print(f.readlines())      # 读取全部的行，返回列表，每行一个元素。每行最后含 \n
# print(f.readlines(10))      # 字符数，字符不足一行，显示整行

# print(f.readable())


''' 2. 写入文件： write(字符串) , rwritelines(列表)'''
f = open('test2.txt', mode='a', encoding='utf-8')
# f.write('\nabcde')        # 以字符串，写入文件内容
# f.writelines(['1','2','3','4','5'])
# f.writelines(['1\n','2\n','3','4','5'])

# f.writable()

''' 3. 必须关闭文件：close '''
f.close()


''' ############### with 语句 ############## '''
# 文件内容，读写一样的
# f = open('文件路径', mode='模式', encoding='gbk')
with open('test2.txt', mode='r', encoding='gbk') as f:
    '文件进行操作'
    print(f.read(20))


with open('test2.txt','r+') as fr, open('test.txt','w+') as fw:
    list_file = fr.readlines()
    fw.writelines(list_file)
    print(list_file)


# 练习：
''' 1. 创建一个目录 file_txt，生成 100 个 txt文件，且每个 txt 文件随机写入 10 个 a~z 组成的字符串，
       文件名命令规则 001.txt ~ 100.txt '''

import os
import random
import string


def create_file(file_path):
    with open(file_path, mode='a', encoding='utf-8') as f:
        # f.write(''.join(random.sample(char_set, k=10)))      # 不重复
        f.writelines(random.sample(char_set, k=20))
        # f.write(random.choices(char_set, k=10))     # 重复
    # print(f'{file_path} 文件创建完成')


if __name__ == '__main__':
    # 定义字符集 a ~ z
    char_set = string.ascii_lowercase       # 全局变量
    # 定义文件目录 file_txt
    file_dir = r'C:\Study\python33\day8\file_txt'
    # 判断文件目录是否存在，不存在创建 file_txt
    if not os.path.exists(file_dir):
        os.mkdir(file_dir)
    # os.makedirs(file_dir, exist_ok=True)
    for i in range(1,101):
        file_path = os.path.join(file_dir, f'{i:03d}.txt')
        # create_file(file_path)

    pass


''' 2. 从上面生成的 txt 文件内容中，读取含有 a 这个字符串的文件路径名 '''
# 目录
file_dir = r'C:\Study\python33\day8\file_txt'

# 拼接文件路径
files = os.listdir(file_dir)    # 获取目录下的所有文件，返回字符串列表
file_txt = [file_name for file_name in files if file_name.endswith('.txt')]

# file_all = [file_name for file_name in os.listdir(file_dir) if file_name.endswith('.txt')]
# print(files)
# print(file_txt)

# 拼接文件路径
# for file in file_txt:
#     file_txt_path = os.path.join(file_dir, file)

# 打开文件，读取内容。循环 100 打开
for file in file_txt:
    file_txt_path = os.path.join(file_dir, file)
    with open(file_txt_path, mode='r', encoding='utf-8') as f:
        content = f.read()   # 读取是内容，返回 content 字符串
        if 'a' in content:
            print(file_txt_path)

'''
4. 当前目录下，创建一个 txt 文件，写入内容：
白发并非雪可替,
相逢已是上上签.

5. 在上面的文件中，插入标题 《自强人》

3. 编写一个程序，从一个文本文件中读取每一行，并将每行的内容逆序写入另一个文件中

6. file_txt 目录下的文件，重名名，规则文件名前面添加5个随机字母
'''