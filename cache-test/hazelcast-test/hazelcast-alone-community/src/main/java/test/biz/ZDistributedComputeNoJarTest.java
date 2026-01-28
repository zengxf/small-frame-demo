package test.biz;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientUserCodeDeploymentConfig;
import com.hazelcast.cluster.Member;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import lombok.extern.slf4j.Slf4j;
import test.common.Constant;
import test.common.SimpleTask;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * 分布式计算，不复制 jar 包
 * <p/>
 * <pre>
 * 配置：
 *      ClientUserCodeDeploymentConfig
 * 同时在 hazelcast.xml 里面加：
 *      <user-code-deployment enabled="true" />
 * 注意：
 *      SimpleTask.class 需要实现 Serializable, 同时改了也要重启服务端。
 *          否则：IllegalStateException: Class test.common.SimpleTask is already in local cache ...
 *      Constant.class 需要继承 Serializable。
 * </pre>
 * Created by ZXFeng on 2026/1/28
 */
@Slf4j
public class ZDistributedComputeNoJarTest implements Constant {

    static void main() throws Exception {
        // 客户端配置
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(CLUSTER_NAME); // 确保与服务端集群名一致
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");

        ClientUserCodeDeploymentConfig codeCfg = clientConfig.getUserCodeDeploymentConfig();
        codeCfg.setEnabled(true);
        codeCfg.addClass(SimpleTask.class); // 需要实现 Serializable
        codeCfg.addClass(Constant.class); // 需要继承 Serializable

        // 连接到集群
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(clientConfig);
        log.info("hz-name: [{}]", hz.getName());

        IExecutorService executor = hz.getExecutorService("default");
        log.info("executor: [{}]", executor);

        // 分发任务到所有成员执行
        Map<Member, Future<String>> results = executor.submitToAllMembers(new SimpleTask());
        // 收集结果
        for (Map.Entry<Member, Future<String>> entry : results.entrySet()) {
            log.info("{} -> {}", entry.getKey(), entry.getValue().get());
        }

        hz.shutdown();
    }

}
