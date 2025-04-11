"""
测试类 2
"""


class TestClass2:
    def __init__(self, k1=33):
        self.k1 = k1
        self.k2 = 'dd'

    def test(self):
        print("-----------------------")
        print('k1', self.k1)
        print('k2', self.k2)
