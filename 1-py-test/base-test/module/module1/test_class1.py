"""
测试类 1
"""


class TestClass1:
    def __init__(self, k1=22):
        self.k1 = k1
        self.k2 = 'bb'

    def test(self):
        print("-----------------------")
        print('k1', self.k1)
        print('k2', self.k2)
