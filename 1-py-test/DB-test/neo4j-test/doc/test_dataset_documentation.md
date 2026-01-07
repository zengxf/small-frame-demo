# Neo4j 测试数据集说明
- https://chatglm.cn/main/alltoolsdetail?lang=zh&cid=695e392bfc159c9685898834

## 数据集概述

本数据集模拟了一个社交网络，包含以下实体和关系：

- **人员**: 20个不同年龄、城市的人员
- **公司**: 4个不同行业的公司
- **技能**: 10种技术技能
- **兴趣**: 10种兴趣爱好

## 关系类型

| 关系类型 | 描述 | 属性 |
|---------|------|------|
| `FRIEND` | 朋友关系 | `since` (建立时间) |
| `WORKS_AT` | 工作关系 | `position` (职位), `since` (入职时间) |
| `HAS_SKILL` | 拥有技能 | `level` (技能等级) |
| `HAS_INTEREST` | 拥有兴趣 | `intensity` (兴趣强度 1-10) |

## 数据统计

- 人员数量: 20
- 公司数量: 4
- 技能数量: 10
- 兴趣数量: 10
- 朋友关系数量: 60
- 工作关系数量: 20
- 技能关系数量: 59
- 兴趣关系数量: 69

## 快速开始

### 1. 导入数据

在 Neo4j Browser 中执行以下步骤：

1. 打开 Neo4j Browser (通常在 `http://localhost:7474`)
2. 复制 `create_test_data.cypher` 文件的内容
3. 粘贴到查询编辑器中
4. 点击执行按钮（▶️）

或者使用命令行工具：
```bash
# cypher-shell -u neo4j -p abcd1234 < create_test_data.cypher
cypher-shell -u neo4j -p abcd1234 -d neo4j
```

### 2. 验证数据导入

```cypher
-- 检查节点数量
MATCH (n) RETURN labels(n) AS label, count(n) AS count

-- 检查关系数量
MATCH ()-[r]->() RETURN type(r) AS relationship, count(r) AS count

-- 查看图的整体结构
CALL db.schema.visualization()
```

## 示例查询

### 基础查询

#### 查找所有人员
```cypher
MATCH (p:Person)
RETURN p.name, p.age, p.city
ORDER BY p.name
```

#### 查找特定人员的朋友
```cypher
MATCH (p:Person {name: 'Alice'})-[:FRIEND]->(f:Person)
RETURN f.name, f.age, f.city
```

### 高级查询

#### 查找共同兴趣的人员
```cypher
MATCH (p1:Person)-[:HAS_INTEREST]->(i:Interest)<-[:HAS_INTEREST]-(p2:Person)
WHERE p1.name < p2.name
WITH p1, p2, collect(i.name) AS shared_interests
WHERE size(shared_interests) >= 2
RETURN p1.name, p2.name, shared_interests
```

#### 推荐系统：根据技能和兴趣推荐朋友
```cypher
MATCH (me:Person {name: 'Alice'})
MATCH (me)-[:HAS_SKILL]->(:Skill)<-[:HAS_SKILL]-(other:Person)
WHERE NOT (me)-[:FRIEND]->(other) AND me <> other
WITH other, count(*) AS common_skills
ORDER BY common_skills DESC
LIMIT 5
RETURN other.name, other.city, common_skills
```

## 性能优化

该数据集已包含以下索引和约束：

```cypher
-- 人员姓名索引
CREATE INDEX person_name_idx IF NOT EXISTS FOR (p:Person) ON (p.name)

-- 人员城市索引
CREATE INDEX person_city_idx IF NOT EXISTS FOR (p:Person) ON (p.city)

-- 人员年龄索引
CREATE INDEX person_age_idx IF NOT EXISTS FOR (p:Person) ON (p.age)

-- 人员ID唯一约束
CREATE CONSTRAINT person_id_unique IF NOT EXISTS FOR (p:Person) REQUIRE p.id IS UNIQUE

-- 人员邮箱唯一约束
CREATE CONSTRAINT person_email_unique IF NOT EXISTS FOR (p:Person) REQUIRE p.email IS UNIQUE
```

## 图结构可视化

```
Person -[FRIEND]-> Person
  |
  +-[WORKS_AT]-> Company
  |
  +-[HAS_SKILL]-> Skill
  |
  +-[HAS_INTEREST]-> Interest
```

## 注意事项

1. 该数据集会先删除所有现有数据 (`MATCH (n) DETACH DELETE n)`
2. 朋友关系是双向的，即如果 A 是 B 的朋友，那么 B 也是 A 的朋友
3. 技能等级包括：初级、中级、高级、专家
4. 兴趣强度范围为 1-10，数字越大表示兴趣越强烈
5. 所有时间戳使用 ISO 8601 格式 (YYYY-MM-DD)

## 扩展建议

1. 添加更多节点和关系以测试查询性能
2. 尝试使用 `MERGE` 来更新现有数据而不是创建重复数据
3. 使用 `PROFILE` 关键字分析查询性能
4. 尝试编写更复杂的查询，如：
   - 查找最短路径
   - 社区检测算法
   - 中心性分析
   - 推荐系统算法