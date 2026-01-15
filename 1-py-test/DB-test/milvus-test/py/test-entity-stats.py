from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# -------- 查看集合数据量 --------
stats = client.get_collection_stats(collection_name="quick_setup")
print(stats)  # 统计不准

# -------- 查看集合准确行数 --------
res = client.query(collection_name="quick_setup", output_fields=["count(*)"])
print(f"准确行数: {res}")
print(f"准确行数: {res[0]['count(*)']}")
