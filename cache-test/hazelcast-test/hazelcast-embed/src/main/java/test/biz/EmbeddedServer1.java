package test.biz;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import test.common.Constant;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Slf4j
public class EmbeddedServer1 implements Constant {

    public static void main(String[] args) {
        Config cfg = new Config()
                .setClusterName(CLUSTER_NAME)   // def: dev
                .setInstanceName("ser-111");
        cfg.getJetConfig().setEnabled(true);
        System.setProperty(INSTANCE_NAME_KEY, "ser-1111");

        // 启动一个 Hazelcast 节点
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(cfg);
        log.info("hz-name: [{}]", hz.getName()); // 如果上面不指定 InstanceName，则会随机返回

        // 获取或创建一个分布式 Map (类似 Java 的 HashMap，但数据同步到整个集群)
        IMap<String, String> map = hz.getMap(MAP_NAME);

        // 存入数据
        map.put("key1", "Hello 2026 from server 1");

        // 读取数据
        log.info("数据内容: {}", map.get("key1"));
        log.info("集群中的 Map 大小: {}", map.size());

        // 保持运行以观察集群发现
        log.info("Server 1 运行中...");
    }

}
