from modelscope import snapshot_download

# model_id = "MiniMax/MiniMax-VL-01"    # 文件有点大
model_id = "Qwen/Qwen2.5-0.5B-Instruct"

# 查看缓存快照位置
model_dir = snapshot_download(model_id)
print('缓存快照位置为：', model_dir)
