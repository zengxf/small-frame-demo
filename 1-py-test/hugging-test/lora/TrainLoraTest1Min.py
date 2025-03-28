"""
改小，只用 250 条数据测试

运行正常 (但预测不准)
"""

# 导入必要的库
import torch
from transformers import AutoModelForSequenceClassification, AutoTokenizer, Trainer, TrainingArguments
from datasets import load_dataset
from peft import LoraConfig, get_peft_model

# 1. 配置参数（新增少量数据设置）
model_name = "bert-base-uncased"
dataset_name = "imdb"
num_labels = 2
output_dir = "./lora_model"
logging_dir = "./logs"
sample_size = 250  # 新增：设置样本量（原数据集的1%）

# 2. 加载预训练模型和 tokenizer（保持原样）
model = AutoModelForSequenceClassification.from_pretrained(model_name, num_labels=num_labels)
tokenizer = AutoTokenizer.from_pretrained(model_name)


# 3. 加载和预处理数据集（修改为小样本）
def preprocess_function(examples):
    return tokenizer(examples["text"], padding="max_length", truncation=True, max_length=512)


# 4. 加载子集
dataset = load_dataset(dataset_name)
train_subset = dataset["train"].select(range(sample_size))  # 取前250条
test_subset = dataset["test"].select(range(int(sample_size * 0.2)))  # 取50条测试

# 预处理小数据集
tokenized_train = train_subset.map(preprocess_function, batched=True)
tokenized_test = test_subset.map(preprocess_function, batched=True)

# 5. 配置LoRA（保持原样）
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

# 7. 设置训练参数（优化训练速度）
training_args = TrainingArguments(
    output_dir=output_dir,
    # evaluation_strategy="epoch",  # 每个 epoch 评估一次
    eval_strategy="steps",  # 改为按步骤评估
    eval_steps=10,  # 每10步评估一次
    save_strategy="no",  # 关闭自动保存
    learning_rate=2e-5,  # 学习率
    per_device_train_batch_size=8,  # 训练批次大小(减小批次大小)
    per_device_eval_batch_size=8,  # 评估批次大小
    num_train_epochs=2,  # 训练 epoch 数
    weight_decay=0.01,  # 权重衰减
    logging_dir=logging_dir,
    logging_steps=5,  # 更频繁的日志记录 (每 5 步记录一次日志)
    fp16=True,  # 使用 16 位精度训练（如果可用）
    # load_best_model_at_end=True,  # 训练结束后加载最佳模型 (与上面的配置不匹配)
    report_to="none"  # 禁用 wandb 等报告工具
)

# 8. 创建Trainer（使用子集）
trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=tokenized_train,
    eval_dataset=tokenized_test,
)

# 9. 开始训练
trainer.train()

# 10. 保存最终模型
model.save_pretrained(output_dir)
tokenizer.save_pretrained(output_dir)

# ------------------------------------------
# 11. 快速验证（新增推理示例）
sample_text = "This movie was absolutely amazing!"
inputs = tokenizer(sample_text, return_tensors="pt", padding=True, truncation=True)
with torch.no_grad():
    outputs = model(**inputs)
    predictions = torch.argmax(outputs.logits, dim=-1)
print(f"Prediction: {'Positive' if predictions == 1 else 'Negative'}")

print("\n--------------------------------------")
