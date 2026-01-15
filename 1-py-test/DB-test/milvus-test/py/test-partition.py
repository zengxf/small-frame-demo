from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# --------- 查看分区 ---------
res = client.list_partitions(collection_name="my_collection")
print(res)

# --------- 创建分区 ---------
client.create_partition(collection_name="my_collection", partition_name="partitionA")
res = client.list_partitions(collection_name="my_collection")
print(res)

# --------- 加载分区 ---------
client.load_partitions(collection_name="my_collection", partition_names=["partitionA"])
res = client.get_load_state(collection_name="my_collection", partition_name="partitionA")
print(res)

# --------- 释放分区 ---------
client.release_partitions(collection_name="my_collection", partition_names=["partitionA"])
res = client.get_load_state(collection_name="my_collection", partition_name="partitionA")
print(res)

# --------- 删除分区 ---------
client.drop_partition(collection_name="my_collection", partition_name="partitionA")
res = client.list_partitions(collection_name="my_collection")
print(res)
