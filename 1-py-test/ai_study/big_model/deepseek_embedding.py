import requests
import numpy as np

def deepseek_embedding():
    url = "http://localhost:11434/api/embed"

    # 使用 POST 方法调用 API，并提供参数
    response_post = requests.post(url, json={
        'model': 'MFDoom/deepseek-r1-tool-calling-1.5b',
        'input': ['中国是一个伟大的国家', '人工智能要加油搞']
    })

    print("POST Response:")
    if response_post.status_code == 200:
        embeddings = response_post.json()["embeddings"]
        arr = np.array(embeddings)
        print("embedding shape:", arr.shape)
    else:
        print(f"请求失败：HTTP状态码 - {response_post.status_code}")

if __name__ == "__main__":
    deepseek_embedding()