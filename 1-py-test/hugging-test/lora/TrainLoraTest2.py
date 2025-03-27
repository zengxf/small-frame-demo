"""
参考 https://yuanbao.tencent.com/chat/naQivTmsDa/db467971-388f-437c-9299-1df6dc52c296
"""

# 环境准备（整合网页1、3、4、7的加速方案）
from transformers import (
    AutoModelForSeq2SeqLM, AutoTokenizer,
    TrainingArguments, Trainer, DataCollatorForSeq2Seq
)
from peft import LoraConfig, get_peft_model
from datasets import load_dataset
import torch
import os

# 配置镜像加速（网页4、7）
os.environ["HF_HUB_DISABLE_SYMLINKS_WARNING"] = "1"  # 禁用符号链接警告
os.environ["HF_ENDPOINT"] = "https://hf-mirror.com"  # 使用国内镜像源
os.environ["TOKENIZERS_PARALLELISM"] = "false"  # 防止tokenizer并行错误

# 全局配置参数
model_name = "t5-small"  # 支持切换为 "google/flan-t5-base"
dataset_name = "samsum"  # 支持切换为 "cnn_dailymail"
max_input_length = 512  # 输入文本最大长度
max_target_length = 128  # 输出文本最大长度
lora_r = 8  # LoRA秩维度
lora_alpha = 32  # LoRA缩放因子


def init_lora_model():
    """初始化LoRA模型，整合网页1、3的最佳实践"""
    # 加载基础模型（添加use_cache配置）
    model = AutoModelForSeq2SeqLM.from_pretrained(
        model_name,
        torch_dtype=torch.float16 if torch.cuda.is_available() else torch.float32,
        device_map="auto",
        use_cache=False  # 重要！防止梯度计算错误
    )
    tokenizer = AutoTokenizer.from_pretrained(model_name)

    # 自动探测线性层（适配不同模型架构）
    def find_all_linear_names(model):
        cls = torch.nn.Linear
        return [name.split('.')[-1]
                for name, module in model.named_modules()
                if isinstance(module, cls)]

    # LoRA配置（优化参数组合）
    peft_config = LoraConfig(
        r=lora_r,
        lora_alpha=lora_alpha,
        target_modules=find_all_linear_names(model),
        lora_dropout=0.05,
        bias="none",
        task_type="SEQ_2_SEQ_LM",
        modules_to_save=["lm_head", "final_layer_norm"]  # 保持关键层可训练
    )

    # 封装模型并设置标签字段
    model = get_peft_model(model, peft_config)
    model.config.label_names = ["labels"]  # 解决label_names警告
    model.print_trainable_parameters()
    return model, tokenizer


def prepare_dataset(tokenizer):
    """数据处理流程优化（整合网页3、4的提示工程）"""
    dataset = load_dataset(dataset_name, trust_remote_code=True)  # 添加信任参数

    def preprocess_function(examples):
        # 构造编码器输入
        inputs = [f"summarize: {dialogue}" for dialogue in examples["dialogue"]]

        # 生成完整序列对（整合输入输出）
        model_inputs = tokenizer(
            inputs,
            max_length=max_input_length,
            truncation=True,
            padding="max_length"
        )

        # 处理解码器输入
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
        load_from_cache_file=False  # 避免缓存问题
    )


if __name__ == "__main__":
    # 初始化模型和数据
    model, tokenizer = init_lora_model()
    processed_data = prepare_dataset(tokenizer)

    # 使用专用数据整理器（解决decoder_input_ids缺失问题）
    data_collator = DataCollatorForSeq2Seq(
        tokenizer,
        model=model,
        pad_to_multiple_of=8,
        padding=True
    )

    # 优化训练参数（整合网页1、3的最佳实践）
    training_args = TrainingArguments(
        output_dir="./lora_outputs",
        per_device_train_batch_size=4,
        per_device_eval_batch_size=4,
        gradient_accumulation_steps=4,
        learning_rate=3e-4,
        num_train_epochs=3,
        fp16=torch.cuda.is_available(),
        logging_steps=50,
        # evaluation_strategy="steps",  # 修正参数名
        eval_strategy="steps",  # 统一策略类型
        save_strategy="steps",  # 必须与eval_strategy一致
        eval_steps=100,  # 每100步评估
        save_steps=100,  # 每100步保存
        report_to="none",  # 禁用wandb等集成
        gradient_checkpointing=True,  # 节省显存
        # predict_with_generate=True,  # 启用生成式评估
        # generation_max_length=max_target_length,
        load_best_model_at_end=True
    )

    # 创建Trainer（添加评估指标）
    trainer = Trainer(
        model=model,
        args=training_args,
        train_dataset=processed_data["train"],
        eval_dataset=processed_data["test"],
        data_collator=data_collator,  # 使用专用collator
        tokenizer=tokenizer
    )

    # 训练与评估
    train_result = trainer.train()
    trainer.save_model("./saved_adapters")

    # 保存完整模型（整合网页3的合并方案）
    merged_model = model.merge_and_unload()
    merged_model.save_pretrained("./merged_model")
    tokenizer.save_pretrained("./merged_model")

    # 输出训练指标
    metrics = train_result.metrics
    print(f"训练完成！最终损失: {metrics['train_loss']:.3f}")
