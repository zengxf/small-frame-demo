package cn.zxf.id;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/22.
 */
@Slf4j
public class SnowflakeIdGeneratorTest {

    @Test
    public void nextId() {
        long workerId = SnowflakeIdWorker.instance.getId();
        SnowflakeIdGenerator.instance.init(workerId);

        long id = SnowflakeIdGenerator.instance.nextId();
        log.info("id: [{}]: [{}]", id, Long.toBinaryString(id));

        id = SnowflakeIdGenerator.instance.nextId();
        log.info("id: [{}]: [{}]", id, Long.toBinaryString(id));

        id = SnowflakeIdGenerator.instance.nextId();
        log.info("id: [{}]: [{}]", id, Long.toBinaryString(id));
    }

}