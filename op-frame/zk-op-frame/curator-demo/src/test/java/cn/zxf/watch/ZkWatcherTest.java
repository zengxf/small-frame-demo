package cn.zxf.watch;

import cn.zxf.node.NodeOp;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/22.
 */
@Slf4j
public class ZkWatcherTest {

    private String watchPath = "/test/watch/key-1";

    @Test
    public void testWatcher() {
        CuratorFramework client = NodeOp.instance.getClient();
        // 检查节点是否存在，没有则创建
        boolean isExist = NodeOp.instance.isNodeExist(watchPath);
        if (!isExist) {
            NodeOp.instance.createNode(watchPath, "nu-ll");
        }
        try {
            Watcher w = new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    log.info("监听到的变化 watchedEvent = [{}]", watchedEvent);
                }
            };
            byte[] content = client.getData()
                    .usingWatcher(w)
                    .forPath(watchPath);
            log.info("监听节点内容：" + new String(content));
            // 第一次变更节点数据
            client.setData().forPath(watchPath, "第 1 次更改内容".getBytes());
            // 第二次变更节点数据
            client.setData().forPath(watchPath, "第 2 次更改内容".getBytes());
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNodeCache() {
        // 检查节点是否存在，没有则创建
        boolean isExist = NodeOp.instance.isNodeExist(watchPath);
        if (!isExist) {
            NodeOp.instance.createNode(watchPath, "nu-ll-00");
        }
        CuratorFramework client = NodeOp.instance.getClient();
        try {
            // NodeCache nodeCache = new NodeCache(client, watchPath, false);
            CuratorCache nodeCache = CuratorCache.builder(client, watchPath)
                    .build();
            // NodeCacheListener l = new NodeCacheListener() {
            //     @Override
            //     public void nodeChanged() throws Exception {
            //         ChildData childData = nodeCache.getCurrentData();
            //         log.info("ZNode节点状态改变, path={}", childData.getPath());
            //         log.info("ZNode节点状态改变, data={}", new String(childData.getData(), "Utf-8"));
            //         log.info("ZNode节点状态改变, stat={}", childData.getStat());
            //     }
            // };
            CuratorCacheListener l = new CuratorCacheListener() {
                @Override
                public void event(Type type, ChildData oldData, ChildData data) {
                    log.info("ZNode节点状态改变, type={}", type);
                    log.info("ZNode节点状态改变, oldData={}", oldData);
                    log.info("ZNode节点状态改变, data={}", data);
                }
            };
            nodeCache.listenable().addListener(l);
            nodeCache.start();

            // 第1次变更节点数据
            client.setData().forPath(watchPath, "第1次更改内容".getBytes());
            Thread.sleep(1000);

            // 第2次变更节点数据
            client.setData().forPath(watchPath, "第2次更改内容".getBytes());
            Thread.sleep(1000);

            // 第3次变更节点数据
            client.setData().forPath(watchPath, "第3次更改内容".getBytes());
            Thread.sleep(1000);

            Thread.sleep(5000);
        } catch (Exception e) {
            log.error("创建 NodeCache 监听失败, path = {}", watchPath);
        }
    }

}
