"""
参考 https://chat.minimaxi.com/?type=chat&chatID=361477765350862848
"""

# 导入必要的库
import torch
from transformers import AutoModelForSequenceClassification, AutoTokenizer, Trainer, TrainingArguments
from datasets import load_dataset
from peft import LoraConfig, get_peft_model

# 1. 配置参数
model_name = "bert-base-uncased"  # 预训练模型名称
dataset_name = "imdb"  # 数据集名称。数据少。 ref: https://huggingface.co/datasets/stanfordnlp/imdb
num_labels = 2  # 分类任务的标签数量（例如，情感分析中的正面/负面）
output_dir = "./lora_model"  # 模型保存路径
logging_dir = "./logs"  # 日志保存路径

# 2. 加载预训练模型和 tokenizer
model = AutoModelForSequenceClassification.from_pretrained(model_name, num_labels=num_labels)
tokenizer = AutoTokenizer.from_pretrained(model_name)


# 3. 加载和预处理数据集
def preprocess_function(examples):
    return tokenizer(examples["text"], padding="max_length", truncation=True, max_length=512)


dataset = load_dataset(dataset_name)
tokenized_datasets = dataset.map(preprocess_function, batched=True)

# 4. 设置训练和评估集
train_dataset = tokenized_datasets["train"]
eval_dataset = tokenized_datasets["test"]

# 5. 配置 LoRA
lora_config = LoraConfig(
    task_type="SEQ_CLS",  # 任务类型，序列分类
    inference_mode=False,
    r=8,  # LoRA 的秩
    lora_alpha=32,  # LoRA 的 alpha 参数
    lora_dropout=0.1,  # LoRA 的 dropout 概率
    target_modules=["query", "key", "value"]  # 需要应用 LoRA 的模块
)

# 6. 应用 LoRA 到模型
model = get_peft_model(model, lora_config)
print("Trainable parameters:", model.print_trainable_parameters())

# 7. 设置训练参数
training_args = TrainingArguments(
    output_dir=output_dir,
    # evaluation_strategy="epoch",  # 每个 epoch 评估一次
    eval_strategy="epoch",  # 每个 epoch 评估一次
    save_strategy="epoch",  # 设置保存策略为按 epoch 进行
    learning_rate=2e-5,  # 学习率
    per_device_train_batch_size=16,  # 训练批次大小
    per_device_eval_batch_size=16,  # 评估批次大小
    num_train_epochs=3,  # 训练 epoch 数
    weight_decay=0.01,  # 权重衰减
    logging_dir=logging_dir,
    logging_steps=10,  # 每 10 步记录一次日志
    # save_steps=500,  # 每 500 步保存一次模型
    save_steps=None,  # 由于已经设置了 save_strategy="epoch"，可以将其设置为 None
    load_best_model_at_end=True,  # 训练结束后加载最佳模型
    fp16=True,  # 使用 16 位精度训练（如果可用）
)

# 8. 初始化 Trainer
trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=train_dataset,
    eval_dataset=eval_dataset,
)

# 9. 开始训练
trainer.train()

# 10. 保存最终模型
model.save_pretrained(output_dir)
tokenizer.save_pretrained(output_dir)
