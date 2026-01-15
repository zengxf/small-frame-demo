from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# ---------- 插入实体 ----------
data = [
    {"my_id": 110, "my_vector": [0.35, -0.60, 0.18, 1, 1], "my_varchar": "pink_8682"}
]
res = client.insert(collection_name="quick_setup", data=data)
print(res)

# ---------- 插入实体 (指定分区) ----------
data = [
    {"my_id": 120, "my_vector": [0.35, -0.60, 0.18, 1, 1], "my_varchar": "pink_8682"}
]
res = client.insert(collection_name="quick_setup", partition_name="partitionA", data=data)
print(res)

# ---------- 插入实体 (带动态字段) ----------
data = [{
    "my_id": 130, "my_vector": [0.35, -0.60, 0.18, 1, 2], "my_varchar": "pink_8682",
    "z_test1": 122, "z_test2": "bbb", "z-test-3": True  # 动态字段
}]
res = client.insert(collection_name="quick_setup", data=data)
print(res)
