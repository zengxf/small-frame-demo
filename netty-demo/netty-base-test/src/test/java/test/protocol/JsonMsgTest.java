package test.protocol;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class JsonMsgTest {

    @Test
    public void convertToJson() {
        JsonMsg msg = new JsonMsg();
        log.info("msg: [{}]", msg);
        log.info("json: [{}]", msg.convertToJson());
    }

}