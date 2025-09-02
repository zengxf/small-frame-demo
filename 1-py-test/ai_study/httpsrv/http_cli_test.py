"""
pip install requests
"""
import requests

host = r"http://127.0.0.1:5000"

rsp = requests.post(f"{host}/add", json={"a": 3, "b": 4})

print(rsp.status_code)  # 获取响应状态码
print(rsp.text)  # 响应内容
print(rsp.json())  # 响应为字典对象
