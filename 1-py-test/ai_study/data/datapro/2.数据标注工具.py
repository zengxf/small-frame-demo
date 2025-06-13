'''# labelimg python=3.9 和 labelme '''

''' 
1. 在 pycharm 创建项目环境下，安装 labelImg
   (.venv) C:\Study\datapro> pip install labelimg
   安装在当前：.venv\Lib\site-packages\labelImg

2. python=3.10+，使用 labelImg 闪退，原因数据类型错误 float --> int
   解决：替换 2 个文件
   1）把 canvas.py 文件拷贝到 C:\Study\datapro\.venv\Lib\site-packages\libs\
   2）把 labelImg.py 文件拷贝到 C:\Study\datapro\.venv\Lib\site-packages\labelimg\
   
    注意：python 3.10+ 使用时会出现闪退。需要在当前环境目录下修改 2个文件，如下
    当前环境下 ..\lib\site-packages\libs\canvas.py 文件 526、530、531行的 float 改为 int
    当前环境下 ..\Lib\site-packages\labelImg 第 965 行将 float 改为 int

'''
