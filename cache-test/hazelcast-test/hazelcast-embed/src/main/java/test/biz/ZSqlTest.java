package test.biz;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import lombok.extern.slf4j.Slf4j;
import test.common.Constant;
import test.common.Person;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Slf4j
public class ZSqlTest implements Constant {

    static void main() {
        // 客户端配置
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(CLUSTER_NAME); // 确保与服务端集群名一致
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");

        // 连接到集群
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        // 填充一些数据
        IMap<Integer, Person> map = client.getMap("people");
        map.put(1, new Person("Alice", 25, "SZ"));
        map.put(2, new Person("Bob", 31, "GZ"));
        map.put(3, new Person("Charlie", 40));

        // 1. 为 map 建表映射（SQL 可查询）
        client.getSql().execute("""
                CREATE OR REPLACE MAPPING people (
                    __key INT,
                    name VARCHAR,
                    age INT
                )
                TYPE IMap
                OPTIONS (
                    'keyFormat' = 'int',
                    'valueFormat' = 'java',
                    'valueJavaClass' = 'test.biz.Person'
                )
                """);

        // 2. 插入数据
        // String iSql = "INSERT INTO people (__key, name, age) VALUES (11, 'fa', 56)";
        // client.getSql().execute(iSql);   // 存在会报错 HazelcastSqlException: Duplicate key
        // client.getSql().execute("INSERT INTO people (__key, name, age) VALUES (12, 'fe', 66)");
        client.getSql().execute("SINK INTO people (__key, name, age) VALUES (11, 'fa', 99)"); // 相当于 UPSERT
        client.getSql().execute("SINK INTO people (__key, name, age) VALUES (12, 'fe', 88)");
        client.getSql().execute("SINK INTO people (__key, name, age) VALUES (13, 'fx', 88)");
        String iSqlFmt = "SINK INTO people (__key, name, age) VALUES (?, ?, ?)";    // 使用占位符
        client.getSql().execute(iSqlFmt, 14, "FF", 90);

        // 3. 查询
        String qSql = "SELECT __key id, name, age FROM people WHERE age > 30";
        try (SqlResult result = client.getSql().execute(qSql)) {
            for (SqlRow row : result) {
                Object id = row.getObject("id");
                Object name = row.getObject("name");
                Object age = row.getObject("age");
                log.info("用户 => id: [{}], name: [{}], age: [{}]", id, name, age);
            }
        }

        map.forEach((k, v) ->
                log.info("遍历 map, k: [{}], v: [{}]", k, v)
        );

        client.shutdown();
    }

}
