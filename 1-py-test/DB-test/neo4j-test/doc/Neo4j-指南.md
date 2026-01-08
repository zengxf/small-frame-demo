## ref
- https://chatgpt.com/c/695b95a2-01ec-8323-9dcb-4f18374396c9


## 概念
### 数据结构
- 标签（Label）：类似“表名”或“实体类型”
- 节点（Node）：类似“表中的一行”
- 关系（Relationship）：表示节点之间的连接
- 属性（Property）：节点或关系上的字段（类似表的列）


## 语法
- 它不使用 SQL，而是有自己专门的查询语言：Cypher（读作 “sai-fer”）。
- Cypher 是为图而设计的查询语言，语法直观且类 SQL。

### SQL vs Cypher 对照示例
| 功能       | SQL 写法                                          | Neo4j Cypher 写法                             |
| -------- | ----------------------------------------------- | ------------------------------------------- |
| 查询所有记录   | `SELECT * FROM Person;`                         | `MATCH (p:Person) RETURN p;`                |
| 条件查询     | `SELECT * FROM Person WHERE name='Alice';`      | `MATCH (p:Person {name:'Alice'}) RETURN p;` |
| 模糊查询     | `WHERE name LIKE 'A%'`                          | `WHERE p.name STARTS WITH 'A'`              |
| 计数       | `SELECT COUNT(*) FROM Person;`                  | `MATCH (p:Person) RETURN count(p);`         |
| 排序       | `ORDER BY age DESC`                             | `ORDER BY p.age DESC`                       |
| 限制条数     | `LIMIT 5`                                       | `LIMIT 5`                                   |
| 连接（JOIN） | `FROM Person p JOIN Movie m ON p.movie_id=m.id` | `MATCH (p:Person)-[:ACTED_IN]->(m:Movie)`   |


## Cypher 语法
- https://chatglm.cn/main/alltoolsdetail?lang=zh&cid=695e392bfc159c9685898834

### Cypher 查询结构
```mermaid
flowchart TD
    A[Cypher 查询结构] --> B[MATCH<br>模式匹配与筛选]
    A --> C[WHERE<br>添加过滤条件]
    A --> D[WITH<br>处理与传递数据]
    A --> E[RETURN<br>返回结果]
    A --> F[CREATE<br>创建节点与关系]
    A --> G[MERGE<br>匹配或创建]
    A --> H[SET/REMOVE<br>更新属性]
    A --> I[DELETE/DETACH DELETE<br>删除节点与关系]
    
    B --> J[核心查询与过滤]
    C --> J
    D --> K[中间数据处理]
    E --> L[结果输出]
    F --> M[数据写入]
    G --> M
    H --> N[数据更新]
    I --> O[数据删除]
```

### 示例
```cypher
// 1. 清理现有数据（可选）
MATCH (n) DETACH DELETE n;

// 2. 创建公司节点
CREATE (:Company {name: 'TechCorp', industry: '科技'});

// 3. 创建人员节点
CREATE (:Person {id: 1, name: 'Alice', age: 29, city: '北京'});

// 4. 创建技能节点
CREATE (:Skill {name: 'Python'});

// 5. 创建人员与技能的关系
MATCH (p:Person {id: 1}), (s:Skill {name: 'Rust'}) 
CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);

// 6. 创建 WORKS_AT 关系
MATCH (p:Person {id: 1}), (c:Company {name: 'TechCorp'}) 
CREATE (p)-[:WORKS_AT {position: '工程师', since: '2023-09-01'}]->(c);

// 7. 创建 FRIEND 关系
// 添加相互的朋友关系（双向关系）
MATCH (a:Person {id: 1}), (b:Person {id: 19}) 
CREATE (a)-[:FRIEND {since: '2019-11-01'}]->(b), 
       (b)-[:FRIEND {since: '2019-11-01'}]->(a);

// 8. 创建索引和约束（提升查询性能）
CREATE INDEX person_name_idx IF NOT EXISTS FOR (p:Person) ON (p.name);
```