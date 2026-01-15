from pymilvus import MilvusClient

client = MilvusClient(uri="http://localhost:19530", token="root:Milvus")

data = [
    {"my_id": 30, "my_vector": [0.3580376395471989, -0.6023495712049978, 0.18414012509913835, -0.26286205330961354,
                                0.9029438446296592], "my_varchar": "pink_8682",
     "z_test1": 1, "z_test2": "a"},
    {"my_id": 31, "my_vector": [0.19886812562848388, 0.06023560599112088, 0.6976963061752597, 0.2614474506242501,
                                0.838729485096104], "my_varchar": "red_7025",
     "z_test1": 122, "z_test2": "aaa", "z-test-3": True},
    {"my_id": 32, "my_vector": [0.43742130801983836, -0.5597502546264526, 0.6457887650909682, 0.7894058910881185,
                                0.20785793220625592], "my_varchar": "orange_6781",
     "z_test1": 11, "z_test2": "dda"},
]

res = client.insert(collection_name="quick_setup", data=data)

print(res)
