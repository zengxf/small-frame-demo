from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

res = client.get_load_state(collection_name="my_collection")
print(res)

res = client.get_load_state(collection_name="my_collection", partition_name="_default")
print(res)

res = client.get_load_state(collection_name="my_collection", partition_name="partitionA")
print(res)
