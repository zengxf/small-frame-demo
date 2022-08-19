package cn.zxf.node;

import cn.zxf.utils.ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/19.
 */
@Slf4j
public class NodeOp {

    private CuratorFramework client;

    // Zk 集群地址
    public static final String ZK_ADDRESS = "localhost:2181";
    public static final NodeOp instance;

    static {
        instance = new NodeOp();
        instance.init();
    }

    public void init() {
        if (null != client)
            return;
        // 创建客户端
        client = ClientFactory.createSimple(ZK_ADDRESS);
        // 启动客户端实例,连接服务器
        client.start();
    }

    public void destroy() {
        CloseableUtils.closeQuietly(client);
    }

    /*** 创建节点 */
    public void createNode(String zkPath, String data) {
        try {
            byte[] payload = data.getBytes("UTF-8");
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(zkPath, payload);
        } catch (Exception e) {
            log.error("Create node error!", e);
        }
    }

    /*** 创建临时-顺序节点 */
    public String createEphemeralSeqNode(String zkPath) {
        try {
            byte[] payload = "T0X".getBytes("UTF-8");
            // 创建一个 ZNode 节点
            String path = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(zkPath, payload);
            return path;
        } catch (Exception e) {
            log.error("Create ephemeral node error!", e);
        }
        return null;
    }

    /*** 删除节点 */
    public void deleteNode(String zkPath) {
        try {
            if (!isNodeExist(zkPath)) {
                log.warn("Path [{}] not exist", zkPath);
                return;
            }
            client.delete()
                    // .inBackground()
                    .forPath(zkPath);
        } catch (Exception e) {
            log.error("Delete node error!", e);
        }
    }

    /*** 检查节点 */
    public boolean isNodeExist(String zkPath) {
        try {
            Stat stat = client.checkExists().forPath(zkPath);
            if (null == stat) {
                log.info("节点不存在: {}", zkPath);
                return false;
            } else {
                log.info("节点存在 stat is: {}", stat);
                return true;
            }
        } catch (Exception e) {
            log.error("Find node error!", e);
        }
        return false;
    }

    public String readData(String zkPath) {
        try {
            Stat stat = client.checkExists().forPath(zkPath);
            if (null != stat) {
                // 读取节点的数据
                byte[] payload = client.getData().forPath(zkPath);
                String data = new String(payload, "UTF-8");
                return data;
            }
        } catch (Exception e) {
            log.error("Read node error!", e);
        }
        return null;
    }

    public List<String> readChildren(String zkPath) {
        try {
            Stat stat = client.checkExists().forPath(zkPath);
            if (null != stat) {
                List<String> children = client.getChildren().forPath(zkPath);
                return children;
            }
        } catch (Exception e) {
            log.error("Read node children error!", e);
        }
        return null;
    }

    /*** 同步更新 */
    public void updateData(String zkPath, String data) {
        try {
            byte[] payload = data.getBytes("UTF-8");
            client.setData().forPath(zkPath, payload);
        } catch (Exception e) {
            log.error("Update node error!", e);
        }
    }

    /*** 异步更新 */
    public void asyncUpdateData(String zkPath, String data) {
        try {
            log.info("------------- 1");
            // 更新完成监听器
            AsyncCallback.StringCallback callback = (rc, path, ctx, name) -> {
                log.info("---------------");
                log.info(
                        "-----> rc = {} | path = {} | ctx = {} | name = {}",
                        rc, path, ctx, name
                );
            };
            byte[] payload = data.getBytes("UTF-8");
            client.setData()
                    .inBackground(callback)
                    .forPath(zkPath, payload);
            log.info("------------- 2");
        } catch (Exception e) {
            log.error("Async update node error!", e);
        }
    }

}
