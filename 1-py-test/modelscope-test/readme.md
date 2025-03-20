# ModelScope test 
- 魔搭-测试

## 依赖设置
```text

# 创建 requirements.txt 填充下面内容

modelscope>=1.24.0
addict>=2.4.0
numpy>=2.2.4
datasets>=3.2.0

```

### 安装单个依赖
```shell
# (在项目根目录下) 安装并指定版本

pip install datasets==3.2.0
```

## 查看依赖版本
```shell
# 使用 pipdeptree 详细些
pip install pipdeptree

pipdeptree -p modelscope
pipdeptree -p numpy

# 下面这个一般，不详细
pip show modelscope
pip show numpy
```