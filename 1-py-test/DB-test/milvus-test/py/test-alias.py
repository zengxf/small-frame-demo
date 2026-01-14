from pymilvus import MilvusClient, DataType

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

# --------- 删除别名 ---------
client.drop_alias(alias="bob")
client.drop_alias(alias="alice")

# --------- 创建别名 ---------
client.create_alias(collection_name="my_collection_1", alias="bob")
client.create_alias(collection_name="my_collection_1", alias="alice")

# --------- 列出别名 ---------
res = client.list_aliases(collection_name="my_collection_1")
print("coll 1 alias: ", res)

# --------- 描述别名 ---------
res = client.describe_alias(alias="bob")
print("alias 'bob' desc: ",res)

# --------- 更改别名 ---------
# 将已分配给特定集合的别名重新分配给另一个集合
client.alter_alias(collection_name="my_collection_2", alias="alice")
res = client.list_aliases(collection_name="my_collection_1")
print("coll 1 alias: ", res)
res = client.list_aliases(collection_name="my_collection_2")
print("coll 2 alias: ", res)
