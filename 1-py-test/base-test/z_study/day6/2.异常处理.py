''' 函数的格式: '''
import sys

def 函数名(形参1 : int, 形参2 : str) -> list:
    '''
    函数作用: 形参1 后面的类型 int, 只是提示作用,
    :param 形参1: 描述参数
    :param 形参2: 描述参数
    :return: 描述返回值
    '''
    # 代码
    return '返回值'


######## 异常处理: 程序出现异常后, 程序终止, 格式
try:
    '执行的程序'
except '异常类型':
    '出现异常类型, 处理的方法'
except '异常1,异常2':
    '出现异常1,异常2, 处理的方法'
except Exception as e: # BaseException: 万能异常
    print(e.args)
else:
    '没有出现上面异常, 执行的代码'
finally:
    '不管有没有异常必须执行的代码'


####
def fun():
    try:
        num = input('输入数字')
        print(int(num))
        lst = [1,2,3]
        print(lst[5])
    except ValueError:
        print('输入的类型错误')
    except IndexError:
        print('索引错误')
    except Exception:
        print('未知错误')

    print('hello')

#### else 没有异常
# try:
#     print(3/1)
# except ZeroDivisionError:
#     print('除数不能为零')
# else:
#     print('hello')


#### finally 必须执行
# import sys
# try:
#     print(3/1)
# except ZeroDivisionError:
#     print('除数不能为零')
# finally:
#     print('hello')
#     sys.exit(111)


# while True:
#     print('python')
#     sys.exit('游戏结束')


''' raise 主动抛出异常 '''
# num = 3
# num1 = 0
# if num1 ==0:
#     # raise Exception('除数不能为零')
#     raise '除数不能为零'
# else:
#     print(num/num1)


''' 断言 assert 条件表达式 '''
num2 = 10
assert num2>5
print(num2)

def add(x:int, y:str) -> int:
    assert y.isdigit()
    y = int(y)
    return x+y

print(add(3,'a'))