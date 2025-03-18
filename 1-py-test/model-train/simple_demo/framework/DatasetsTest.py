from datasets import load_dataset

# 基础数据加载
# -----------------------------------------------
print("\n------------------------------- 1")
# 加载内置的 MNIST 数据集（手写数字识别）
dataset = load_dataset("mnist")
print(dataset)  # 查看数据集结构（包含 train/test）

# 查看数据集内容
# -----------------------------------------------
print("\n------------------------------- 2")
# 查看训练集第一条数据（图片像素数组 + 标签）
sample = dataset["train"][0]
print("图片形状:", sample["image"].size)  # 输出 (28, 28)
print("标签:", sample["label"])  # 输出 5
# 查看数据集列名和统计信息
print(dataset["train"].features)  # 显示特征类型（如 Image/ClassLabel）
