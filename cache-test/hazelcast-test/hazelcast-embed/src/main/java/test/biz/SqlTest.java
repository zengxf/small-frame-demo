package test.biz;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import lombok.extern.slf4j.Slf4j;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Slf4j
public class SqlTest implements Constant {

    static void main() {
        // 客户端配置
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(CLUSTER_NAME); // 确保与服务端集群名一致
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");

        // 连接到集群
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        // 填充一些数据
        IMap<Integer, Person> map = client.getMap("people");
        map.put(1, new Person("Alice", 25));
        map.put(2, new Person("Bob", 31));
        map.put(3, new Person("Charlie", 40));

        // 为 map 建表映射（SQL 可查询）
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

        // 查询
        String sql = "SELECT __key id, name, age FROM people WHERE age > 30";
        try (SqlResult result = client.getSql().execute(sql)) {
            for (SqlRow row : result) {
                Object id = row.getObject("id");
                Object name = row.getObject("name");
                Object age = row.getObject("age");
                log.info("用户 => id: [{}], name: [{}], age: [{}]", id, name, age);
            }
        }

        client.shutdown();
    }

}
