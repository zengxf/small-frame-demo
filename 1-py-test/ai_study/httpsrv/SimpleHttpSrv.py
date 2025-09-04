"""
pip install Flask  --proxy http://127.0.0.1:7890

# Get 测试
curl http://localhost:5000/
# 404
curl http://localhost:5000/hello
# => {"msg":"Hello, Flask!"}


# Post 测试
curl -X POST http://127.0.0.1:5000/add \
     -H "Content-Type: application/json" \
     -d '{"a":3,"b":4}'
# => {"result":7.0}
"""

from flask import Flask, request, jsonify

# 1. 创建 Flask 应用
app = Flask(__name__)

# 2. 定义路由与视图函数
@app.route('/hello', methods=['GET'])
def hello():
    """GET /hello  ->  {"msg": "Hello, Flask!"}"""
    return jsonify(msg="Hello, Flask!")

@app.route('/add', methods=['POST'])
def add():
    """POST /add  接收 JSON {"a": 1, "b": 2}  ->  {"result": 3}"""
    data = request.get_json(force=True, silent=True) or {}
    a = data.get('a', 0)
    b = data.get('b', 0)
    try:
        result = float(a) + float(b)
    except (TypeError, ValueError):
        return jsonify(error="参数 a、b 必须为数字"), 400
    return jsonify(result=result)

# 3. 启动服务
if __name__ == '__main__':
    # debug=True 代码改动后自动重启；host='0.0.0.0' 允许局域网访问
    app.run(host='0.0.0.0', port=5000, debug=True)