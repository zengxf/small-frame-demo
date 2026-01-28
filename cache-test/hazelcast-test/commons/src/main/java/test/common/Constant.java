package test.common;

import java.io.Serializable;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/27
 */
public interface Constant extends Serializable {

    String
            CLUSTER_NAME = "zxf_dev",
            INSTANCE_NAME_KEY = "hazelcast.instance.name",
            INSTANCE_CFG_KEY = "hazelcast.config",
            MAP_NAME = "my-distributed-map";

}
