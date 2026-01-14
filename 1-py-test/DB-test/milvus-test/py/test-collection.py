from pymilvus import MilvusClient, DataType

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# --------- 创建 ---------
schema = MilvusClient.create_schema(auto_id=False, enable_dynamic_field=True)
schema.add_field(field_name="my_id", datatype=DataType.INT64, is_primary=True)
schema.add_field(field_name="my_vector", datatype=DataType.FLOAT_VECTOR, dim=5)
schema.add_field(field_name="my_varchar", datatype=DataType.VARCHAR, max_length=512)

index_params = client.prepare_index_params()
index_params.add_index(field_name="my_id", index_type="AUTOINDEX")
index_params.add_index(field_name="my_vector", index_type="AUTOINDEX", metric_type="COSINE")

client.create_collection(collection_name="my_collection", schema=schema, index_params=index_params)

# --------- 查看加载状态 ---------
res = client.get_load_state(collection_name="my_collection")
print(res)

client.release_collection(collection_name="my_collection")
res = client.get_load_state(collection_name="my_collection")
print(res)
