# ------------------------------------------------------------
# 加载数据集 (datasets 库（由 Hugging Face 维护）)
# ------------------------------------------------------------

from datasets import load_dataset
from tqdm import tqdm

# 基础数据加载
# -----------------------------------------------
# print("\n------------------------------- 1")
# # 加载内置的 MNIST 数据集（手写数字识别）
# dataset = load_dataset("mnist")
# print(dataset)  # 查看数据集结构（包含 train/test）

# 查看数据集内容
# -----------------------------------------------
# print("\n------------------------------- 2")
# # 查看训练集第一条数据（图片像素数组 + 标签）
# sample = dataset["train"][0]
# print("图片形状:", sample["image"].size)  # 输出 (28, 28)
# print("标签:", sample["label"])  # 输出 5
# # 查看数据集列名和统计信息
# print(dataset["train"].features)  # 显示特征类型（如 Image/ClassLabel）

# 处理文本数据集示例
# -----------------------------------------------
# print("\n------------------------------- 3")
# # 加载数据集
# news_dataset = load_dataset("ag_news")
# print(news_dataset["train"][:2])  # 查看前两条新闻的文本和标签
# print("------------------------------- ")
# # 过滤长文本（保留长度超过 100 的新闻）
# filtered_data = news_dataset["train"].filter(lambda x: len(x["text"]) > 100)
# print(filtered_data)

# 流式模式处理大数据
# -----------------------------------------------
# print("\n------------------------------- 4")
# # 流式加载（避免内存溢出）
# stream_dataset = load_dataset("ag_news", streaming=True)
# for batch in stream_dataset["train"].take(5):  # 仅加载前 5 条
#     print(batch["text"][:50] + "...")

# 流式模式处理大数据 (带进度条的)
# -----------------------------------------------
print("\n------------------------------- 4")
# 初始化进度条（动态计算总量或预设）
total_samples = 5  # 明确指定加载前5条
progress_bar = tqdm(total=total_samples, desc="流式加载进度", unit="样本")

# 流式加载数据集（不缓存到本地）
stream_dataset = load_dataset("ag_news", streaming=True)

# 逐条加载并处理数据
for batch in stream_dataset["train"].take(total_samples):
    truncated_text = batch["text"][:50] + "..."
    print(truncated_text)

    # 更新进度条（根据实际加载量）
    progress_bar.update(1)  # 每次迭代更新1个单位

progress_bar.close()  # 关闭进度条防止残留
