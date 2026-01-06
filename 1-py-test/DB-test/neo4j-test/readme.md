# Neo4j 测试

## Neo4j 命名由来
- Neo + 4j
- Neo 含义：源于希腊语前缀 *neo-*，意为“新的”。
- 4j 含义：“for Java” 的谐音缩写。


## 基础环境
- Python 3.14.0


## PyCharm 设置
- Add Python Interpreter
- Environment: `Generate new`
- Type: `Virtualenv` (Virtual env)
- Base Python: `Python 3.14.0 download and install`
- Location: `..\neo4j-test\.venv`
- 不选 `Inherit packages from base interpreter`
- 不选 `Make available to all projects`


## 安装依赖
```shell
pip install -r requirements.txt
```