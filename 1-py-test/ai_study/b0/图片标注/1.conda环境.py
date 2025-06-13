''' 项目环境独立，避免出现冲突 '''

''' anaconda 安装：默认安装的路径 C:\ProgramData\\anaconda3 '''
''' conda 终端使用的命令：base 基础环境 
    (base) PS C:\\Users\Administrator> '''
r"""
1. 查看当前已安装的环境：conda env list

2. 查看 conda 命令：conda --help

3. 创建 conda 环境的命令：conda create -n 环境名 python=版本 -y
   conda create -n 环境名 python=版本 -y timeout=3000
   
   默认的环境保存在：C:\ProgramData\\anaconda3\envs（d:\\anaconda3\envs）

4. 激活切换项目环境：conda activate 环境名
   退出当前项目环境：conda deactivate

5. 删除项目环境：conda remove -n 环境名 --all -y

6. 指定位置安装：conda create --prefix d:\环境名 python=版本 -y
   1）切换环境：conda activate d:\my_conda\data1

   2）如果想通过 conda activate data1 切换环境，必须将 data1 环境的目录 添加到 conda 环境搜索中
      conda config --append envs_dirs d:\my_conda

   3）通过环境名可以切换
      conda activate data1

7. 修改默认安装的路径：conda config --add 指定新环境的存储的目录

8. 导出当前环境的所有依赖包到 .yml 文件中：conda env export -n 环境名 > 文件.yml

9. 通过 yml 文件导入安装依赖包：conda env create -f 文件.yml 

10.清除未使用包：conda clean --all -y

"""
''' conda install 包=版本 -c 指定国内源 '''


''' 配置国内源 
1. 清除原有配置
    conda config --remove-key channels
2. 添加清华源
    conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/
    conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
    conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/conda-forge/

3. 删除所有自定义源
    conda config --remove-key channels
4. 重置为默认源
    conda config --set restore_free_channel true
'''



