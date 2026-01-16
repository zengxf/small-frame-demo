coll_name = "hnsw_example"

from pymilvus import MilvusClient

# client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")
client = MilvusClient(uri="http://localhost:19530", token="root:Milvus", db_name="test1")

# --------- 搜索 ---------
query_vector = [2, 2, 2, 2, 2]
results = client.search(
    collection_name=coll_name,
    data=[query_vector],
    params={"ef": 10},  # ef: 搜索时要考虑的邻居数量。
    limit=5,
)

for r in results[0]:
    print(r.id, r.distance)
