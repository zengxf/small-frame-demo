from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# -------- 通过筛选条件删除实体 --------
res = client.delete(collection_name="quick_setup", filter="my_varchar in ['red_7025', 'purple_4976']")
print(res)

# -------- 通过主键删除实体 --------
res = client.delete(collection_name="quick_setup", ids=[18, 19])
print(res)

# -------- 从分区中删除实体 --------
res = client.delete(collection_name="quick_setup", ids=[18, 19], partition_name="partitionA")
print(res)

# -------- 删除所有 --------
res = client.delete(collection_name="quick_setup", filter="my_id > 0")
print(res)
