package cn.zxf.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/19.
 */
@Slf4j
public class ClientFactory {

    /*** 简化版本 */
    public static CuratorFramework createSimple(String connectionString) {
        long start = System.currentTimeMillis();
        // 重试策略: 第一次重试等待 1s，第二次重试等待 2s，第三次重试等待 4s
        // 第一个参数：等待时间的基础单位，单位为 毫秒
        // 第二个参数：最大重试次数
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

        // 获取 CuratorFramework 实例的最简单的方式
        // 第一个参数：zk 的连接地址
        // 第二个参数：重试策略
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        log.info("创建连接耗费时间 ms：" + (System.currentTimeMillis() - start));
        return client;
    }

    /*** 复杂的版本 */
    public static CuratorFramework createWithOptions(String connectionString) {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // Builder 模式创建
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(3000) // 连接超时
                .sessionTimeoutMs(60_000)  // 会话超时
                // 其他的创建选项
                .build();
    }

}
