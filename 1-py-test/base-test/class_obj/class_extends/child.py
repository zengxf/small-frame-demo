"""
子类
"""

from parent import Parent


class Child(Parent):
    def test2(self):
        print("ok2", self.__class__)
        self.test()


c = Child()
c.test2()
