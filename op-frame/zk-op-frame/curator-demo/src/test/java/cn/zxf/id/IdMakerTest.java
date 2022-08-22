package cn.zxf.id;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/22.
 */
@Slf4j
public class IdMakerTest {

    @Test
    public void makeId() {
        String key = "/test/seq-a-";
        IdMaker maker = new IdMaker();
        String id = maker.makeId(key);
        log.info("id: [{}]", id);

        id = maker.makeId(key);
        log.info("id: [{}]", id);

        id = maker.makeId(key);
        log.info("id: [{}]", id);
    }

}