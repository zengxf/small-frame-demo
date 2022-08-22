package cn.zxf.id;

import cn.zxf.node.NodeOp;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/22.
 */
@Slf4j
public class SnowflakeIdWorker {

    private String pathPrefix = "/test/id-maker/worker-";
    private String pathRegistered = null;
    public static SnowflakeIdWorker instance = new SnowflakeIdWorker();

    private SnowflakeIdWorker() {
        this.init();
    }

    private void init() {
        // 创建一个 ZNode 节点
        // 节点的 payload 为当前 worker 实例
        try {
            byte[] payload = pathPrefix.getBytes();
            pathRegistered = NodeOp.instance.getClient()
                    .create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(pathPrefix, payload);
        } catch (Exception e) {
            log.error("Init error!", e);
        }
    }

    public long getId() {
        if (null == pathRegistered)
            throw new RuntimeException("节点注册失败");
        int index = pathPrefix.length();
        String sid = pathRegistered.substring(index);
        return Long.parseLong(sid);
    }

}
