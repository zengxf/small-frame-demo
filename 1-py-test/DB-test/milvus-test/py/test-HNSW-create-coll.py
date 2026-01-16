coll_name = "hnsw_example"

from pymilvus import MilvusClient, DataType

# client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")
client = MilvusClient(uri="http://localhost:19530", token="root:Milvus", db_name="test1")

# --------- 创建集合 ---------
schema = MilvusClient.create_schema(auto_id=False, enable_dynamic_field=True)
schema.add_field(field_name="my_id", datatype=DataType.INT64, is_primary=True)
schema.add_field(field_name="my_vector", datatype=DataType.FLOAT_VECTOR, dim=5)
schema.add_field(field_name="my_varchar", datatype=DataType.VARCHAR, max_length=512)

index_params = client.prepare_index_params()
index_params.add_index(field_name="my_id", index_type="AUTOINDEX")
# index_params.add_index(field_name="my_vector", index_type="AUTOINDEX", metric_type="COSINE")
index_params.add_index(
    index_name="idx_vector",  # 要创建的索引的名称
    field_name="my_vector",  # 要索引的向量字段的名称
    index_type="HNSW",  # 要创建的索引类型
    metric_type="COSINE",  # 用于度量相似性的度量类型
    params={
        "M": 64,  # 每个节点可连接的最大邻居数量。
        "efConstruction": 100  # 索引构建过程中考虑连接的候选邻居数量。
    }  # 索引构建参数
)

res = client.create_collection(collection_name=coll_name, schema=schema, index_params=index_params)
print(f"Create collection {coll_name} res: {res}")
