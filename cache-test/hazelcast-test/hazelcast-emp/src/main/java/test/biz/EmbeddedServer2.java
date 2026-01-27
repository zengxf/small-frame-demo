package test.biz;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Slf4j
public class EmbeddedServer2 implements Constant {

    public static void main(String[] args) {
        Config cfg = new Config()
                // .setClusterName("zxf_dev1"); // 集群名不一样，不会自动组成集群
                .setClusterName(CLUSTER_NAME);  // def: dev

        // 启动一个 Hazelcast 节点
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(cfg);

        // 获取或创建一个分布式 Map (类似 Java 的 HashMap，但数据同步到整个集群)
        IMap<String, String> map = hz.getMap(MAP_NAME);

        // 存入数据
        map.put("key2", "Hello 2026 from server 2");

        // 读取数据
        log.info("数据内容: {}", map.get("key2"));
        log.info("集群中的 Map 大小: {}", map.size());

        // 保持运行以观察集群发现
        log.info("Server 2 运行中...");
    }

}
