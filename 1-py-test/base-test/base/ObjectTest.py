"""
创建类对象测试
"""

import oop.Main
import class_obj.Person

print('\n--------------------------------')
main = oop.Main.Main()  # 需要同时指定模块名和类名
main.test()
main.print()

print('\n--------------------------------')
alice = class_obj.Person.Person("XX", 66)
print(alice.greet())
