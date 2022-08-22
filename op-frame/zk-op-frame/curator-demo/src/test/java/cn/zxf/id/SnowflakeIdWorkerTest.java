package cn.zxf.id;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/22.
 */
@Slf4j
public class SnowflakeIdWorkerTest {

    @Test
    public void getId() {
        long id = SnowflakeIdWorker.instance.getId();
        log.info("id: [{}]", id);

        id = SnowflakeIdWorker.instance.getId();
        log.info("id: [{}]", id);

        id = SnowflakeIdWorker.instance.getId();
        log.info("id: [{}]", id);
    }

}