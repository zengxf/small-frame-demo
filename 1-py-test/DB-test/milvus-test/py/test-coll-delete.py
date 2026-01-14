coll_name = "my_collection_3"

from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# --------- 删除 ---------
res = client.drop_collection(collection_name=coll_name)
print(f"Delete collection {coll_name} res: {res}")
