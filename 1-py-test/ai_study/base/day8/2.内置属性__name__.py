''' __name__ 模块的内置属性，控制 main 执行 '''
'''
1. import 模块

2. PI = 3.14       # 常量命名全大写

3. def 函数():       # 函数命名下划线，全小写 user_name
    pass

3. class 类():      # 类命名使用驼峰法，单词的首字母 UserName
    pass

4. if __name__=='__main__':
    执行的主程序
    关注的是，主程序中等号前面的变化
    name = 'yoyo'
    res = XXX()      # 函数()-函数调用、 类()-实例对象
    2 层 for嵌套循环
'''

def add_(x,y):
    return x+y

print(__name__)

if __name__ == '__main__':
    # main 下面的代码，只能自己用。别人可以使用自定义函数
    print(add_(3,5))