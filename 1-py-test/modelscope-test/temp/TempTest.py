# --------------------------------------------------
# 测试版本
#
# pip uninstall datasets
# pip install datasets==3.3.0
#
# datasets 3.4.0 的版本没有 get_metadata_patterns 函数
# --------------------------------------------------

from datasets.data_files import get_metadata_patterns

import sentencepiece
print(sentencepiece.__version__)

print("\n---------------------------")

print(type(get_metadata_patterns))

print("---------------------------\n")
