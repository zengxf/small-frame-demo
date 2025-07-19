''' shutil 模块：主要用来备份文件。不要在源数据上操作'''
import shutil

''' 1. 拷贝文件：copy、copyfile、copy2-拷贝源文件时间 '''
''' shutil.copy('源文件或目录', '目标文件或目录') 重命名 '''
# 1）拷贝图片文件到文件
img_path = r'C:\Study\datapro\label_images\test_00000546.jpg'
# shutil.copy(img_path, 'test_00000546.jpg')

# 2）拷贝图片文件到目录
# shutil.copy(r'./test_00000546.jpg',r'C:\Study\datapro\voc_label')

''' 2. shutil.copyfile('源文件', '目标文件') 重命名'''
# shutil.copyfile(r'./test_00000546.jpg','test.jpg')

''' 3. shutil.move('移除源文件','目标文件或目录') 重命名'''
# shutil.move(r'./test_00000546.jpg',r'C:\Study\datapro\yolo_label')
# shutil.move(r'./test.jpg',r'abcd.png')

''' 4. shutil.rmtree('目录') '''
# shutil.rmtree(r'C:\Study\datapro\coco_label')

''' 5. shutil.copytree() 拷贝目录，目标目录存在报错 '''
# 备份 label_images 目录
shutil.copytree(r'label_images', 'image_copy')