"""
AI 示例：
https://chatglm.cn/main/alltoolsdetail?lang=zh&cid=695b959e4886e1c88a378a6e
"""

from neo4j import GraphDatabase
import logging

# 配置日志
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


class Neo4jManager:
    """Neo4j 数据库管理类"""

    def __init__(self, uri, user, password):
        self.uri = uri
        self.user = user
        self.password = password
        self.driver = None

    def connect(self):
        """连接到 Neo4j 数据库"""
        try:
            self.driver = GraphDatabase.driver(
                self.uri,
                auth=(self.user, self.password)
            )
            self.driver.verify_connectivity()
            logger.info("成功连接到 Neo4j 数据库")
            return True
        except Exception as e:
            logger.error(f"连接失败: {e}")
            return False

    def close(self):
        """关闭连接"""
        if self.driver:
            self.driver.close()
            logger.info("数据库连接已关闭")

    def execute_query(self, query, parameters=None):
        """执行查询并返回结果"""
        with self.driver.session() as session:
            result = session.run(query, parameters or {})
            return [record for record in result]

    def get_all_people(self):
        """获取所有人员"""
        query = "MATCH (p:Person) RETURN p ORDER BY p.name"
        return self.execute_query(query)

    def get_company_employees(self, company_name):
        """获取公司所有员工"""
        query = """
        MATCH (p:Person)-[r:WORKS_AT]->(c:Company {name: $company_name})
        RETURN p.name AS name, r.position AS position, p.age AS age
        ORDER BY p.name
        """
        with self.driver.session() as session:
            result = session.run(query, {"company_name": company_name})
            return [{"name": record["name"], "position": record["position"], "age": record["age"]}
                    for record in result]

    def get_friends_of_person(self, person_name):
        """获取某人的朋友"""
        query = """
        MATCH (p:Person {name: $person_name})-[:FRIEND]->(friend:Person)
        RETURN friend.name AS name, friend.age AS age
        ORDER BY friend.name
        """
        with self.driver.session() as session:
            result = session.run(query, {"person_name": person_name})
            return [{"name": record["name"], "age": record["age"]}
                    for record in result]

    def find_shortest_path(self, person1_name, person2_name):
        """查找两人之间的最短路径"""
        query = """
        MATCH path = shortestPath(
            (p1:Person {name: $person1_name})-[*]-(p2:Person {name: $person2_name})
        )
        RETURN [node IN nodes(path) | node.name] AS names,
               length(path) AS hops
        """
        with self.driver.session() as session:
            result = session.run(query, {
                "person1_name": person1_name,
                "person2_name": person2_name
            })
            return result.single()

    def get_recommendations(self, person_name):
        """基于朋友的朋友获取推荐"""
        query = """
        MATCH (p:Person {name: $person_name})-[:FRIEND]->(friend)-[:FRIEND]->(recommended)
        WHERE NOT (p)-[:FRIEND]->(recommended) AND recommended <> p
        RETURN recommended.name AS name, recommended.age AS age, count(*) AS mutual_friends
        ORDER BY mutual_friends DESC
        LIMIT 5
        """
        with self.driver.session() as session:
            result = session.run(query, {"person_name": person_name})
            return [{"name": record["name"], "age": record["age"],
                     "mutual_friends": record["mutual_friends"]}
                    for record in result]

def main():
    """主函数演示各种操作"""

    # 数据库连接配置
    neo4j_config = {
        "uri": "bolt://localhost:7687",  # 本地 Neo4j 默认地址
        "user": "neo4j",  # 默认用户名
        "password": "abcd1234"  # 替换为你的密码
    }

    # 创建管理器实例
    manager = Neo4jManager(**neo4j_config)

    try:
        # 连接数据库
        if not manager.connect():
            return

        # 查询操作演示
        print("\n" + "=" * 50)
        print("1. 查询所有人员:")
        print("=" * 50)
        all_people = manager.get_all_people()
        for record in all_people:
            person = record["p"]
            print(f"姓名: {person['name']}, 年龄: {person['age']}, 邮箱: {person['email']}")

        print("\n" + "=" * 50)
        print("2. 查询阿里巴巴的员工:")
        print("=" * 50)
        alibaba_employees = manager.get_company_employees("阿里巴巴")
        for emp in alibaba_employees:
            print(f"{emp['name']} - {emp['position']} (年龄: {emp['age']})")

        print("\n" + "=" * 50)
        print("3. 查询张三的朋友:")
        print("=" * 50)
        friends = manager.get_friends_of_person("张三")
        for friend in friends:
            print(f"朋友: {friend['name']}, 年龄: {friend['age']}")

        print("\n" + "=" * 50)
        print("4. 查找张三和王五之间的最短路径:")
        print("=" * 50)
        path = manager.find_shortest_path("张三", "王五")
        if path:
            print(f"路径: {' -> '.join(path['names'])}")
            print(f"跳数: {path['hops']}")

        print("\n" + "=" * 50)
        print("5. 为张三推荐朋友:")
        print("=" * 50)
        recommendations = manager.get_recommendations("张三")
        for rec in recommendations:
            print(f"推荐: {rec['name']} (年龄: {rec['age']}, 共同朋友: {rec['mutual_friends']})")

        # 统计查询
        print("\n" + "=" * 50)
        print("8. 数据库统计:")
        print("=" * 50)

        stats_queries = {
            "人员总数": "MATCH (p:Person) RETURN count(p) AS count",
            "公司总数": "MATCH (c:Company) RETURN count(c) AS count",
            "友谊关系数": "MATCH ()-[r:FRIEND]->() RETURN count(r)/2 AS count",
            "工作关系数": "MATCH ()-[r:WORKS_AT]->() RETURN count(r) AS count"
        }

        for desc, query in stats_queries.items():
            result = manager.execute_query(query)
            print(f"{desc}: {result[0]['count']}")

    except Exception as e:
        logger.error(f"发生错误: {e}")
    finally:
        # 关闭连接
        manager.close()


if __name__ == "__main__":
    main()
