"""
参考官网示例：
https://qdrant.tech/documentation/quickstart/
"""

# ---------
"""
连接初始化
"""
import time

start = time.perf_counter()

from qdrant_client import QdrantClient  # 这个导入用时 12 秒左右

client = QdrantClient(url="http://localhost:6333")

print('init client:', client)

end = time.perf_counter()
print(f"导入 + 初始化，耗时: {end - start:.3f} 秒")

# ---------
"""
创建集合
"""
from qdrant_client.models import Distance, VectorParams

coll_name = "test001"
exists = client.collection_exists(collection_name=coll_name)

if not exists:
    res = client.create_collection(
        collection_name=coll_name,
        vectors_config=VectorParams(size=4, distance=Distance.DOT),
    )
    print(f'{coll_name} not exists, create res:', res)
else:
    print(f'{coll_name} existed')

# ---------
"""
添加向量
"""
from qdrant_client.models import PointStruct

operation_info = client.upsert(
    collection_name=coll_name,
    wait=True,
    points=[
        PointStruct(id=1, vector=[0.05, 0.61, 0.76, 0.74], payload={"city": "Berlin"}),
        PointStruct(id=2, vector=[0.19, 0.81, 0.75, 0.11], payload={"city": "London"}),
        PointStruct(id=3, vector=[0.36, 0.55, 0.47, 0.94], payload={"city": "Moscow"}),
        PointStruct(id=4, vector=[0.18, 0.01, 0.85, 0.80], payload={"city": "New York"}),
        PointStruct(id=5, vector=[0.24, 0.18, 0.22, 0.44], payload={"city": "Beijing"}),
        PointStruct(id=6, vector=[0.35, 0.08, 0.11, 0.44], payload={"city": "Mumbai"}),
    ],
)

print(f'{coll_name} operation_info:', operation_info)

# ---------
"""
查询
"""
search_result = client.query_points(
    collection_name=coll_name,
    query=[0.2, 0.1, 0.9, 0.7],
    with_payload=False,
    limit=3
).points

print(f'{coll_name} search_result:', search_result)

# ---------
"""
过滤
"""
from qdrant_client.models import Filter, FieldCondition, MatchValue

search_result = client.query_points(
    collection_name=coll_name,
    query=[0.2, 0.1, 0.9, 0.7],
    query_filter=Filter(
        must=[FieldCondition(key="city", match=MatchValue(value="London"))]
    ),
    with_payload=True,
    limit=3,
)

print(f'{coll_name} Filter search_result:', search_result)

for hit in search_result.points:
    result = {
        "id": hit.id,
        "score": hit.score,
        "metadata": hit.payload or {}
    }
    print("result: ", result)
