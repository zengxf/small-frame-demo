''' 21. 判断 {"name": "yoyo", "age": 20} 用户 yoyo 是否成年（年龄大于18岁），格式输出：{用户}是否成年 '''

d = {"name": "yoyo", "age": 20}
if d['age']>18:
    print('成年')
else:
    print('未成年')

# 简化格式:三元运算没有 elif 只能 if  else
# 1. 格式:  条件True操作 if 条件1 else 条件False操作
print('成年' if d['age']>18 else '未成年')

# 2. 格式:  条件1True操作 if 条件1 else (条件2为真 if 条件2 else 条件2为假)
# 输入密码，长度小于 6 为弱；否则字母开头，不含特殊字符为中；字母开头，含特殊字符为强（使用三元运算）
# pwd = input('请输入密码:')
# if len(pwd)<6:
#     print('弱')
# else:
#     if pwd[0].isalpha() and pwd.isalnum():
#         print('中')
#     else:
#         if pwd[0].isalpha() and not pwd.isalnum():
#             print('强')
#         else:
#             print('弱-必须字母开头')

# input('请输入密码:') 海象运算   变量 := 表达式
# 输入密码,后首先赋值给pwd变量, pwd变量,else
# print('弱' if len(pwd := input('请输入密码:'))<6 else
#       '中' if pwd[0].isalpha() and pwd.isalnum() else
#       '强' if pwd[0].isalpha() and not pwd.isalnum() else '弱-必须字母开头')