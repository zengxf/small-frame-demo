''' pillow 第三方库：读取图片 '''

# 终端安装：pip install pillow -i 国内源

from PIL import Image
# from setuptools.package_index import distros_for_url

# png、jpg\jpge、bmp、gif动态
img_path = r'lenna.png'

# open 打开图片，返回 image 图片对象，变量 img
# img = Image.open(img_path)

# 图片对象的属性
# print(img.size)     # 返回：元组图片宽、高(512, 512)
# width = img.width
# height = img.height

# 图片对象的方法
# img.copy()          # 深拷贝
# img.show()      # 显示图片
# img.close()         # 关闭图片


# with Image.open(img_path) as img:
#     img.show()


''' 1. 调整大小 resize((宽，高), box=(左,上,右,下)) '''
with Image.open(img_path) as img:
    print(img.size)
    img = img.copy()
    img1 = img.resize((200,200))

    img2 = img.resize((1250, 1250))

    img3 = img.resize((150, 150), box=(100, 100, 200, 200))
    # img3.show()


''' 2. 转模式 convert("RGB")，1黑白1b，L灰度图8b，RGB24b，RGBA32b '''
# 图片不能直接修改后缀名，需要转模式
with Image.open(img_path) as img:
    img = img.copy()
    img4 = img.convert('L')
    img5 = img.convert('RGB')
    img6 = img.convert('1')
    img7 = img.convert('RGBA')

    img4.save('new_lenna.png')

    # img6.show()


''' 3. 旋转角度 rotate(角度) '''
with Image.open(img_path) as img:
    img = img.copy()
    img8 = img.rotate(90)
    img9 = img.rotate(180)
    img10 = img.rotate(270, center=(100,100))

    # img10.show()


''' 4. 保存图片 save '''
with Image.open(img_path) as img:
    img = img.copy()
    img11 = img.convert("L")
    img12 = img.rotate(180)
    img13 = img.convert('RGBA')
    img14 = img.rotate(270)
    img15 = img.convert('1')

    img15.save('文件名.jpg')

    # img15.show()

    ''' save 保存动态图 gif 
    img.save('图片名.gif', save_all=True, append_images='序列', loop=0, duration='毫秒')
        # save_all：是否全部保存
        # append_images = [图片序列]  首张[1:]
        # loop 循环 0 无限循环，1, 2
        # duration 每帧，每张图片的耗时 1000，还可以通过列表 [1000，2000，3000，4000]
    '''
    img_seq = [img11, img12, img13, img14, img15]
    img_seq[0].save('图片名.gif', save_all=True,
                    append_images=[img_seq[1], img_seq[2], img_seq[3],img_seq[4]], loop = 0,
                    duration=[1000,2000,3000,4000,5000])
                    # duration = 1000)


import uuid
print(uuid.uuid4())


# lxml 第三方模块，需要安装。读取 xml
# xpath 语句：xpath(//相对/绝对.text())


import csv          # pandas

with open(r'C:\Study\datapro\data.csv',mode='r',encoding='utf-8') as f:
    c = csv.reader(f)
    for row in c:
        print(row)



