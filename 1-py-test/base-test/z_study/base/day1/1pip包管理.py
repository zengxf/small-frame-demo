# coding:utf-8
"""
pycharm 开发工具，主要用来写python代码、运行代码、调用代码（anaconda管理项目环境）
1. 注释
# 单行注释
''' 多行注释 '''
# py文件的字符编码注释
注释快捷方式：按 Ctrl + /
"""

''' pip 包管理工具，在终端使用命令，安装、卸载、更新第三方库 numpy '''
# pip --help    或  pip -h

""" 1. pip list 列出已安装的包 """
'''
(.venv) C:\Study\python33> pip list
Package    Version
---------- -------
numpy      2.2.6
pip        23.2.1
setuptools 68.2.0
wheel      0.41.2

'''
""" 2. 升级pip的版本：python.exe -m pip install --upgrade pip """

""" 3. pip install 安装包 """
# pip install -y numpy       # 默认 pip 安装时，重国外源

# pip install -y numpy -i https://mirrors.tuna.tsinghua.edu.cn/  指定国内源（清华、阿里、中科...）

""" 4. pip uninstall 安装包 """
# pip uninstall -y numpy       # 卸载包

""" 5. pip freeze > requirements.txt 将当前已安装的包，导出到依赖包 """

""" 6. pip install -r requirements.txt 安装依赖包中的所有包
    requirements.txt ： >  <  ==  >=  <= 指定包的版本
"""













