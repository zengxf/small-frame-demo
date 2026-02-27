"""
PyCharm 有告警，但运行都不会错

https://gemini.google.com/app/0f9eb4d31e2f60f2
https://chatglm.cn/main/alltoolsdetail?lang=zh&cid=69a0f1bb46f8819a41a35701
"""

from typing import TypedDict, Required, NotRequired


# Python 3.11+ 写法
class UserProfile(TypedDict):
    # 使用 Required 显式标记 name 为必须字段
    # 即使下面的 total=False，这个字段也是必须的
    name: Required[str]

    # 不写 Required/NotRequired 时，是否必须取决于 total 参数
    # 这里我们假设默认 total=True，那么下面两个字段默认是必须的
    # 但我们可以用 NotRequired 标记它们为可选

    age: NotRequired[int]
    email: NotRequired[str]


# --- 另一种常见写法 (total=False时的反转) ---

class UserProfileAlt(TypedDict, total=False):
    # 当 total=False 时，默认所有字段都是可选的
    # 此时必须使用 Required 来强制指定 name 为必须字段
    name: Required[str]

    # age 和 email 在这里默认就是可选的 (因为 total=False)
    age: int
    email: str


# 测试代码
def create_user(data: UserProfile) -> None:
    print(f"user created, data 原始：{data}")
    print(f"User created: {data.get('name')}, age: {data.get('age')}, email: {data.get('email')}")


# 合法：name 存在，其他可选
create_user({"name": "Alice"})

# 合法：name 存在，其他也存在
create_user({"name": "Bob", "age": 30, "email": "bob@example.com"})

# 不合法：缺少必须的 name (静态检查会报错)
create_user({"age": 30})  # PyCharm 有告警

# 正确示例
user1: UserProfile = {"name": "coder_zero", "email": "zero@example.com"}
create_user(user1)

# 错误示例（IDE 报错，因为缺少 name）
user2: UserProfile = {"xx": "incomplete_user"}  # PyCharm 有告警
create_user(user2)

user3: UserProfileAlt = {"name": "incomplete_user"}  # 正确示例
user4: UserProfileAlt = {"xx": "incomplete_user"}  # 错误示例 # PyCharm 有告警

print(f"user3: {user3}")
print(f"user4: {user4}")
