# 测试连接性
from huggingface_hub import try_to_load_from_cache
from transformers import AutoModelForSeq2SeqLM;
from datasets import load_dataset;

print("----------------------------------")
print(try_to_load_from_cache("t5-small", "config.json"))  # 检查模型下载状态

# 检查处理后的数据集结构
print("----------------------------------")
print(load_dataset('samsum')['train'][0])

# 检查模型输入兼容性
model = AutoModelForSeq2SeqLM.from_pretrained('t5-small');
print("----------------------------------")
print(model.get_input_embeddings())

# end
print("\n----------------------------------")
print("end")
