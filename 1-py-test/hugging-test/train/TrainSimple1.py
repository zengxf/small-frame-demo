# train_bert_sentiment.py

from datasets import load_dataset
from transformers import (
    AutoTokenizer,
    AutoModelForSequenceClassification,
    TrainingArguments,
    Trainer,
    pipeline
)
import numpy as np
import evaluate
import torch

#
#
# 定义数据集和模型名
# ds_name = "yelp_review_full"
ds_name = "imdb" # 数据集少
model_name = "bert-base-uncased"


def main():
    # 步骤1：加载数据集
    try:
        dataset = load_dataset(ds_name)
        small_train = dataset["train"].shuffle(seed=42).select(range(1000))
        small_eval = dataset["test"].shuffle(seed=42).select(range(100))
    except Exception as e:
        print(f"数据集加载失败，请检查网络连接: {str(e)}")
        return

    # 步骤2：数据预处理
    tokenizer = AutoTokenizer.from_pretrained(model_name)

    def tokenize_function(examples):
        return tokenizer(
            examples["text"],
            padding="max_length",
            truncation=True,
            max_length=512
        )

    tokenized_train = small_train.map(tokenize_function, batched=True)
    tokenized_eval = small_eval.map(tokenize_function, batched=True)

    # 步骤3：初始化模型
    model = AutoModelForSequenceClassification.from_pretrained(
        model_name,
        num_labels=5
    )

    # 步骤4：配置训练参数
    training_args = TrainingArguments(
        output_dir="./results",
        evaluation_strategy="epoch",
        learning_rate=2e-5,
        per_device_train_batch_size=8,
        per_device_eval_batch_size=8,
        num_train_epochs=3,
        weight_decay=0.01,
        logging_steps=50
    )

    # 步骤5：定义评估指标
    metric = evaluate.load("accuracy")

    def compute_metrics(eval_pred):
        logits, labels = eval_pred
        predictions = np.argmax(logits, axis=-1)
        return metric.compute(predictions=predictions, references=labels)

    # 步骤6：启动训练
    trainer = Trainer(
        model=model,
        args=training_args,
        train_dataset=tokenized_train,
        eval_dataset=tokenized_eval,
        compute_metrics=compute_metrics
    )

    print("开始训练...")
    trainer.train()
    print("训练完成！")

    # 步骤7：保存模型
    model.save_pretrained("./my_finetuned_bert")
    print("模型已保存至 ./my_finetuned_bert")

    # 测试推理
    classifier = pipeline(
        "text-classification",
        model=model,
        tokenizer=tokenizer,
        device=0 if torch.cuda.is_available() else -1
    )
    test_text = "The food was terrible, but the service was excellent."
    result = classifier(test_text)
    print(f"\n测试推理结果: {result}")


if __name__ == "__main__":
    main()
