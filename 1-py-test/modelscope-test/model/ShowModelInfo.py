from modelscope.pipelines import pipeline
from modelscope.utils.constant import Tasks

# 创建一个文本生成管道
text_pipeline = pipeline(
    task=Tasks.text_generation,
    model='Qwen/Qwen2.5-0.5B-Instruct'
)


# # 获取模型信息
# model_info = text_pipeline.model_info
#
# # 打印模型信息
# print(f"模型名称: {model_info['name']}")
# print(f"模型 ID: {model_info['id']}")
# print(f"模型描述: {model_info['description']}")


def chat():
    print("开始对话（输入 '退出' 来结束对话）:")
    while True:
        user_input = input("你: ")
        if user_input.strip().lower() in ['退出', 'exit', 'quit']:
            print("对话结束。")
            break
        # 使用管道生成回复
        response = text_pipeline(user_input)
        print(f"AI: {response['output']}")


if __name__ == "__main__":
    chat()
