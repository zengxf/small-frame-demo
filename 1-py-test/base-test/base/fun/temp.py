def divide(x, y):
    if y == 0:
        # raise ZeroDivisionError('除数不能为 0') # 特定异常
        raise Exception('除数不能为 0')  # 通常使用万能异常
    return x / y


# result = divide(10, 0)
try:
    result = divide(10, 0)
except Exception as e:
    print(f'程序异常： {e}')
