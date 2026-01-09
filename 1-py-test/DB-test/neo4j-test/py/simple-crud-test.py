"""
AI 示例：
https://chatglm.cn/main/alltoolsdetail?lang=zh&cid=695b959e4886e1c88a378a6e
"""

import logging

from neo4j import GraphDatabase

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

    def create_person(self, name, age, email=None):
        """创建人员节点"""
        query = """
        CREATE (p:Person {
            name: $name,
            age: $age,
            email: $email,
            created_at: datetime()
        })
        RETURN p
        """
        with self.driver.session() as session:
            result = session.run(query, {
                "name": name,
                "age": age,
                "email": email
            })
            return result.single()

    def create_company(self, name, industry):
        """创建公司节点"""
        query = """
        CREATE (c:Company {
            name: $name,
            industry: $industry,
            created_at: datetime()
        })
        RETURN c
        """
        with self.driver.session() as session:
            result = session.run(query, {
                "name": name,
                "industry": industry
            })
            return result.single()

    def create_works_at_relationship(self, person_name, company_name, position):
        """创建工作关系"""
        query = """
        MATCH (p:Person {name: $person_name})
        MATCH (c:Company {name: $company_name})
        CREATE (p)-[r:WORKS_AT {
            position: $position,
            start_date: date()
        }]->(c)
        RETURN p, r, c
        """
        with self.driver.session() as session:
            result = session.run(query, {
                "person_name": person_name,
                "company_name": company_name,
                "position": position
            })
            return result.single()

    def create_friendship(self, person1_name, person2_name):
        """创建友谊关系"""
        query = """
        MATCH (p1:Person {name: $person1_name})
        MATCH (p2:Person {name: $person2_name})
        CREATE (p1)-[:FRIEND]->(p2)
        CREATE (p2)-[:FRIEND]->(p1)
        RETURN p1, p2
        """
        with self.driver.session() as session:
            result = session.run(query, {
                "person1_name": person1_name,
                "person2_name": person2_name
            })
            return result.single()

    def get_person(self, name):
        """获取指定人员信息"""
        query = """
        MATCH (p:Person {name: $name})
        RETURN p
        """
        with self.driver.session() as session:
            result = session.run(query, {"name": name})
            return result.single()

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

    def update_person_email(self, name, new_email):
        """更新人员邮箱"""
        query = """
        MATCH (p:Person {name: $name})
        SET p.email = $new_email
        RETURN p
        """
        with self.driver.session() as session:
            result = session.run(query, {
                "name": name,
                "new_email": new_email
            })
            return result.single()

    def delete_person(self, name):
        """删除人员节点及其关系"""
        query = """
        MATCH (p:Person {name: $name})
        DETACH DELETE p
        RETURN count(p) AS deleted_count
        """
        with self.driver.session() as session:
            result = session.run(query, {"name": name})
            return result.single()["deleted_count"]

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

    def batch_create_people(self, people_data):
        """批量创建人员"""
        query = """
        UNWIND $people AS person
        CREATE (p:Person {
            name: person.name,
            age: person.age,
            email: person.email,
            created_at: datetime()
        })
        RETURN p
        """
        with self.driver.session() as session:
            result = session.run(query, {"people": people_data})
            return [record["p"] for record in result]


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

        # 清空现有数据（可选）
        logger.info("清空现有数据...")
        # 不清空数据
        # manager.execute_query("MATCH (n) DETACH DELETE n")

        # 创建人员节点
        logger.info("创建人员节点...")
        people = [
            ("张三", 28, "zhangsan@email.com"),
            ("李四", 32, "lisi@email.com"),
            ("王五", 25, "wangwu@email.com"),
            ("赵六", 30, None),
            ("钱七", 27, "qianqi@email.com")
        ]

        for name, age, email in people:
            manager.create_person(name, age, email)
            logger.info(f"创建人员: {name}")

        # 创建公司节点
        logger.info("创建公司节点...")
        companies = [
            ("阿里巴巴", "互联网"),
            ("腾讯", "互联网"),
            ("华为", "通信技术"),
            ("字节跳动", "互联网")
        ]

        for name, industry in companies:
            manager.create_company(name, industry)
            logger.info(f"创建公司: {name}")

        # 创建工作关系
        logger.info("创建工作关系...")
        work_relationships = [
            ("张三", "阿里巴巴", "软件工程师"),
            ("李四", "腾讯", "产品经理"),
            ("王五", "字节跳动", "数据分析师"),
            ("赵六", "华为", "系统架构师"),
            ("钱七", "阿里巴巴", "前端开发")
        ]

        for person_name, company_name, position in work_relationships:
            manager.create_works_at_relationship(person_name, company_name, position)
            logger.info(f"{person_name} 在 {company_name} 担任 {position}")

        # 创建友谊关系
        logger.info("创建友谊关系...")
        friendships = [
            ("张三", "李四"),
            ("李四", "王五"),
            ("王五", "赵六"),
            ("赵六", "钱七"),
            ("钱七", "张三"),
            ("张三", "王五")
        ]

        for person1, person2 in friendships:
            manager.create_friendship(person1, person2)
            logger.info(f"创建友谊: {person1} <-> {person2}")

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

        # 更新操作
        print("\n" + "=" * 50)
        print("6. 更新赵六的邮箱:")
        print("=" * 50)
        updated = manager.update_person_email("赵六", "zhaoliu_new@email.com")
        if updated:
            print(f"更新成功: {updated['p']}")

        # 批量操作演示
        print("\n" + "=" * 50)
        print("7. 批量创建人员:")
        print("=" * 50)
        new_people = [
            {"name": "孙八", "age": 29, "email": "sunba@email.com"},
            {"name": "周九", "age": 31, "email": "zhoujiu@email.com"}
        ]
        created_people = manager.batch_create_people(new_people)
        for person in created_people:
            print(f"创建: {person['name']}")

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
