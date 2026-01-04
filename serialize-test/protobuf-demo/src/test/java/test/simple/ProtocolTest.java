package test.simple;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <p/>
 * Created by ZXFeng on 2026/1/4
 */
@Slf4j
public class ProtocolTest {

    @Test
    public void testProtocol() throws InvalidProtocolBufferException {
        ProtocolSub1.Msg msg = ProtocolSub1.Msg.newBuilder()
                .setType("1001")
                .setSign("test-msg")
                .setName("ZXF")
                .setAge(88)
                .build();

        // 将 Protobuf 对象，序列化成二进制字节数组
        // 可以理解为传过来的消息载体
        byte[] transData = msg.toByteArray();

        // 反序列化成主的协议，用于查看具体消息类型
        ProtocolMain.Msg mainMsg = ProtocolMain.Msg.parseFrom(transData);
        log.info("mainMsg: \n{}", mainMsg);

        // 根据消息类型，再反序列化成具体的消息对象
        if (mainMsg.getType().equals("1001")) {
            ProtocolSub1.Msg subMsg = ProtocolSub1.Msg.parseFrom(transData);
            log.info("subMsg: \n{}", subMsg);
        }
    }

}
