import numpy as np

# 求对数

print('*' * 50)
print(f'np.log(e): {np.log(np.e)}')
# np.log(e): 1.0

print('*' * 50)
print(f'np.log10(100): {np.log10(100)}')
# np.log10(100): 2.0

print('*' * 50)
print(f'np.log2(8): {np.log2(8)}')
# np.log2(8): 3.0

print('*' * 50)
print(f'np.log1p(np.e - 1): {np.log1p(np.e - 1)}')
# 相当于 log1p(x) = ln(1 + x)
# np.log1p(np.e - 1): 1.0
