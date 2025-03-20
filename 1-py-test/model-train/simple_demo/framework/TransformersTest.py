# ------------------------------------------------------------
# 模型训练与调用 (transformers 库（由 Hugging Face 维护）)
# ------------------------------------------------------------

from transformers import pipeline
from transformers import AutoTokenizer, AutoModelForCausalLM
from transformers import BertTokenizer, BertForSequenceClassification
from torch.utils.data import Dataset, DataLoader
import torch

# 文本分类（情感分析）
# ------------------------------------------
# print("\n------------------------------- 1")
# # 加载预训练的情感分析模型
# classifier = pipeline("text-classification", model="distilbert-base-uncased-finetuned-sst-2-english")
# # 输入文本
# text = "I love using Transformers! It's so easy and powerful."
# # 预测情感
# result = classifier(text)
# print(result)  # 输出：[{'label': 'POSITIVE', 'score': 0.9998}]

# 问答系统
# ------------------------------------------
# print("\n------------------------------- 2")
# qa_pipeline = pipeline("question-answering", model="bert-large-uncased-whole-word-masking-finetuned-squad")
# context = "Transformers are deep learning models designed to process sequential data. They were introduced by Google in 2017."
# question = "When were Transformers introduced?"
# answer = qa_pipeline(question=question, context=context)
# print(answer)  # 输出：{'answer': '2017', 'score': 0.98}

# 文本生成
# ------------------------------------------
print("\n------------------------------- 3")
tokenizer = AutoTokenizer.from_pretrained("gpt2")
model = AutoModelForCausalLM.from_pretrained("gpt2")
# 输入
prompt = "In the future, artificial intelligence will"
inputs = tokenizer(prompt, return_tensors="pt")
print(inputs)
print("-------------------------------")
# 输出
# 生成50个token
outputs = model.generate(**inputs, max_length=50)
print(outputs)
print("-------------------------------")
print(tokenizer.decode(outputs[0]))
print("-------------------------------")

# 自定义模型训练（文本分类）
# 使用 PyTorch 微调 BERT 模型
# ------------------------------------------
# 下面代码有问题
# ------------------------------------------

# print("\n------------------------------- 4")
#
#
# # 自定义数据集
# class TextDataset(Dataset):
#     def __init__(self, texts, labels):
#         self.texts = texts
#         self.labels = labels
#         self.tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
#
#     def __getitem__(self, idx):
#         encoding = self.tokenizer(self.texts[idx],
#                                   padding='max_length', truncation=True, max_length=128,
#                                   return_tensors="pt"
#                                   )
#         return {'input_ids': encoding['input_ids'], 'attention_mask': encoding['attention_mask'],
#                 'labels': torch.tensor(self.labels[idx])}
#
#
# # 数据准备
# texts = ["Great product!", "Poor quality..."]
# labels = [1, 0]
# dataset = TextDataset(texts, labels)
# dataloader = DataLoader(dataset, batch_size=2)
#
# # 加载预训练模型
# model = BertForSequenceClassification.from_pretrained('bert-base-uncased')
#
# # 训练循环
# optimizer = torch.optim.AdamW(model.parameters(), lr=5e-5)
# for batch in dataloader:
#     outputs = model(**batch)
#     loss = outputs.loss
#     loss.backward()
#     optimizer.step()
