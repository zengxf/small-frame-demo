from modelscope.pipelines import pipeline
from modelscope.utils.constant import Tasks

# 初始化管道
text_pipeline = pipeline(
    task=Tasks.text_generation,
    model='Qwen/Qwen2.5-0.5B-Instruct',
    device_map='auto'
)

# 定义对话历史
messages = [
    {"role": "system", "content": "你是一个人工智能助手，用简洁的中文回答。"},
    {"role": "user", "content": "如何预防感冒？"}
]

# 生成回复
response = text_pipeline(
    messages=messages,
    max_length=256,
    temperature=0.8
)
print("助手:", response["text"])

# 添加新问题并继续对话
messages.append({"role": "assistant", "content": response["text"]})
messages.append({"role": "user", "content": "如果已经感冒了怎么办？"})

new_response = text_pipeline(
    messages=messages,
    max_length=512
)
print("助手:", new_response["text"])
