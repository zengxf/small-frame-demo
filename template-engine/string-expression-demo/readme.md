# 字符串表达式解析 Demo

## jexl3

- 支持 Null-Safe（空安全）
    - 不需要在表达式上显示写明

## mvel2

- 支持 Null-Safe（空安全）
    - 但需要在表达式上显示写明
    - 如：`$root.?user.name`

## aviator

- 其目标是一门脚本语言
- 不支持 Null-Safe（空安全）
- 不支持 `map.put(x, y)` 这种操作