"""
连接管理
"""

from neo4j import GraphDatabase
import logging

# 配置日志
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# 数据库连接配置
neo4j_config = {
    "uri": "bolt://localhost:7687",  # 本地 Neo4j 默认地址
    "user": "neo4j",  # 默认用户名
    "password": "abcd1234"  # 替换为你的密码
}


class Neo4jManager:
    """Neo4j 数据库管理类"""

    def __init__(self):
        self.uri = neo4j_config["uri"]
        self.user = neo4j_config["user"]
        self.password = neo4j_config["password"]
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

    def execute_query(self, query, parameters=None):
        """执行查询并返回结果"""
        with self.driver.session() as session:
            result = session.run(query, parameters or {})
            return [record for record in result]

    def close(self):
        """关闭连接"""
        if self.driver:
            self.driver.close()
            logger.info("数据库连接已关闭")


def execute(query, parameters=None):
    # 创建管理器实例
    manager = Neo4jManager()

    try:
        # 连接数据库
        if not manager.connect():
            logger.error("连接数据库出错")
            return False

        res = manager.execute_query(query, parameters)
        return res

    except Exception as e:
        print(f"发生错误: {e}")
        return False

    finally:
        # 关闭连接
        manager.close()