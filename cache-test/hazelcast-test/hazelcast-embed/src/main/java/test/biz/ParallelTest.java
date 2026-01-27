package test.biz;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.cluster.Member;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
@Slf4j
public class ParallelTest implements Constant {

    static void main() throws Exception {
        // 客户端配置
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(CLUSTER_NAME); // 确保与服务端集群名一致
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");

        // 连接到集群
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(clientConfig);


        IExecutorService executor = hz.getExecutorService("default");

        // 分发任务到所有成员执行
        Map<Member, Future<String>> results = executor.submitToAllMembers(new MyTask());

        // 收集结果
        for (Map.Entry<Member, Future<String>> entry : results.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().get());
        }

        hz.shutdown();
    }

    static class MyTask implements Callable<String>, Serializable {
        @Override
        public String call() {
            String node = Hazelcast.getHazelcastInstanceByName(CLUSTER_NAME)
                    .getCluster().getLocalMember().getAddress().toString();
            return "Hello from " + node;
        }
    }

}
