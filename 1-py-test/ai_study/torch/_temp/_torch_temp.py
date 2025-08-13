import torch

# 创建一个张量
x = torch.tensor([[1, 2, 3], [4, 5, 6]], dtype=torch.float32)

# 创建一个布尔掩码，True 表示需要替换的位置
mask = torch.tensor([[True, False, True], [False, True, False]])

# 使用 masked_fill 替换为 -1
result = x.masked_fill(mask, -1)
print(result)