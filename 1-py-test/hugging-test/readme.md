# 模型训练

## 开发工具
- PyCharm 2024.3.4
- Python 3.13

## 依赖设置
```shell
# 创建 requirements.txt 填充下面内容

torch>=2.6.0                # 25-01
transformers>=4.50.0        # 25-03
datasets>=3.4.1             # 25-03
huggingface-hub>=0.29.3     # 25-03
numpy>=2.2.4                # 25-03
evaluate>=0.4.3             # 24-09
accelerate>=1.5.1           # 25-03
sklearn>=0.0.post12         # 23-12
scikit-learn>=1.6.1         # 25-01
peft>=0.15.0                # 25-03
py7zr>=0.22.0               # 24-08 (samsum 数据集用)

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