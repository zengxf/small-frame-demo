"""
子类
"""

from parent import Parent, Parent2


# 表示 Child 继承 Parent, Parent2
class Child(Parent, Parent2):
    def test2(self):
        print("ok2", self.__class__)
        self.test_p1()
        self.test_p2()
        super().test_p1()   # 显示调用父类方法
        super().test_p2()   # 显示调用父类方法
        print('super', super().__class__)


c = Child()
c.test2()
