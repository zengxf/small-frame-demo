"""
pip install openai requests
"""
import json
import requests
from openai import OpenAI

# ---------- 1. 真实天气函数 ----------
def get_weather(location: str) -> str:
    """
    调用 OpenWeatherMap 实时查询天气，返回一句话描述。
    失败时返回错误信息，保证 LLM 能继续对话。
    """
    api_key = "<your_owm_key>"          # 去 https://home.openweathermap.org/api_keys 免费申请
    url = "https://api.openweathermap.org/data/2.5/weather"
    params = {
        "q": location,
        "appid": api_key,
        "units": "metric",
        "lang": "zh_cn"
    }
    try:
        r = requests.get(url, params=params, timeout=5)
        r.raise_for_status()
        data = r.json()
        desc = data["weather"][0]["description"]        # 多云／晴 …
        temp = data["main"]["temp"]                     # 摄氏度
        return f"{location}当前{desc}，气温{temp:.1f}℃"
    except Exception as e:
        return f"查询{location}天气失败：{e}"

# ---------- 2. 初始化 DeepSeek 客户端 ----------
client = OpenAI(
    api_key="<your_deepseek_key>",
    base_url="https://api.deepseek.com"
)

# ---------- 3. 把函数注册给模型 ----------
tools = [{
    "type": "function",
    "function": {
        "name": "get_weather",
        "description": "Get current weather of a location.",
        "parameters": {
            "type": "object",
            "properties": {
                "location": {"type": "string", "description": "城市中文名或拼音"}
            },
            "required": ["location"]
        }
    }
}]

# ---------- 4. 对话入口 ----------
def chat_with_function(user_query: str):
    messages = [{"role": "user", "content": user_query}]
    # 第一次请求：让模型决定要不要调用函数
    response = client.chat.completions.create(
        model="deepseek-chat",
        messages=messages,
        tools=tools
    )

    # 如果模型没有触发函数调用，直接返回答案
    if not response.choices[0].message.tool_calls:
        return response.choices[0].message.content

    # 解析函数调用
    tool_call = response.choices[0].message.tool_calls[0]
    func_name = tool_call.function.name
    func_args = json.loads(tool_call.function.arguments)

    # 本地执行函数
    if func_name == "get_weather":
        obs = get_weather(func_args["location"])

    # 把函数返回结果追加到对话
    messages.append(response.choices[0].message)            # 助手消息（含 tool_calls）
    messages.append({
        "role": "tool",
        "tool_call_id": tool_call.id,
        "content": obs
    })

    # 第二次请求：让模型用自然语言组织答案
    final = client.chat.completions.create(
        model="deepseek-chat",
        messages=messages,
        tools=tools
    )
    return final.choices[0].message.content

# ---------- 5. 测试 ----------
if __name__ == "__main__":
    print(chat_with_function("深圳现在天气怎么样？"))