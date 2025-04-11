# from test_class1 import TestClass1  # 会报错 >>> ModuleNotFoundError: No module named 'test_class1'
from .test_class1 import TestClass1
from .test_class2 import TestClass2

# __all__ = [TestClass1, TestClass2]    # 用标识符有问题
__all__ = ["TestClass1", "TestClass2"]  # 要用字符串
