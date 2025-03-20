# ------------------------------------------------------------
# 加载数据集 (ModelScope)
# ------------------------------------------------------------

from modelscope.msdatasets import MsDataset

# 基础数据加载
# -----------------------------------------------
ds = MsDataset.load('swift/self-cognition', subset_name='default', split='train')
print(ds)
