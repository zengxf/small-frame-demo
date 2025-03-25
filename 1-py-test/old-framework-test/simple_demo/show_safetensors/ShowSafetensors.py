# --------------------------------------
# 对 safetensors 文件，可视化展示
# 但只能对部分可视
# --------------------------------------

from safetensors import safe_open
import matplotlib.pyplot as plt

# 加载 Safetensors 文件
model_file = "D:/Data/AI/model/gpt2.safetensors"
tensors = {}
with safe_open(model_file, framework="pt") as f:
    for key in f.keys():
        tensors[key] = f.get_tensor(key)

# 可视化某个张量的统计信息（如直方图）
weight = tensors['h.0.ln_1.weight'].flatten().cpu().numpy()
plt.hist(weight, bins=50)
plt.title("Weight Distribution 1")
plt.show()

# 可视化某个张量的统计信息（如直方图）
weight = tensors['h.5.mlp.c_proj.bias'].flatten().cpu().numpy()
plt.hist(weight, bins=50)
plt.title("Weight Distribution 2")
plt.show()
