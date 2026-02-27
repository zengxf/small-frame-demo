"""
PyCharm 有告警，但运行都不会错

https://gemini.google.com/app/0f9eb4d31e2f60f2
https://chatglm.cn/main/alltoolsdetail?lang=zh&cid=69a0f1bb46f8819a41a35701
"""


# ---------------------------------
# 1. 基础类型与函数
# ---------------------------------

def greet(name: str, age: int) -> str:
    return f"Hello {name}, you are {age} years old."


print(greet("Alice", 1))
print(greet(33, 'xx'))  # PyCharm 有告警

# ---------------------------------
# 2. 容器类型（List, Dict, Tuple）
# ---------------------------------

from typing import List, Dict, Tuple

# 列表：元素全是整数
numbers: List[int] = [1, 2, 3, "ss"]  # PyCharm 没有告警
print(numbers)
numbers.append('sss')  # PyCharm 有告警
print(numbers)

# 字典：键是字符串，值是整数
scores: Dict[str, int] = {"Alice": 95, "Bob": 88, 1: "ss"}  # PyCharm 没有告警
print(scores)
scores[1] = 'a'  # PyCharm 有告警
print(scores)

# 元组：特定位置的类型
user_info: Tuple[str, int, bool] = ("Admin", 1, True, 1.2)  # PyCharm 有告警
# user_info: Tuple[str, int, bool] = ("Admin", 1, True)
print(user_info)

# ---------------------------------
# 3. 可选类型 (Optional) 与 联合类型 (Union)
# ---------------------------------

from typing import Union, Optional


# Union: 变量可以是多种类型之一
def process_data(data: Union[int, str]):
    print(f"process_data: {data}")


# Python 3.10+ 简化写法 (推荐):
def process_data2(data: int | str):
    print(f"process_data 2: {data}")


# Optional: 等同于 Union[str, None]，表示可能返回字符串，也可能返回 None
def find_user(user_id: int) -> Optional[str]:
    if user_id == 1:
        return "Admin"
    if user_id == 2:
        return 88  # PyCharm 有告警
    return None


# Python 3.10+ 简化写法 (推荐):
def find_user2(user_id: int) -> str | None:
    if user_id == 1:
        return "Admin"
    if user_id == 2:
        return 88  # PyCharm 有告警
    return None


process_data("ss")
process_data(True)  # 相当于 int，无告警
process_data(333.33)  # PyCharm 有告警
process_data2(333.33)  # PyCharm 有告警

# ---------------------------------
# 4. 任意类型 (Any) 与 可调用对象 (Callable) 与 永远不返回 (NoReturn)
# ---------------------------------

from typing import Any, Callable, NoReturn


# Any: 不进行类型检查
def log(value: Any) -> None:
    print(f"Log: {value}")


# Callable: 限制参数为一个函数
# 格式：Callable[[参数类型列表], 返回类型]
def execute_task(task: Callable[[int, int], int], a: int, b: int) -> int:
    res = task(a, b)
    print(f"exe res: {res}")
    return res


def my_fun_add(x: int, y: int) -> int:
    return x + y


def stop_program() -> NoReturn:
    print("stop_program")
    return "stop_program"  # PyCharm 有告警


log("xxxx")
log(3223)
execute_task(my_fun_add, 5, 10)
# execute_task(my_fun_add, 5, 12.2, 'dd')  # PyCharm 有告警，且运行出错
execute_task(my_fun_add, 5, 12.2)  # PyCharm 有告警

# ---------------------------------
# 5. 类型别名 (Type Alias)
# ---------------------------------

from typing import List, Tuple

# 定义别名
Point = Tuple[float, float]
Route = List[Point]


def draw_path(path: Route) -> None:
    for x, y in path:
        print(f"Moving to {x}, {y}")


draw_path([(1, 2), (2, 3)])
# draw_path([(1, 2), (2, 3, 3)])  # PyCharm 没告警，但运行出错
# draw_path((1, 2), (2, 3))  # PyCharm 有告警，且运行出错
# draw_path((1, 2))  # PyCharm 有告警，且运行出错

# ---------------------------------
# 6. 泛型
# ---------------------------------

from typing import TypeVar, Generic

# 定义一个类型变量 T
T = TypeVar('T')


class Box(Generic[T]):
    def __init__(self, content: T):
        self.content = content

    def get_content(self) -> T:
        return self.content


# 使用示例
box_of_int = Box(123)  # 此时 T 被推断为 int
box_of_str = Box("hello")  # 此时 T 被推断为 str

print(box_of_int.get_content() + 1)  # IDE 知道这是 int，允许加法
# print(box_of_str.get_content() + 1)  # PyCharm 有告警，且运行出错
print(box_of_str.get_content() + "-xx")

# ---------------------------------
# 7. TypedDict (结构化字典)
#
# Python 3.8+ 引入，用于定义字典的具体结构（键名和对应的值类型）。
# ---------------------------------

from typing import TypedDict


class Person(TypedDict):
    name: str
    age: int
    is_student: bool


def show_person(p: Person) -> None:
    print(f"show_person {p['name']} is {p['age']} years old. is_student: {p.get('is_student')}")


# 必须包含定义的所有字段，且类型正确
person_data: Person = {"name": "Alice", "age": 25, "is_student": False}
show_person(person_data)
person_data2: Person = {"name": "Alice", "age": 25}  # PyCharm 有告警
show_person(person_data2)

# ---------------------------------
# 8. Literal (字面量类型)
#
# Python 3.8+ 引入，用于限制参数只能是特定的几个值之一。
# ---------------------------------

from typing import Literal


def set_mode(mode: Literal['r', 'w', 'x']) -> None:
    print(f"Mode set to: {mode}")


set_mode('r')  # 正确
set_mode('w')  # 正确
set_mode('a')  # PyCharm 有告警，因为 'a' 不在定义中
