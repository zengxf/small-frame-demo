"""
参考官网示例：
https://milvus.io/docs/zh/create-collection.md
"""

# ---------
"""
连接初始化
"""
from pymilvus import MilvusClient, DataType

client = MilvusClient(
    uri="http://localhost:19530",
    token="root:Milvus"
)

# ---------
"""
创建 Schema
"""
schema = MilvusClient.create_schema(
    auto_id=False,
    enable_dynamic_field=True,
)
schema.add_field(field_name="my_id", datatype=DataType.INT64, is_primary=True)
schema.add_field(field_name="my_vector", datatype=DataType.FLOAT_VECTOR, dim=5)
schema.add_field(field_name="my_varchar", datatype=DataType.VARCHAR, max_length=512)

# ---------
"""
(可选）设置索引参数
"""
index_params = client.prepare_index_params()

index_params.add_index(
    field_name="my_id",
    index_type="AUTOINDEX"
)

index_params.add_index(
    field_name="my_vector",
    index_type="AUTOINDEX",
    metric_type="COSINE"
)

# ---------
"""
创建 Collections
"""
client.create_collection(
    collection_name="customized_setup_1",
    schema=schema,
    index_params=index_params
)

res = client.get_load_state(
    collection_name="customized_setup_1"
)

print(res)

# ---------
"""
查看 Collections
"""
res = client.list_collections()

print(res)

# ---------
"""
获取 Collection 的详细信息
"""
res = client.describe_collection(
    collection_name="customized_setup_1"
)

print(res)
