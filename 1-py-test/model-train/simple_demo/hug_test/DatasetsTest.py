# ------------------------------------------------------------
# 加载数据集 (datasets 库（由 Hugging Face 维护）)
# ------------------------------------------------------------

from datasets import load_dataset

# 基础数据加载 imdb
# -----------------------------------------------
print("\n------------------------------- 1")
dataset = load_dataset("imdb")
print(dataset)  # 查看数据集结构（包含 train/test）
