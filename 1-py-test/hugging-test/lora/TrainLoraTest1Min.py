"""
改小，只用 250 条数据测试

测试不通过
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


# 新增：加载时直接取子集
dataset = load_dataset(dataset_name)
train_subset = dataset["train"].select(range(sample_size))  # 取前250条
test_subset = dataset["test"].select(range(int(sample_size * 0.2)))  # 取50条测试

# 预处理小数据集
tokenized_train = train_subset.map(preprocess_function, batched=True)
tokenized_test = test_subset.map(preprocess_function, batched=True)

# 4. 设置训练参数（优化训练速度）
training_args = TrainingArguments(
    output_dir=output_dir,
    eval_strategy="steps",  # 改为按步骤评估
    eval_steps=10,  # 每10步评估一次
    save_strategy="no",  # 关闭自动保存
    learning_rate=2e-5,
    per_device_train_batch_size=8,  # 减小批次大小
    per_device_eval_batch_size=8,
    num_train_epochs=1,  # 仅训练1个epoch
    weight_decay=0.01,
    logging_dir=logging_dir,
    logging_steps=5,  # 更频繁的日志记录
    fp16=True,
    report_to="none"  # 禁用wandb等报告工具
)

# 5. 配置LoRA（保持原样）
lora_config = LoraConfig(
    task_type="SEQ_CLS",
    r=8,
    lora_alpha=32,
    lora_dropout=0.1,
    target_modules=["query", "key", "value"]
)
model = get_peft_model(model, lora_config)

# 6. 创建Trainer（使用子集）
trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=tokenized_train,
    eval_dataset=tokenized_test,
)

# 7. 开始训练
trainer.train()

# 8. 快速验证（新增推理示例）
sample_text = "This movie was absolutely amazing!"
inputs = tokenizer(sample_text, return_tensors="pt", padding=True, truncation=True)
with torch.no_grad():
    outputs = model(**inputs)
    predictions = torch.argmax(outputs.logits, dim=-1)
print(f"Prediction: {'Positive' if predictions == 1 else 'Negative'}")
