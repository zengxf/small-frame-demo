"""
TypeAlias 示例
适用版本：Python 3.10+
用途：显式声明一个类型别名。

在 Python 3.10 之前，我们通常直接赋值：Vector = List[float]。这在简单情况下没问题，但在复杂场景下（如递归类型、IDE 提示优化），显式声明更好。

--------------------------------

PyCharm 有告警，但运行都不会错

https://gemini.google.com/app/0f9eb4d31e2f60f2
https://chatglm.cn/main/alltoolsdetail?lang=zh&cid=69a0f1bb46f8819a41a35701
"""

from typing import TypeAlias, List, Dict, Union

# 旧式写法 (隐式别名)
# Vector = List[float]

# 新式写法 (显式别名)
# 这样 IDE 和类型检查器明确知道 Vector 是一个类型的别名，而不是一个普通变量
Vector: TypeAlias = List[float]


def scale(scalar: float, vector: Vector) -> Vector:
    return [scalar * num for num in vector]


# 复杂类型示例
# 定义一个可能是 ID(int) 或 Name(str) 的键，值是浮点数
ConnectionOptions: TypeAlias = Dict[Union[int, str], float]


def configure(options: ConnectionOptions) -> None:
    print("options: ", options)


# 递归类型示例 (TypeAlias 在这里特别有用)
# 定义一个 JSON 结构，它可以是嵌套的
Json: TypeAlias = Union[
    None,
    bool,
    int,
    float,
    str,
    List['Json'],
    Dict[str, 'Json']
]


def parse_json(data: Json) -> None:
    print("data: ", data)


print("scale: ", scale(2, [22, 33]))
print("scale: ", scale(3, ["22-", "33-"]))  # PyCharm 有告警

configure({"aa": 32.3})
configure({"aa": "xx"})  # PyCharm 有告警

parse_json("xxx")
parse_json(["xxx", 222, {"xx": "bbb"}])

# ---------------------------------
# Python 3.12+ 的更新：
#
# 在最新的 Python 3.12 中，引入了 type 关键字，写法更加简洁，完全替代了 TypeAlias：
# ---------------------------------

# Python 3.12+ only
type Vector2 = list[float]
type Json2 = None | bool | int | float | str | list[Json2] | dict[str, Json2]

v1: Vector2 = ["dd", "sxx"]  # PyCharm 有告警
v2: Vector2 = [2, 3, 3.333]
print("v1: ", v1)
print("v2: ", v2)

j1: Json2 = "xxx"
j2: Json2 = {"xxx"}  # j2 类型是 集合 (set)，而不是字典 (dict)。所以 PyCharm 有告警
j3: Json2 = {"xxx": "99999", "i": 3333}
s1: Json2 = {"xxx", "cc", "cc", 99}  # s1 类型是 集合 (set)，而不是字典 (dict)。所以 PyCharm 有告警
print("j1: ", j1)
print("j2: ", j2)
print("j3: ", j3)
print("s1: ", s1)
print("type(s1): ", type(s1))
