from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# ---------- 单向量搜索 ----------
query_vector = [0.35, -0.60, 0.18, 1, 1.56]
res = client.search(
    collection_name="quick_setup",
    # anns_field="my_vector",                   # 可以省略
    data=[query_vector],
    limit=3,
    output_fields=["my_varchar", "$meta"],
    # search_params={"metric_type": "IP"}       # 不能随意指定
    # search_params={"metric_type": "COSINE"}   # 默认就是 COSINE，因此可以省略
)

for hits in res:
    print("----------------------")
    for hit in hits:
        print(hit)
