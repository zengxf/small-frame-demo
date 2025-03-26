# ------------------------------------------------------------
# 加载数据集 (ModelScope)
# ------------------------------------------------------------

import addict
import modelscope
import logging
from modelscope.msdatasets import MsDataset

# 设置日志级别 (WARN 级别的不输出)
# -----------------------------------------------
logging.getLogger('modelscope').setLevel(logging.ERROR)

# 查看版本
# -----------------------------------------------
print(addict.__version__)
print(modelscope.__version__)
print("------------------------------------")

# 基础数据加载
# -----------------------------------------------
ds = MsDataset.load('swift/self-cognition', subset_name='default', split='train')
print(ds)

print("---------------------------\n")
