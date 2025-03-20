# 模型训练

## 开发工具
- PyCharm 2024.3.4
- Python 3.13
- 运行：直接导入项目文件夹、安装依赖、运行或调式 `Current File`

## 依赖设置
```text

# 创建 requirements.txt 填充下面内容

torch>=2.0.0
transformers>=4.30.0
datasets>=2.12.0
huggingface-hub>=0.16.4
tqdm>=4.65.0
logging>=0.4.9.6
pyyaml>=6.0.1
numpy>=1.24.3
matplotlib>=3.9.0
modelscope>=1.24.0
addict>=2.4.0

```

### 安装单个依赖
```shell
# (在项目根目录下) 安装并指定版本

pip install matplotlib==3.9.0

pip install modelscope==1.24.0

pip install addict==2.4.0
```

## 手动运行
```shell
# 定位目录
cd simple_demo

# 运行 py 文件
python HelloWorld.py
```

## 查看版本
```shell
# 列出所有已安装的 Python 版本
py -0

# 查看默认版本
py --version
```