package test.biz;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.cluster.Member;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 分布式计算
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Slf4j
public class ZDistributedComputeTest implements Constant {

    static void main() throws Exception {
        // 客户端配置
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(CLUSTER_NAME); // 确保与服务端集群名一致
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");

        // 连接到集群
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(clientConfig);
        log.info("hz-name: [{}]", hz.getName());

        IExecutorService executor = hz.getExecutorService("default");
        log.info("executor: [{}]", executor);

        { // test 1
            // 分发任务到所有成员执行
            Map<Member, Future<String>> results = executor.submitToAllMembers(new MyTask1());
            // 收集结果
            for (Map.Entry<Member, Future<String>> entry : results.entrySet()) {
                log.info("{} -> {}", entry.getKey(), entry.getValue().get());
            }
        }

        { // test 2
            // 分发任务到所有成员执行
            Map<Member, Future<String>> results = executor.submitToAllMembers(new MyTask2());
            // 收集结果
            for (Map.Entry<Member, Future<String>> entry : results.entrySet()) {
                log.info("{} -> {}", entry.getKey(), entry.getValue().get());
            }
        }

        hz.shutdown();
    }

    /*** 简单执行示例 */
    static class MyTask1 implements Callable<String>, Serializable {
        @Override
        public String call() {
            String msg = "Hello from " + System.getProperty(INSTANCE_NAME_KEY);
            log.info(msg); // 这个日志会在 EmbeddedServer1/2/3 中输出
            return msg;
        }
    }

    /*** 获取自身 map 示例 */
    static class MyTask2 implements Callable<String>, HazelcastInstanceAware, Serializable {
        private transient HazelcastInstance hz; // 自动注入

        /*** 会在 EmbeddedServer1/2/3 中调用 2 次 */
        @Override
        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            this.hz = hazelcastInstance;
            log.info("注入 hz: [{}]", hz); // 这个日志会在 EmbeddedServer1/2/3 中输出
        }

        @Override
        public String call() {
            IMap<String, Object> map = hz.getMap(MAP_NAME);
            String msg = "Map size: " + map.size();
            log.info(msg); // 这个日志会在 EmbeddedServer1/2/3 中输出
            return msg;
        }

    }

}
