// ====================================
// 创建测试数据 - 社交网络图
// ====================================

// 1. 清理现有数据（可选）
MATCH (n) DETACH DELETE n;

// 2. 创建公司节点
CREATE (:Company {name: 'TechCorp', industry: '科技'});
CREATE (:Company {name: 'DataSoft', industry: '软件'});
CREATE (:Company {name: 'CloudNine', industry: '云计算'});
CREATE (:Company {name: 'WebFlow', industry: '互联网'});

// 3. 创建人员节点
CREATE (:Person {id: 1, name: 'Alice', age: 29, city: '北京', email: 'alice@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 2, name: 'Bob', age: 39, city: '广州', email: 'bob@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 3, name: 'Charlie', age: 36, city: '深圳', email: 'charlie@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 4, name: 'David', age: 28, city: '上海', email: 'david@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 5, name: 'Eve', age: 49, city: '北京', email: 'eve@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 6, name: 'Frank', age: 23, city: '上海', email: 'frank@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 7, name: 'Grace', age: 35, city: '广州', email: 'grace@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 8, name: 'Henry', age: 54, city: '北京', email: 'henry@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 9, name: 'Ivy', age: 34, city: '武汉', email: 'ivy@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 10, name: 'Jack', age: 36, city: '西安', email: 'jack@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 11, name: 'Karen', age: 39, city: '北京', email: 'karen@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 12, name: 'Leo', age: 32, city: '武汉', email: 'leo@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 13, name: 'Mia', age: 43, city: '杭州', email: 'mia@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 14, name: 'Noah', age: 31, city: '广州', email: 'noah@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 15, name: 'Olivia', age: 43, city: '上海', email: 'olivia@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 16, name: 'Peter', age: 27, city: '武汉', email: 'peter@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 17, name: 'Quinn', age: 28, city: '成都', email: 'quinn@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 18, name: 'Rachel', age: 44, city: '杭州', email: 'rachel@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 19, name: 'Sam', age: 24, city: '西安', email: 'sam@example.com', created_at: '2024-01-01'});
CREATE (:Person {id: 20, name: 'Tina', age: 29, city: '武汉', email: 'tina@example.com', created_at: '2024-01-01'});

// 4. 创建技能节点和 HAS_SKILL 关系
// 首先创建所有技能节点
CREATE (:Skill {name: 'Python'});
CREATE (:Skill {name: 'Java'});
CREATE (:Skill {name: 'JavaScript'});
CREATE (:Skill {name: 'Go'});
CREATE (:Skill {name: 'Rust'});
CREATE (:Skill {name: 'SQL'});
CREATE (:Skill {name: 'Cypher'});
CREATE (:Skill {name: 'Docker'});
CREATE (:Skill {name: 'Kubernetes'});
CREATE (:Skill {name: 'AWS'});

// 然后创建人员与技能的关系
MATCH (p:Person {id: 1}), (s:Skill {name: 'Rust'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 1}), (s:Skill {name: 'JavaScript'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 1}), (s:Skill {name: 'SQL'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 1}), (s:Skill {name: 'Python'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 2}), (s:Skill {name: 'Kubernetes'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 2}), (s:Skill {name: 'Rust'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 2}), (s:Skill {name: 'Python'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 3}), (s:Skill {name: 'Java'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 3}), (s:Skill {name: 'AWS'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 3}), (s:Skill {name: 'JavaScript'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 4}), (s:Skill {name: 'SQL'}) CREATE (p)-[:HAS_SKILL {level: '专家'}]->(s);
MATCH (p:Person {id: 4}), (s:Skill {name: 'Go'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 4}), (s:Skill {name: 'Rust'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 4}), (s:Skill {name: 'Docker'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 5}), (s:Skill {name: 'Cypher'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 5}), (s:Skill {name: 'Rust'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 5}), (s:Skill {name: 'Python'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 5}), (s:Skill {name: 'Docker'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 6}), (s:Skill {name: 'Docker'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 6}), (s:Skill {name: 'Kubernetes'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 6}), (s:Skill {name: 'Cypher'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 6}), (s:Skill {name: 'Rust'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 7}), (s:Skill {name: 'Python'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 7}), (s:Skill {name: 'SQL'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 7}), (s:Skill {name: 'JavaScript'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 7}), (s:Skill {name: 'Go'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 8}), (s:Skill {name: 'SQL'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 8}), (s:Skill {name: 'Go'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 9}), (s:Skill {name: 'Kubernetes'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 9}), (s:Skill {name: 'Cypher'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 9}), (s:Skill {name: 'JavaScript'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 10}), (s:Skill {name: 'Python'}) CREATE (p)-[:HAS_SKILL {level: '专家'}]->(s);
MATCH (p:Person {id: 10}), (s:Skill {name: 'JavaScript'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 10}), (s:Skill {name: 'SQL'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 11}), (s:Skill {name: 'Java'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 11}), (s:Skill {name: 'Cypher'}) CREATE (p)-[:HAS_SKILL {level: '专家'}]->(s);
MATCH (p:Person {id: 12}), (s:Skill {name: 'Go'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 12}), (s:Skill {name: 'Docker'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 13}), (s:Skill {name: 'Go'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 13}), (s:Skill {name: 'Python'}) CREATE (p)-[:HAS_SKILL {level: '专家'}]->(s);
MATCH (p:Person {id: 14}), (s:Skill {name: 'Rust'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 14}), (s:Skill {name: 'Java'}) CREATE (p)-[:HAS_SKILL {level: '专家'}]->(s);
MATCH (p:Person {id: 14}), (s:Skill {name: 'AWS'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 15}), (s:Skill {name: 'Java'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 15}), (s:Skill {name: 'Rust'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 16}), (s:Skill {name: 'Java'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 16}), (s:Skill {name: 'Cypher'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 17}), (s:Skill {name: 'AWS'}) CREATE (p)-[:HAS_SKILL {level: '专家'}]->(s);
MATCH (p:Person {id: 17}), (s:Skill {name: 'Kubernetes'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 17}), (s:Skill {name: 'Java'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 18}), (s:Skill {name: 'Cypher'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 18}), (s:Skill {name: 'Python'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 19}), (s:Skill {name: 'Java'}) CREATE (p)-[:HAS_SKILL {level: '初级'}]->(s);
MATCH (p:Person {id: 19}), (s:Skill {name: 'SQL'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 19}), (s:Skill {name: 'Kubernetes'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 20}), (s:Skill {name: 'Cypher'}) CREATE (p)-[:HAS_SKILL {level: '高级'}]->(s);
MATCH (p:Person {id: 20}), (s:Skill {name: 'SQL'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 20}), (s:Skill {name: 'AWS'}) CREATE (p)-[:HAS_SKILL {level: '中级'}]->(s);
MATCH (p:Person {id: 20}), (s:Skill {name: 'Kubernetes'}) CREATE (p)-[:HAS_SKILL {level: '专家'}]->(s);

// 5. 创建兴趣节点和 HAS_INTEREST 关系
// 首先创建所有兴趣节点
CREATE (:Interest {name: '阅读'});
CREATE (:Interest {name: '旅行'});
CREATE (:Interest {name: '摄影'});
CREATE (:Interest {name: '烹饪'});
CREATE (:Interest {name: '音乐'});
CREATE (:Interest {name: '电影'});
CREATE (:Interest {name: '运动'});
CREATE (:Interest {name: '游戏'});
CREATE (:Interest {name: '编程'});
CREATE (:Interest {name: '设计'});

// 然后创建人员与兴趣的关系
MATCH (p:Person {id: 1}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 5}]->(i);
MATCH (p:Person {id: 1}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 5}]->(i);
MATCH (p:Person {id: 1}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 1}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 1}), (i:Interest {name: '阅读'}) CREATE (p)-[:HAS_INTEREST {intensity: 10}]->(i);
MATCH (p:Person {id: 2}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 2}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 9}]->(i);
MATCH (p:Person {id: 2}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 2}), (i:Interest {name: '电影'}) CREATE (p)-[:HAS_INTEREST {intensity: 3}]->(i);
MATCH (p:Person {id: 3}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 10}]->(i);
MATCH (p:Person {id: 3}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 6}]->(i);
MATCH (p:Person {id: 4}), (i:Interest {name: '烹饪'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 4}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 5}), (i:Interest {name: '阅读'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 5}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 10}]->(i);
MATCH (p:Person {id: 5}), (i:Interest {name: '烹饪'}) CREATE (p)-[:HAS_INTEREST {intensity: 2}]->(i);
MATCH (p:Person {id: 6}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 6}), (i:Interest {name: '烹饪'}) CREATE (p)-[:HAS_INTEREST {intensity: 3}]->(i);
MATCH (p:Person {id: 6}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 1}]->(i);
MATCH (p:Person {id: 6}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 2}]->(i);
MATCH (p:Person {id: 6}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 7}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 1}]->(i);
MATCH (p:Person {id: 7}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 9}]->(i);
MATCH (p:Person {id: 7}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 8}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 8}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 9}]->(i);
MATCH (p:Person {id: 9}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 9}]->(i);
MATCH (p:Person {id: 9}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 9}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 3}]->(i);
MATCH (p:Person {id: 9}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 10}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 10}), (i:Interest {name: '烹饪'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 10}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 5}]->(i);
MATCH (p:Person {id: 10}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 10}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 2}]->(i);
MATCH (p:Person {id: 11}), (i:Interest {name: '烹饪'}) CREATE (p)-[:HAS_INTEREST {intensity: 9}]->(i);
MATCH (p:Person {id: 11}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 2}]->(i);
MATCH (p:Person {id: 11}), (i:Interest {name: '电影'}) CREATE (p)-[:HAS_INTEREST {intensity: 3}]->(i);
MATCH (p:Person {id: 11}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 3}]->(i);
MATCH (p:Person {id: 12}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 2}]->(i);
MATCH (p:Person {id: 12}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 12}), (i:Interest {name: '烹饪'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 13}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 13}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 13}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 13}), (i:Interest {name: '阅读'}) CREATE (p)-[:HAS_INTEREST {intensity: 10}]->(i);
MATCH (p:Person {id: 14}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 14}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 1}]->(i);
MATCH (p:Person {id: 15}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 9}]->(i);
MATCH (p:Person {id: 15}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 10}]->(i);
MATCH (p:Person {id: 15}), (i:Interest {name: '编程'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 15}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 16}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 1}]->(i);
MATCH (p:Person {id: 16}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 16}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 6}]->(i);
MATCH (p:Person {id: 17}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 1}]->(i);
MATCH (p:Person {id: 17}), (i:Interest {name: '游戏'}) CREATE (p)-[:HAS_INTEREST {intensity: 7}]->(i);
MATCH (p:Person {id: 17}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 10}]->(i);
MATCH (p:Person {id: 17}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 10}]->(i);
MATCH (p:Person {id: 17}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 1}]->(i);
MATCH (p:Person {id: 18}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 18}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 3}]->(i);
MATCH (p:Person {id: 19}), (i:Interest {name: '音乐'}) CREATE (p)-[:HAS_INTEREST {intensity: 6}]->(i);
MATCH (p:Person {id: 19}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 4}]->(i);
MATCH (p:Person {id: 20}), (i:Interest {name: '电影'}) CREATE (p)-[:HAS_INTEREST {intensity: 5}]->(i);
MATCH (p:Person {id: 20}), (i:Interest {name: '设计'}) CREATE (p)-[:HAS_INTEREST {intensity: 2}]->(i);
MATCH (p:Person {id: 20}), (i:Interest {name: '运动'}) CREATE (p)-[:HAS_INTEREST {intensity: 8}]->(i);
MATCH (p:Person {id: 20}), (i:Interest {name: '摄影'}) CREATE (p)-[:HAS_INTEREST {intensity: 1}]->(i);
MATCH (p:Person {id: 20}), (i:Interest {name: '烹饪'}) CREATE (p)-[:HAS_INTEREST {intensity: 9}]->(i);

// 6. 创建 WORKS_AT 关系
MATCH (p:Person {id: 1}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '工程师', since: '2023-09-01'}]->(c);
MATCH (p:Person {id: 2}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '工程师', since: '2019-03-01'}]->(c);
MATCH (p:Person {id: 3}), (c:Company {name: 'WebFlow'}) CREATE (p)-[:WORKS_AT {position: '数据分析师', since: '2021-04-01'}]->(c);
MATCH (p:Person {id: 4}), (c:Company {name: 'WebFlow'}) CREATE (p)-[:WORKS_AT {position: '工程师', since: '2019-07-01'}]->(c);
MATCH (p:Person {id: 5}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '数据分析师', since: '2020-08-01'}]->(c);
MATCH (p:Person {id: 6}), (c:Company {name: 'CloudNine'}) CREATE (p)-[:WORKS_AT {position: '数据分析师', since: '2023-12-01'}]->(c);
MATCH (p:Person {id: 7}), (c:Company {name: 'WebFlow'}) CREATE (p)-[:WORKS_AT {position: '产品经理', since: '2019-05-01'}]->(c);
MATCH (p:Person {id: 8}), (c:Company {name: 'DataSoft'}) CREATE (p)-[:WORKS_AT {position: '工程师', since: '2022-12-01'}]->(c);
MATCH (p:Person {id: 9}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '设计师', since: '2018-01-01'}]->(c);
MATCH (p:Person {id: 10}), (c:Company {name: 'WebFlow'}) CREATE (p)-[:WORKS_AT {position: '架构师', since: '2022-03-01'}]->(c);
MATCH (p:Person {id: 11}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '架构师', since: '2018-03-01'}]->(c);
MATCH (p:Person {id: 12}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '架构师', since: '2018-11-01'}]->(c);
MATCH (p:Person {id: 13}), (c:Company {name: 'DataSoft'}) CREATE (p)-[:WORKS_AT {position: '数据分析师', since: '2018-10-01'}]->(c);
MATCH (p:Person {id: 14}), (c:Company {name: 'DataSoft'}) CREATE (p)-[:WORKS_AT {position: '架构师', since: '2022-01-01'}]->(c);
MATCH (p:Person {id: 15}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '数据分析师', since: '2023-10-01'}]->(c);
MATCH (p:Person {id: 16}), (c:Company {name: 'CloudNine'}) CREATE (p)-[:WORKS_AT {position: '设计师', since: '2019-11-01'}]->(c);
MATCH (p:Person {id: 17}), (c:Company {name: 'CloudNine'}) CREATE (p)-[:WORKS_AT {position: '产品经理', since: '2020-07-01'}]->(c);
MATCH (p:Person {id: 18}), (c:Company {name: 'DataSoft'}) CREATE (p)-[:WORKS_AT {position: '设计师', since: '2021-06-01'}]->(c);
MATCH (p:Person {id: 19}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '工程师', since: '2021-10-01'}]->(c);
MATCH (p:Person {id: 20}), (c:Company {name: 'TechCorp'}) CREATE (p)-[:WORKS_AT {position: '工程师', since: '2022-04-01'}]->(c);

// 7. 创建 FRIEND 关系
// 添加相互的朋友关系（双向关系）
MATCH (a:Person {id: 1}), (b:Person {id: 19}) CREATE (a)-[:FRIEND {since: '2019-11-01'}]->(b), (b)-[:FRIEND {since: '2019-11-01'}]->(a);
MATCH (a:Person {id: 2}), (b:Person {id: 13}) CREATE (a)-[:FRIEND {since: '2018-05-01'}]->(b), (b)-[:FRIEND {since: '2018-05-01'}]->(a);
MATCH (a:Person {id: 2}), (b:Person {id: 8}) CREATE (a)-[:FRIEND {since: '2016-04-01'}]->(b), (b)-[:FRIEND {since: '2016-04-01'}]->(a);
MATCH (a:Person {id: 2}), (b:Person {id: 4}) CREATE (a)-[:FRIEND {since: '2016-07-01'}]->(b), (b)-[:FRIEND {since: '2016-07-01'}]->(a);
MATCH (a:Person {id: 2}), (b:Person {id: 3}) CREATE (a)-[:FRIEND {since: '2019-08-01'}]->(b), (b)-[:FRIEND {since: '2019-08-01'}]->(a);
MATCH (a:Person {id: 2}), (b:Person {id: 12}) CREATE (a)-[:FRIEND {since: '2020-03-01'}]->(b), (b)-[:FRIEND {since: '2020-03-01'}]->(a);
MATCH (a:Person {id: 3}), (b:Person {id: 13}) CREATE (a)-[:FRIEND {since: '2016-10-01'}]->(b), (b)-[:FRIEND {since: '2016-10-01'}]->(a);
MATCH (a:Person {id: 3}), (b:Person {id: 8}) CREATE (a)-[:FRIEND {since: '2017-09-01'}]->(b), (b)-[:FRIEND {since: '2017-09-01'}]->(a);
MATCH (a:Person {id: 3}), (b:Person {id: 10}) CREATE (a)-[:FRIEND {since: '2018-03-01'}]->(b), (b)-[:FRIEND {since: '2018-03-01'}]->(a);
MATCH (a:Person {id: 4}), (b:Person {id: 14}) CREATE (a)-[:FRIEND {since: '2015-04-01'}]->(b), (b)-[:FRIEND {since: '2015-04-01'}]->(a);
MATCH (a:Person {id: 4}), (b:Person {id: 10}) CREATE (a)-[:FRIEND {since: '2015-06-01'}]->(b), (b)-[:FRIEND {since: '2015-06-01'}]->(a);
MATCH (a:Person {id: 4}), (b:Person {id: 9}) CREATE (a)-[:FRIEND {since: '2021-05-01'}]->(b), (b)-[:FRIEND {since: '2021-05-01'}]->(a);
MATCH (a:Person {id: 4}), (b:Person {id: 12}) CREATE (a)-[:FRIEND {since: '2016-04-01'}]->(b), (b)-[:FRIEND {since: '2016-04-01'}]->(a);
MATCH (a:Person {id: 5}), (b:Person {id: 12}) CREATE (a)-[:FRIEND {since: '2022-03-01'}]->(b), (b)-[:FRIEND {since: '2022-03-01'}]->(a);
MATCH (a:Person {id: 5}), (b:Person {id: 8}) CREATE (a)-[:FRIEND {since: '2019-03-01'}]->(b), (b)-[:FRIEND {since: '2019-03-01'}]->(a);
MATCH (a:Person {id: 5}), (b:Person {id: 17}) CREATE (a)-[:FRIEND {since: '2018-12-01'}]->(b), (b)-[:FRIEND {since: '2018-12-01'}]->(a);
MATCH (a:Person {id: 5}), (b:Person {id: 14}) CREATE (a)-[:FRIEND {since: '2023-09-01'}]->(b), (b)-[:FRIEND {since: '2023-09-01'}]->(a);
MATCH (a:Person {id: 5}), (b:Person {id: 16}) CREATE (a)-[:FRIEND {since: '2019-12-01'}]->(b), (b)-[:FRIEND {since: '2019-12-01'}]->(a);
MATCH (a:Person {id: 6}), (b:Person {id: 15}) CREATE (a)-[:FRIEND {since: '2023-08-01'}]->(b), (b)-[:FRIEND {since: '2023-08-01'}]->(a);
MATCH (a:Person {id: 6}), (b:Person {id: 14}) CREATE (a)-[:FRIEND {since: '2016-01-01'}]->(b), (b)-[:FRIEND {since: '2016-01-01'}]->(a);
MATCH (a:Person {id: 6}), (b:Person {id: 13}) CREATE (a)-[:FRIEND {since: '2016-03-01'}]->(b), (b)-[:FRIEND {since: '2016-03-01'}]->(a);
MATCH (a:Person {id: 6}), (b:Person {id: 9}) CREATE (a)-[:FRIEND {since: '2017-11-01'}]->(b), (b)-[:FRIEND {since: '2017-11-01'}]->(a);
MATCH (a:Person {id: 6}), (b:Person {id: 3}) CREATE (a)-[:FRIEND {since: '2021-10-01'}]->(b), (b)-[:FRIEND {since: '2021-10-01'}]->(a);
MATCH (a:Person {id: 7}), (b:Person {id: 14}) CREATE (a)-[:FRIEND {since: '2021-10-01'}]->(b), (b)-[:FRIEND {since: '2021-10-01'}]->(a);
MATCH (a:Person {id: 8}), (b:Person {id: 18}) CREATE (a)-[:FRIEND {since: '2023-05-01'}]->(b), (b)-[:FRIEND {since: '2023-05-01'}]->(a);
MATCH (a:Person {id: 8}), (b:Person {id: 10}) CREATE (a)-[:FRIEND {since: '2020-02-01'}]->(b), (b)-[:FRIEND {since: '2020-02-01'}]->(a);
MATCH (a:Person {id: 8}), (b:Person {id: 1}) CREATE (a)-[:FRIEND {since: '2019-07-01'}]->(b), (b)-[:FRIEND {since: '2019-07-01'}]->(a);
MATCH (a:Person {id: 8}), (b:Person {id: 4}) CREATE (a)-[:FRIEND {since: '2017-08-01'}]->(b), (b)-[:FRIEND {since: '2017-08-01'}]->(a);
MATCH (a:Person {id: 9}), (b:Person {id: 10}) CREATE (a)-[:FRIEND {since: '2023-03-01'}]->(b), (b)-[:FRIEND {since: '2023-03-01'}]->(a);
MATCH (a:Person {id: 10}), (b:Person {id: 11}) CREATE (a)-[:FRIEND {since: '2020-03-01'}]->(b), (b)-[:FRIEND {since: '2020-03-01'}]->(a);
MATCH (a:Person {id: 10}), (b:Person {id: 18}) CREATE (a)-[:FRIEND {since: '2023-09-01'}]->(b), (b)-[:FRIEND {since: '2023-09-01'}]->(a);
MATCH (a:Person {id: 10}), (b:Person {id: 7}) CREATE (a)-[:FRIEND {since: '2015-10-01'}]->(b), (b)-[:FRIEND {since: '2015-10-01'}]->(a);
MATCH (a:Person {id: 11}), (b:Person {id: 17}) CREATE (a)-[:FRIEND {since: '2020-05-01'}]->(b), (b)-[:FRIEND {since: '2020-05-01'}]->(a);
MATCH (a:Person {id: 11}), (b:Person {id: 1}) CREATE (a)-[:FRIEND {since: '2018-01-01'}]->(b), (b)-[:FRIEND {since: '2018-01-01'}]->(a);
MATCH (a:Person {id: 11}), (b:Person {id: 4}) CREATE (a)-[:FRIEND {since: '2018-10-01'}]->(b), (b)-[:FRIEND {since: '2018-10-01'}]->(a);
MATCH (a:Person {id: 12}), (b:Person {id: 3}) CREATE (a)-[:FRIEND {since: '2022-02-01'}]->(b), (b)-[:FRIEND {since: '2022-02-01'}]->(a);
MATCH (a:Person {id: 13}), (b:Person {id: 5}) CREATE (a)-[:FRIEND {since: '2023-10-01'}]->(b), (b)-[:FRIEND {since: '2023-10-01'}]->(a);
MATCH (a:Person {id: 13}), (b:Person {id: 20}) CREATE (a)-[:FRIEND {since: '2021-04-01'}]->(b), (b)-[:FRIEND {since: '2021-04-01'}]->(a);
MATCH (a:Person {id: 13}), (b:Person {id: 17}) CREATE (a)-[:FRIEND {since: '2023-12-01'}]->(b), (b)-[:FRIEND {since: '2023-12-01'}]->(a);
MATCH (a:Person {id: 13}), (b:Person {id: 19}) CREATE (a)-[:FRIEND {since: '2018-12-01'}]->(b), (b)-[:FRIEND {since: '2018-12-01'}]->(a);
MATCH (a:Person {id: 14}), (b:Person {id: 13}) CREATE (a)-[:FRIEND {since: '2023-08-01'}]->(b), (b)-[:FRIEND {since: '2023-08-01'}]->(a);
MATCH (a:Person {id: 14}), (b:Person {id: 12}) CREATE (a)-[:FRIEND {since: '2016-04-01'}]->(b), (b)-[:FRIEND {since: '2016-04-01'}]->(a);
MATCH (a:Person {id: 14}), (b:Person {id: 16}) CREATE (a)-[:FRIEND {since: '2018-02-01'}]->(b), (b)-[:FRIEND {since: '2018-02-01'}]->(a);
MATCH (a:Person {id: 15}), (b:Person {id: 1}) CREATE (a)-[:FRIEND {since: '2018-01-01'}]->(b), (b)-[:FRIEND {since: '2018-01-01'}]->(a);
MATCH (a:Person {id: 15}), (b:Person {id: 19}) CREATE (a)-[:FRIEND {since: '2016-12-01'}]->(b), (b)-[:FRIEND {since: '2016-12-01'}]->(a);
MATCH (a:Person {id: 15}), (b:Person {id: 8}) CREATE (a)-[:FRIEND {since: '2015-04-01'}]->(b), (b)-[:FRIEND {since: '2015-04-01'}]->(a);
MATCH (a:Person {id: 16}), (b:Person {id: 2}) CREATE (a)-[:FRIEND {since: '2020-02-01'}]->(b), (b)-[:FRIEND {since: '2020-02-01'}]->(a);
MATCH (a:Person {id: 17}), (b:Person {id: 8}) CREATE (a)-[:FRIEND {since: '2017-12-01'}]->(b), (b)-[:FRIEND {since: '2017-12-01'}]->(a);
MATCH (a:Person {id: 17}), (b:Person {id: 9}) CREATE (a)-[:FRIEND {since: '2022-04-01'}]->(b), (b)-[:FRIEND {since: '2022-04-01'}]->(a);
MATCH (a:Person {id: 17}), (b:Person {id: 16}) CREATE (a)-[:FRIEND {since: '2022-07-01'}]->(b), (b)-[:FRIEND {since: '2022-07-01'}]->(a);
MATCH (a:Person {id: 17}), (b:Person {id: 7}) CREATE (a)-[:FRIEND {since: '2018-02-01'}]->(b), (b)-[:FRIEND {since: '2018-02-01'}]->(a);
MATCH (a:Person {id: 17}), (b:Person {id: 19}) CREATE (a)-[:FRIEND {since: '2016-11-01'}]->(b), (b)-[:FRIEND {since: '2016-11-01'}]->(a);
MATCH (a:Person {id: 18}), (b:Person {id: 12}) CREATE (a)-[:FRIEND {since: '2015-11-01'}]->(b), (b)-[:FRIEND {since: '2015-11-01'}]->(a);
MATCH (a:Person {id: 18}), (b:Person {id: 14}) CREATE (a)-[:FRIEND {since: '2016-01-01'}]->(b), (b)-[:FRIEND {since: '2016-01-01'}]->(a);
MATCH (a:Person {id: 18}), (b:Person {id: 19}) CREATE (a)-[:FRIEND {since: '2021-12-01'}]->(b), (b)-[:FRIEND {since: '2021-12-01'}]->(a);
MATCH (a:Person {id: 18}), (b:Person {id: 15}) CREATE (a)-[:FRIEND {since: '2020-02-01'}]->(b), (b)-[:FRIEND {since: '2020-02-01'}]->(a);
MATCH (a:Person {id: 19}), (b:Person {id: 7}) CREATE (a)-[:FRIEND {since: '2023-08-01'}]->(b), (b)-[:FRIEND {since: '2023-08-01'}]->(a);
MATCH (a:Person {id: 19}), (b:Person {id: 20}) CREATE (a)-[:FRIEND {since: '2017-07-01'}]->(b), (b)-[:FRIEND {since: '2017-07-01'}]->(a);
MATCH (a:Person {id: 20}), (b:Person {id: 9}) CREATE (a)-[:FRIEND {since: '2018-02-01'}]->(b), (b)-[:FRIEND {since: '2018-02-01'}]->(a);
MATCH (a:Person {id: 20}), (b:Person {id: 15}) CREATE (a)-[:FRIEND {since: '2022-09-01'}]->(b), (b)-[:FRIEND {since: '2022-09-01'}]->(a);

// 8. 创建索引和约束（提升查询性能）
CREATE INDEX person_name_idx IF NOT EXISTS FOR (p:Person) ON (p.name);
CREATE INDEX person_city_idx IF NOT EXISTS FOR (p:Person) ON (p.city);
CREATE INDEX person_age_idx IF NOT EXISTS FOR (p:Person) ON (p.age);
CREATE INDEX company_name_idx IF NOT EXISTS FOR (c:Company) ON (c.name);
CREATE INDEX skill_name_idx IF NOT EXISTS FOR (s:Skill) ON (s.name);
CREATE INDEX interest_name_idx IF NOT EXISTS FOR (i:Interest) ON (i.name);

// 唯一性约束
//CREATE CONSTRAINT person_id_unique IF NOT EXISTS FOR (p:Person) REQUIRE p.id IS UNIQUE;
//CREATE CONSTRAINT person_email_unique IF NOT EXISTS FOR (p:Person) REQUIRE p.email IS UNIQUE;
//CREATE CONSTRAINT company_name_unique IF NOT EXISTS FOR (c:Company) REQUIRE c.name IS UNIQUE;
//CREATE CONSTRAINT skill_name_unique IF NOT EXISTS FOR (s:Skill) REQUIRE s.name IS UNIQUE;
//CREATE CONSTRAINT interest_name_unique IF NOT EXISTS FOR (i:Interest) REQUIRE i.name IS UNIQUE;

// ====================================
// 示例查询（用于测试）
// ====================================

// 查询 1: 查找所有年龄大于30岁的人员
MATCH (p:Person)
WHERE p.age > 30
RETURN p.name, p.age, p.city
ORDER BY p.age DESC
;

// 查询 2: 查找 Alice 的所有朋友
MATCH (p:Person {name: 'Alice'})-[:FRIEND]->(f:Person)
RETURN f.name, f.age, f.city
;

// 查询 3: 查找拥有 'Python' 技能的所有人员及其技能等级
MATCH (p:Person)-[r:HAS_SKILL]->(s:Skill {name: 'Python'})
RETURN p.name, r.level
ORDER BY r.level
;

// 查询 4: 查找在 'TechCorp' 工作的所有人员及其职位
MATCH (p:Person)-[r:WORKS_AT]->(c:Company {name: 'TechCorp'})
RETURN p.name, r.position, r.since
ORDER BY r.since DESC
;

// 查询 5: 统计每个城市的人员数量
MATCH (p:Person)
RETURN p.city, count(p) AS num_people
ORDER BY num_people DESC
;

// 查询 6: 查找共同兴趣的人员对
MATCH (p1:Person)-[:HAS_INTEREST]->(i:Interest)<-[:HAS_INTEREST]-(p2:Person)
WHERE p1.name < p2.name  // 避免重复
WITH p1, p2, count(i) AS common_interests
WHERE common_interests >= 2
RETURN p1.name, p2.name, common_interests
ORDER BY common_interests DESC
;

// 查询 7: 查找朋友的朋友（二度关系）
MATCH (p:Person {name: 'Alice'})-[:FRIEND]->(f:Person)-[:FRIEND]->(fof:Person)
WHERE NOT (p)-[:FRIEND]->(fof)  // 排除已经是朋友的人
RETURN DISTINCT fof.name, fof.city, count(f) AS mutual_friends
ORDER BY mutual_friends DESC
;

// 查询 8: 查找拥有特定技能组合的人员
MATCH (p:Person)-[:HAS_SKILL]->(s1:Skill {name: 'Python'})
MATCH (p)-[:HAS_SKILL]->(s2:Skill {name: 'Java'})
RETURN p.name, p.city
;

// 查询 9: 使用 MERGE 确保数据存在
MERGE (p:Person {name: 'NewUser'})
ON CREATE SET p.age = 28, p.city = '北京', p.email = 'newuser@example.com'
ON MATCH SET p.last_seen = timestamp()
RETURN p
;

// 查询 10: 使用 CASE 表达式对人员进行分类
MATCH (p:Person)
RETURN p.name, p.age,
  CASE
    WHEN p.age < 25 THEN '青年'
    WHEN p.age >= 25 AND p.age < 35 THEN '壮年'
    WHEN p.age >= 35 AND p.age < 50 THEN '中年'
    ELSE '资深'
  END AS age_group
ORDER BY p.age
;