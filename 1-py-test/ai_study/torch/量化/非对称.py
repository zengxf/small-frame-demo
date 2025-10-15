import torch

# 生成原始数据，范围从 0 到 10
x = torch.rand(2, 3, dtype=torch.float32)
print("Original tensor (float32):")
print(x)

# ==================================================
# 非对称量化（Asymmetric Quantization）
# ==================================================
# 非对称量化的公式：
# x_q = round(x / scale + zero_point)

# 量化的过程通过 `scale` 和 `zero_point` 来将浮动值映射到整数值。
# 例如，当 `zero_point=128`，则浮动值的 0 映射到整数值 128。
scale_asymmetric = 0.5

# 假设选择128作为零点，这样量化后的值可以在0-255之间
zero_point_asymmetric = 128

xq_asymmetric = torch.quantize_per_tensor(
    x,
    scale=scale_asymmetric,
    zero_point=zero_point_asymmetric,
    dtype=torch.quint8
)

print("\nAsymmetric Quantized tensor:")
print(xq_asymmetric)

# 显示整数表示
print("\nSymmetric Quantized integer representation:")
print(xq_asymmetric.int_repr())  # 显示量化后的整数表示
