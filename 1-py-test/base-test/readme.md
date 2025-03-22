# ModelScope test 
- 魔搭-测试

## 依赖设置
```text

# 创建 requirements.txt 填充下面内容

numpy>=2.2.4

```

### 安装单个依赖
```shell
# (在项目根目录下) 安装并指定版本

pip install numpy==2.2.4
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