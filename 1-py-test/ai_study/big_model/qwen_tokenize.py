from transformers import AutoModelForCausalLM, AutoTokenizer

model_name = "Qwen/Qwen2.5-0.5B-Instruct"

model = AutoModelForCausalLM.from_pretrained(
    model_name,
    torch_dtype="auto",
    device_map="auto"
)

tokenizer = AutoTokenizer.from_pretrained(model_name)

text_input = "中国是一个伟大的国家"
tokens_input = tokenizer.tokenize(text_input)
print(tokens_input)

print('tokens: ', tokens_input)

tokens_id = tokenizer.encode(text_input, add_special_tokens=True)
print('token_id: ', tokens_id)

text_decoded = tokenizer.convert_tokens_to_string(tokens_input)
print('token_decoded: ', text_decoded)