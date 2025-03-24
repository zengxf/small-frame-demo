"""
模型训练 (有问题，运行不起来)
"""

import os
import torch
from transformers import AutoTokenizer, AutoModelForCausalLM
from datasets import load_dataset
from tqdm import tqdm
import logging
import yaml
import numpy as np

# 配置日志
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)


class ModelTrainer:
    def __init__(self, config_path='0config.yaml'):
        self.load_config(config_path)
        self.setup_model_and_tokenizer()
        self.setup_dataset()

    def load_config(self, config_path):
        with open(config_path, 'r', encoding='utf-8') as f:
            self.config = yaml.safe_load(f)

    def setup_model_and_tokenizer(self):
        self.tokenizer = AutoTokenizer.from_pretrained(
            self.config['model_name'],
            trust_remote_code=True
        )
        self.model = AutoModelForCausalLM.from_pretrained(
            self.config['model_name'],
            trust_remote_code=True,
            device_map='auto'
        )

    def setup_dataset(self):
        self.dataset = load_dataset(
            self.config['dataset_name'],
            use_auth_token=True
        )
        logging.info(f"Dataset loaded: {len(self.dataset['train'])} training samples")

    def preprocess_function(self, examples):
        return self.tokenizer(
            examples[self.config['text_column']],
            truncation=True,
            max_length=self.config['max_length'],
            padding='max_length'
        )

    def train(self):
        train_dataset = self.dataset['train'].map(
            self.preprocess_function,
            batched=True,
            remove_columns=self.dataset['train'].column_names
        )

        train_dataloader = torch.utils.data.DataLoader(
            train_dataset,
            batch_size=self.config['batch_size'],
            shuffle=True
        )

        optimizer = torch.optim.AdamW(
            self.model.parameters(),
            lr=self.config['learning_rate']
        )

        self.model.train()
        for epoch in range(self.config['num_epochs']):
            total_loss = 0
            progress_bar = tqdm(train_dataloader, desc=f'Epoch {epoch + 1}')

            for batch in progress_bar:
                batch = {k: v.to(self.model.device) for k, v in batch.items()}
                outputs = self.model(
                    input_ids=batch['input_ids'],
                    attention_mask=batch['attention_mask'],
                    labels=batch['input_ids']
                )

                loss = outputs.loss
                total_loss += loss.item()

                loss.backward()
                optimizer.step()
                optimizer.zero_grad()

                progress_bar.set_postfix({'loss': loss.item()})

            avg_loss = total_loss / len(train_dataloader)
            logging.info(f'Epoch {epoch + 1}: Average loss = {avg_loss:.4f}')

            # 保存模型检查点
            if (epoch + 1) % self.config['save_every_epochs'] == 0:
                checkpoint_path = os.path.join(
                    self.config['output_dir'],
                    f'checkpoint-epoch-{epoch + 1}'
                )
                self.model.save_pretrained(checkpoint_path)
                self.tokenizer.save_pretrained(checkpoint_path)
                logging.info(f'Saved checkpoint to {checkpoint_path}')


if __name__ == '__main__':
    trainer = ModelTrainer()
    trainer.train()
