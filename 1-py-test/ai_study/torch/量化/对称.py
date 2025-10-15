import torch

# 生成原始数据，范围从 0 到 10
x = torch.rand(2, 3, dtype=torch.float32)
print("Original tensor (float32):")
print(x)

# 对称量化（Symmetric Quantization）
# ==================================================
# 对称量化的公式：
# x_q = round(x / scale)

# 对称量化的 `zero_point` 固定为 0，量化后的整数范围通常是 -128 到 127（对于 8 位量化）。
# scale 是通过数据的最大值和最小值确定的：
# scale = max(abs(min(x)), abs(max(x))) / 127

min_val, max_val = x.min(), x.max()
scale_symmetric = max(abs(min_val), abs(max_val)) / 127  # 确定量化尺度
zero_point_symmetric = 0  # 对称量化的 zero_point 固定为 0

xq_symmetric = torch.quantize_per_tensor(
    x,
    scale=scale_symmetric,
    zero_point=zero_point_symmetric,
    dtype=torch.qint8  # 使用 qint8 支持负数（-128 ~ 127）
)

print('\nscale_symmetric: ', scale_symmetric)

print("\nSymmetric Quantized tensor:")
print(xq_symmetric)

# 显示整数表示
print("\nSymmetric Quantized integer representation:")
print(xq_symmetric.int_repr())  # 显示量化后的整数表示
