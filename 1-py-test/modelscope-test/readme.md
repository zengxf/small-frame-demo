# ModelScope test 
- 魔搭-测试
- 模型测试不成功

## 依赖设置
```shell

# 创建 requirements.txt 填充下面内容

modelscope>=1.24.0  # 25-03
addict>=2.4.0       # 20-11
numpy>=2.2.4        # 25-03
datasets>=3.3.0     # 25-02
setuptools>=78.1.0  # 25-03
torch>=2.6.0        # 25-01
Pillow>=11.1.0      # 25-01
simplejson>=3.20.1  # 25-02
sortedcontainers>=2.4.0   # 21-05
transformers>=4.50.1      # 25-03
sentencepiece>=0.2.0      # 24-02 (需要 Python 3.11)

```

### 安装所有依赖
```shell
pip install -r requirements.txt
```

### 安装单个依赖
```shell
# (在项目根目录下) 安装并指定版本

pip uninstall datasets
pip install datasets==3.3.0

pip install sentencepiece
```

## 查看依赖版本
```shell
# 使用 pipdeptree 详细些
pip install pipdeptree

pipdeptree -p modelscope
pipdeptree -p datasets

# 下面这个一般，不详细
pip show modelscope
pip show datasets
```