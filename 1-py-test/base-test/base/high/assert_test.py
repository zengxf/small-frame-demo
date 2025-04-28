"""
assert 断言函数，主动抛出异常

assert 断言函数，主要用于进行调试和测试，它允许你在程序运行时检查某个条件是否为真。
 如果条件为真，继续执行代码。
 否则 assert 会引发一个 AssertionError 异常

基本格式：assert 条件表达式, 条件为假的错误描述
"""


def user_info(name, pwd):
    assert name.isidentifier(), '不符合标识符的规则'
    assert len(pwd) > 6 and pwd.isalnum(), '密码长度必须 6 位，且字母 + 数字'
    print(f'{name} 用户的密码是: {pwd}')


# user_info('asd+%^&','1227713') # 直接抛出异常
try:
    user_info('asd+%^&', '1227713')
except Exception as e:
    print(f'程序异常：{e}')

try:
    user_info('admin', '1227')
except Exception as e:
    print(f'程序异常：{e}')

try:
    user_info('admin', '12dfd27')
except Exception as e:
    print(f'程序异常：{e}')
