package cn.zxf.node;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/19.
 */
@Slf4j
public class NodeOpTest {

    @Test
    public void createNode() {
        NodeOp.instance.createNode("/u-test/k-null", "");
        NodeOp.instance.createNode("/u-test/k-v1", "v1");
        NodeOp.instance.createNode("/u-test/k-v2", "v2");
        NodeOp.instance.createNode("/u-test/k-v3/k-3-1", "v3");
    }

    @Test
    public void createEphemeralSeqNode() {
        NodeOp.instance.createEphemeralSeqNode("/u-e-test/k1-x-");
        NodeOp.instance.createEphemeralSeqNode("/u-e-test/k2-x-");
        log.info("------------");
    }

    @Test
    public void deleteNode() {
        NodeOp.instance.deleteNode("/u-test/k-null");
    }

    @Test
    public void readData() {
        String v = NodeOp.instance.readData("/u-test/k-v1");
        log.info("v: [{}]", v);
    }

    @Test
    public void readChildren() {
        List<String> children = NodeOp.instance.readChildren("/u-test");
        log.info("children: [{}]", children);
    }

    @Test
    public void updateData() {
        NodeOp.instance.updateData("/u-test/k-v1", "v1-222");
    }

    @Test
    public void asyncUpdateData() throws InterruptedException {
        NodeOp.instance.asyncUpdateData("/u-test/k-v1", "v1-xx");
        log.info("--------- 3");
        Thread.sleep(10_000L);
        log.info("--------- 4");
    }

}