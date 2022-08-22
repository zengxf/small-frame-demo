package cn.zxf.id;

import cn.zxf.node.NodeOp;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/22.
 */
@Slf4j
public class IdMaker {

    private String createSeqNode(String pathPrefix) {
        try {
            // 创建一个 ZNode 顺序节点
            String seqPath = NodeOp.instance.getClient()
                    .create()
                    .creatingParentsIfNeeded()
                    // 避免 ZooKeeper 建的顺序节点暴增，需要删除创的持久化顺序节点
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(pathPrefix);
            return seqPath;
        } catch (Exception e) {
            log.error("createSeqNode error!", e);
            return null;
        }
    }

    public String makeId(String nodeName) {
        String str = createSeqNode(nodeName);
        if (null == str)
            return null;
        int index = nodeName.length();
        return str.substring(index);
    }

}
