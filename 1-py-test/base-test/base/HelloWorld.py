# ****************************
# 简单示例
# ****************************

# 最简写法（适用于Python 3.x）
message = "Hello World!"  # 定义字符串变量
print(message)
print()

# ****************************
# Map 类型
languages = {
    "中文": "你好，世界！",
    "日语": "こんにちは世界",
    "法语": "Bonjour le monde"
}

for lang, text in languages.items():
    print(f"{lang}: {text}")

print()

# ****************************
# 交互式
name = input("请输入您的名字：")
print(f"Hello {name}! Welcome to Python!")
