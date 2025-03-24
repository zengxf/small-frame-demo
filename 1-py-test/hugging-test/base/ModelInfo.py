from transformers import pipeline
from transformers import AutoConfig
from transformers import AutoModel, AutoTokenizer
from transformers import AutoModelForSequenceClassification

model_name = "bert-base-uncased"

#
# 官网示例
# https://huggingface.co/google-bert/bert-base-uncased
#
unmasker = pipeline('fill-mask', model=model_name)
unmasker("Hello I'm a [MASK] model.")
print(unmasker.model)

#
# 查看模型配置
#
# config = AutoConfig.from_pretrained(model_name)
# # print(config)

#
# 查看分词器信息
#
# tokenizer = AutoTokenizer.from_pretrained(model_name)
# print(tokenizer)

#
# 查看模型摘要
#
# model = AutoModel.from_pretrained(model_name)
# model.summary() # 不可用
# classifier = pipeline('text-classification', model=model_name)
# print(classifier.model)

#
# 获取模型的其他信息
#
# model = AutoModelForSequenceClassification.from_pretrained(model_name)
# # print(model.task_specific_params)  # 查看可用的任务 (出错)
# print(model.state_dict())  # 查看模型的权重 (太大了)
