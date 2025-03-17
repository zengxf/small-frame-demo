from transformers import pipeline

# 文本分类（情感分析）
# ------------------------------------------
print("\n------------------------------- 1")
# 加载预训练的情感分析模型
classifier = pipeline("text-classification", model="distilbert-base-uncased-finetuned-sst-2-english")
# 输入文本
text = "I love using Transformers! It's so easy and powerful."
# 预测情感
result = classifier(text)
print(result)  # 输出：[{'label': 'POSITIVE', 'score': 0.9998}]

# 问答系统
# ------------------------------------------
print("\n------------------------------- 2")
qa_pipeline = pipeline("question-answering", model="bert-large-uncased-whole-word-masking-finetuned-squad")
context = "Transformers are deep learning models designed to process sequential data. They were introduced by Google in 2017."
question = "When were Transformers introduced?"
answer = qa_pipeline(question=question, context=context)
print(answer)  # 输出：{'answer': '2017', 'score': 0.98}
