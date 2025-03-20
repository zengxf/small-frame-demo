# ------------------------------
# Yaml 文件读写
# ------------------------------

import yaml

# 读取 YAML 文件
print("------------------------------- 1")
config_path = '0test.yaml'
with open(config_path, 'r', encoding='utf-8') as f:
    config = yaml.safe_load(f)
    print(config)
print(config)

# 从字符串中读取 (对象)
print("------------------------------- 2")
document = """
a: 1
b:
  c: 3
  d: 4
"""
data = yaml.safe_load(document)
print(data)
print("------------------------------- ")
dump = yaml.safe_dump(data)
print(dump)

# 从字符串中读取 (数组)
print("------------------------------- 2")
document = """
employees:
  - name: "Alice"
    skills: ["Python", "SQL"]
  - name: "Bob"
    skills: ["JavaScript", "React"]
"""
data = yaml.safe_load(document)
print(data)
print("------------------------------- ")
dump = yaml.safe_dump(data)
print(dump)
