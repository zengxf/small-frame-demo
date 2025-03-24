import torch
from transformers import BertTokenizer, BertForSequenceClassification, Trainer, TrainingArguments
from datasets import load_dataset, load_metric
import numpy as np

# 加载 IMDB 数据集
dataset = load_dataset('imdb')

# 加载预训练的 BERT 分词器
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')


# 定义一个函数来编码数据集
def encode_batch(batch):
    return tokenizer(batch['text'], padding='max_length', truncation=True, max_length=256)


# 编码训练集和测试集
encoded_dataset = dataset.map(encode_batch, batched=True, batch_size=len(dataset['train']))

# 设置格式为 PyTorch 张量
encoded_dataset.set_format(type='torch', columns=['input_ids', 'attention_mask', 'label'])

# 分离训练集和测试集
train_dataset = encoded_dataset['train']
test_dataset = encoded_dataset['test']

# 加载预训练的 BERT 模型用于序列分类
model = BertForSequenceClassification.from_pretrained('bert-base-uncased', num_labels=2)

# 定义训练参数
training_args = TrainingArguments(
    output_dir='./results',
    num_train_epochs=3,
    per_device_train_batch_size=16,
    per_device_eval_batch_size=32,
    evaluation_strategy='epoch',
    save_strategy='epoch',
    logging_dir='./logs',
    logging_steps=10,
    load_best_model_at_end=True,
    metric_for_best_model='accuracy',
)

# 加载准确率指标
metric = load_metric('accuracy')


# 定义评估函数
def compute_metrics(p):
    preds = np.argmax(p.predictions, axis=1)
    return metric.compute(predictions=preds, references=p.label_ids)


# 初始化 Trainer
trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=train_dataset,
    eval_dataset=test_dataset,
    compute_metrics=compute_metrics,
)

# 开始训练
trainer.train()

# 评估模型
results = trainer.evaluate()
print(results)
