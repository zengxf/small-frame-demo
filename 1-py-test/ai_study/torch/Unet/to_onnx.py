import onnx
import onnxruntime as ort
import numpy as np
import torch
from model import ResUNetSegmentation

batch_size = 1
channels = 3
height = 480
width = 640

dummy_input = torch.randn(batch_size, channels, height, width)
checkpoint_path = r"./checkpoints/best_model.pth"
checkpoint = torch.load(checkpoint_path, map_location='cpu')
model = ResUNetSegmentation()
# 加载模型权重
model.load_state_dict(checkpoint['model_state_dict'])
model.eval()

# 导出 ONNX 模型
torch.onnx.export(
    model,
    dummy_input,
    "./checkpoints/model_1x480x640.onnx",
    export_params=True,
    opset_version=13,  # 推荐使用较高的算子集版本
    do_constant_folding=True,
    input_names=['input'],
    output_names=['output'],
    dynamic_axes={
        'input': {0: 'batch_size'},  # 只允许批量大小动态变化
        'output': {0: 'batch_size'}
    }
)

print("ONNX 模型导出成功！")
