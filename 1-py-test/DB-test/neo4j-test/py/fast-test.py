"""
快速测试
"""

from Neo4jManager import execute

# cmd = "MATCH (c:Company) RETURN count(c) AS count"
# res = execute(cmd)
# print(res)

query = """
        MATCH (p:Person {name: $name})
        SET p.email = $new_email
        """
res = execute(query, {"name": "赵六", "new_email": "zhaoliu_new@email.com"})
print(res)  # 命令没有 return，结果为空

query = """
        MATCH (p:Person {name: $name})
        SET p.email = $new_email
        return p
        """
res = execute(query, {"name": "赵六", "new_email": "zhaoliu_new@email.com"})
print(res)
