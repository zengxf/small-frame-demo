package test.biz;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import test.common.Constant;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Slf4j
public class CommunityClientTest implements Constant {

    static void main() {
        // 客户端配置
        ClientConfig clientConfig = new ClientConfig();
        // clientConfig.setClusterName("dev1"); // 集群名不一致，会出错：AuthenticationException: Authentication failed.
        clientConfig.setClusterName(CLUSTER_NAME); // 确保与服务端集群名一致
        clientConfig.getNetworkConfig()
                .addAddress("127.0.0.1:5701");

        // 连接到集群
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        // // 测试锁
        // testLock(client);

        // 获取分布式对象
        IMap<String, String> map = client.getMap(MAP_NAME);
        log.info("集群中的 Map 大小: {}", map.size());

        map.put("k3", "value-3333");
        map.forEach((k, v) -> log.info("数据内容: [{} -> {}]", k, v));
        log.info("集群中的 Map 大小: {}", map.size());

        // 关闭连接
        client.shutdown();
    }

    /**
     * CP subsystem 是企业版功能。
     * <p/>
     * java.lang.UnsupportedOperationException: CP subsystem is an Enterprise feature.
     */
    @Deprecated
    static void testLock(HazelcastInstance client) {
        // 分布式锁示例 (解决并发问题)
        var lock = client.getCPSubsystem().getLock("my-lock");
        lock.lock();
        try {
            log.info("获取锁成功。");
        } finally {
            lock.unlock();
        }
    }

}
