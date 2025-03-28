# ------------------------------------------------------------
# 加载数据集 (datasets 库（由 Hugging Face 维护）)
# ------------------------------------------------------------

from datasets import load_dataset
from datasets import config

#
# 基础数据加载 imdb
# -----------------------------------------------
print("\n------------------------------- 0")
print(config.HF_DATASETS_CACHE)

#
# 基础数据加载 imdb
# -----------------------------------------------
print("\n------------------------------- 1")
dataset = load_dataset("imdb")
print(dataset)  # 查看数据集结构（包含 train/test）
print("------------------------")
print(dataset["train"].num_rows)
print("------------------------")
print(dataset["test"].num_rows)
print("------------------------")
print('本地缓存文件:', dataset.cache_files)
# print("------------------------")
# print('本地下载文件:', dataset.info.download_checksums)  # 没有 info 属性

# #
# # 基础数据加载 yelp_review_full
# # -----------------------------------------------
# print("\n------------------------------- 1")
# dataset = load_dataset("yelp_review_full")
# print(dataset)  # 查看数据集结构（包含 train/test）
# print("------------------------")
# print(dataset["train"].num_rows)
# print("------------------------")
# print(dataset["test"].num_rows)
