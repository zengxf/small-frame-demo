"""
测试不通过
"""

# 环境准备（整合加速方案）
from transformers import (
    AutoModelForSeq2SeqLM, AutoTokenizer,
    TrainingArguments, Trainer, DataCollatorForSeq2Seq
)
from peft import LoraConfig, get_peft_model
from datasets import load_dataset
import torch
import os

# 配置镜像加速（网页4、7）
os.environ["HF_ENDPOINT"] = "https://hf-mirror.com"
os.environ["TOKENIZERS_PARALLELISM"] = "false"

# 全局配置参数（网页1优化）
model_name = "t5-small"
dataset_name = "samsum"
max_input_length = 512
max_target_length = 128
lora_r = 8
lora_alpha = 32


def init_lora_model():
    """初始化LoRA模型（网页1最佳实践）"""
    model = AutoModelForSeq2SeqLM.from_pretrained(
        model_name,
        torch_dtype=torch.float16 if torch.cuda.is_available() else torch.float32,
        device_map="auto",
        use_cache=False
    )
    tokenizer = AutoTokenizer.from_pretrained(model_name)

    # 自动探测线性层（网页3架构适配）
    def find_all_linear_names(model):
        return [name.split('.')[-1]
                for name, module in model.named_modules()
                if isinstance(module, torch.nn.Linear)]

    peft_config = LoraConfig(
        r=lora_r,
        lora_alpha=lora_alpha,
        target_modules=find_all_linear_names(model),
        lora_dropout=0.05,
        bias="none",
        task_type="SEQ_2_SEQ_LM"
    )

    model = get_peft_model(model, peft_config)
    model.config.label_names = ["labels"]
    model.print_trainable_parameters()
    return model, tokenizer


def prepare_dataset(tokenizer):
    """数据处理优化（网页3小数据方案）"""
    dataset = load_dataset(dataset_name, trust_remote_code=True)

    # 关键修改：数据量缩减（网页7建议）
    dataset["train"] = dataset["train"].select(range(100))  # 训练数据保留100条
    dataset["test"] = dataset["test"].select(range(20))  # 测试数据保留20条

    def preprocess_function(examples):
        inputs = [f"summarize: {dialogue}" for dialogue in examples["dialogue"]]
        model_inputs = tokenizer(
            inputs,
            max_length=max_input_length,
            truncation=True,
            padding="max_length"
        )
        with tokenizer.as_target_tokenizer():
            labels = tokenizer(
                examples["summary"],
                max_length=max_target_length,
                truncation=True,
                padding="max_length"
            )
        model_inputs["labels"] = labels["input_ids"]
        return model_inputs

    return dataset.map(
        preprocess_function,
        batched=True,
        remove_columns=dataset["train"].column_names,
        load_from_cache=False
    )


if __name__ == "__main__":
    # 初始化模型和数据（网页1配置）
    model, tokenizer = init_lora_model()
    processed_data = prepare_dataset(tokenizer)

    # 数据整理器（网页3优化）
    data_collator = DataCollatorForSeq2Seq(
        tokenizer,
        model=model,
        pad_to_multiple_of=8
    )

    # 训练参数优化（网页4加速策略）
    training_args = TrainingArguments(
        output_dir="./lora_outputs",
        per_device_train_batch_size=8,  # 增大batch_size
        per_device_eval_batch_size=8,
        gradient_accumulation_steps=2,  # 减少梯度累积
        learning_rate=3e-4,
        num_train_epochs=1,  # 仅训练1个epoch
        fp16=torch.cuda.is_available(),
        logging_steps=10,  # 更频繁的日志
        eval_strategy="steps",
        eval_steps=5,  # 每5步验证一次
        save_strategy="steps",
        save_steps=10,
        report_to="none",
        gradient_checkpointing=False  # 关闭检查点加速
    )

    trainer = Trainer(
        model=model,
        args=training_args,
        train_dataset=processed_data["train"],
        eval_dataset=processed_data["test"],
        data_collator=data_collator,
        tokenizer=tokenizer
    )

    # 训练与保存（网页3简化方案）
    train_result = trainer.train()
    trainer.save_model("./saved_adapters")

    # 输出结果（网页7建议）
    metrics = train_result.metrics
    print(f"训练完成！最终损失: {metrics['train_loss']:.3f}")
