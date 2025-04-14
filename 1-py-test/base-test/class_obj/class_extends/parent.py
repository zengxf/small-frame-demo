"""
父类
"""


class Parent(object):
    def test_p1(self):
        print("p ok1", self.__class__)


class Parent2(object):
    def test_p2(self):
        print("p ok2", self.__class__)

# p = Parent()
# p.test()
