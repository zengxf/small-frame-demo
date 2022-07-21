package test.simple.multi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test.simple.SimpleMsg;

import java.util.stream.Stream;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/21.
 */
@Slf4j
public class SimpleMultiTest {

    @Test
    public void testType() {
        Stream.of(Type.values())
                .peek(type -> log.info("-----------\ntype: [{}]", type))
                .forEach(type -> {
                    if (type != Type.UNRECOGNIZED)
                        log.info("t.name: [{}], t.value: [{}]", type.name(), type.getNumber());
                });
    }

    @Test
    public void testMsg() {
        Msg.Builder builder = Msg.newBuilder();
        builder.setId(68);
        builder.setContent("中-11111");
        builder.setType(Type.LOGIN_REQUEST);
        Msg message = builder.build();
        log.info("message: [{}]", message);
    }

}