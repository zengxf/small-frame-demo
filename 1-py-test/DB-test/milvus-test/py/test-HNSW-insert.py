coll_name = "hnsw_example"

from pymilvus import MilvusClient

# client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")
client = MilvusClient(uri="http://localhost:19530", token="root:Milvus", db_name="test1")

# --------- 插入数据 ---------
data = [
    {"my_id": 0, "my_vector": [0.35803, -0.60234, 0.18414, -0.26286, 0.90294], "my_varchar": "pink_8682"},
    {"my_id": 1, "my_vector": [0.19886, 0.06023, 0.69769, 0.26144, 0.83872], "my_varchar": "red_7025"},
    {"my_id": 2, "my_vector": [0.43742, -0.5597, 0.64578, 0.78940, 0.20785], "my_varchar": "orange_6781"},
    {"my_id": 3, "my_vector": [0.31720, 0.9719, -0.36981, -0.4860, 0.95791], "my_varchar": "pink_9298"},
    {"my_id": 4, "my_vector": [0.44523, -0.8757, 0.82207, 0.464062, 0.30337], "my_varchar": "red_4794"},
    {"my_id": 5, "my_vector": [0.98582, -0.8144, 0.62992, 0.1206, -0.1446], "my_varchar": "yellow_4222"},
    {"my_id": 6, "my_vector": [0.83719, -0.0157, -0.31062, -0.5626, -0.8984], "my_varchar": "red_9392"},
    {"my_id": 7, "my_vector": [-0.3344, -0.2567, 0.89875, 0.94029, 0.53780], "my_varchar": "grey_8510"},
    {"my_id": 8, "my_vector": [0.39524, 0.40002, -0.5890, -0.8650, -0.6140], "my_varchar": "white_9381"},
    {"my_id": 9, "my_vector": [0.57182, 0.24070, -0.3737, -0.0672, -0.6980], "my_varchar": "purple_4976"}
]

res = client.insert(collection_name=coll_name, data=data)

print(res)
