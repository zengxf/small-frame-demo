from transformers import AutoModelForCausalLM, AutoTokenizer
import torch

model_name = "Qwen/Qwen2.5-0.5B-Instruct"

model = AutoModelForCausalLM.from_pretrained(
    model_name,
    torch_dtype="auto",
    device_map="auto"
)

tokenizer = AutoTokenizer.from_pretrained(model_name)

text_input = "中国是一个伟大的国家"

# 1. 分词处理
inputs = tokenizer(text_input, return_tensors="pt").to(model.device)
input_ids = inputs.input_ids[0].tolist()
token_list = tokenizer.convert_ids_to_tokens(input_ids)

print("\n== Tokenize 结果 ==")
print(f"原始文本: '{text_input}'")
print(f"Token ID列表: {input_ids}")
print(f"Token列表: {token_list}")
print(f"还原文本: '{tokenizer.convert_tokens_to_string(token_list)}'")

# 2. 嵌入处理
with torch.no_grad():
    # 获取嵌入层输出（词嵌入）
    embeddings = model.get_input_embeddings()(inputs.input_ids)

print("\n== Embedding 结果 ==")
print(f"嵌入向量形状: {embeddings.shape} (序列长度 x 隐藏层维度)")

embedding_dim = model.config.hidden_size
print(f"嵌入向量维度: {embedding_dim}")

# 输出前10个token的嵌入向量（简化显示）
for i in range(min(10, len(token_list))):
    token_embed = embeddings[0, i].cpu().to(torch.float32).numpy()
    print(f"\nToken ({i}): '{token_list[i]}' (ID: {input_ids[i]})")
    print(f"词嵌入前5维: {token_embed[:5].round(4)}...")