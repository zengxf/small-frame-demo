package test.simple;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DSv3.2 生成 (Kilo Code 插件)
 * <br/>
 * ProtocolMain.Msg 单元测试，专注于 PO 转 byte 和 byte 转 PO 的转换。
 * <br/>
 * @author Kilo Code
 */
@Slf4j
public class ProtocolMain_Kilo_DSv3_2_Test {

    @Test
    public void testPoToByteAndByteToPo() throws InvalidProtocolBufferException {
        // 1. 创建 ProtocolMain.Msg 实例 (PO)
        ProtocolMain.Msg originalMsg = ProtocolMain.Msg.newBuilder()
                .setType("test-type")
                .setSign("test-sign")
                .build();

        log.info("原始消息: {}", originalMsg);
        assertEquals("test-type", originalMsg.getType());
        assertEquals("test-sign", originalMsg.getSign());
        assertTrue(originalMsg.hasSign());

        // 2. 将 PO 转换为字节数组 (序列化)
        byte[] byteArray = originalMsg.toByteArray();
        assertNotNull(byteArray);
        assertTrue(byteArray.length > 0);
        log.info("序列化字节数组长度: {}", byteArray.length);

        // 3. 将字节数组转换回 PO (反序列化)
        ProtocolMain.Msg deserializedMsg = ProtocolMain.Msg.parseFrom(byteArray);
        log.info("反序列化消息: {}", deserializedMsg);

        // 4. 验证反序列化的 PO 与原始消息一致
        assertEquals(originalMsg.getType(), deserializedMsg.getType());
        assertEquals(originalMsg.getSign(), deserializedMsg.getSign());
        assertEquals(originalMsg.hasSign(), deserializedMsg.hasSign());
        assertEquals(originalMsg, deserializedMsg);
    }

    @Test
    public void testPoToByteAndByteToPoWithoutOptionalSign() throws InvalidProtocolBufferException {
        // 测试未设置可选 sign 的情况
        ProtocolMain.Msg originalMsg = ProtocolMain.Msg.newBuilder()
                .setType("type-only")
                .build();

        log.info("原始消息 (无 sign): {}", originalMsg);
        assertEquals("type-only", originalMsg.getType());
        assertFalse(originalMsg.hasSign());

        byte[] byteArray = originalMsg.toByteArray();
        assertNotNull(byteArray);

        ProtocolMain.Msg deserializedMsg = ProtocolMain.Msg.parseFrom(byteArray);
        log.info("反序列化消息 (无 sign): {}", deserializedMsg);

        assertEquals(originalMsg.getType(), deserializedMsg.getType());
        assertFalse(deserializedMsg.hasSign());
        assertEquals(originalMsg, deserializedMsg);
    }

    @Test
    public void testPoToByteAndByteToPoWithEmptyStrings() throws InvalidProtocolBufferException {
        // 测试空字符串
        ProtocolMain.Msg originalMsg = ProtocolMain.Msg.newBuilder()
                .setType("")
                .setSign("")
                .build();

        log.info("原始消息 (空字符串): {}", originalMsg);
        assertEquals("", originalMsg.getType());
        assertEquals("", originalMsg.getSign());

        byte[] byteArray = originalMsg.toByteArray();
        ProtocolMain.Msg deserializedMsg = ProtocolMain.Msg.parseFrom(byteArray);

        assertEquals("", deserializedMsg.getType());
        assertEquals("", deserializedMsg.getSign());
        assertEquals(originalMsg, deserializedMsg);
    }

    @Test(expected = InvalidProtocolBufferException.class)
    public void testInvalidByteArrayThrowsException() throws InvalidProtocolBufferException {
        // 提供无效字节数组，期望抛出异常
        byte[] invalidBytes = new byte[]{0x00, 0x01, 0x02};
        ProtocolMain.Msg.parseFrom(invalidBytes);
    }

    @Test
    public void testToByteArrayAndParseFromAreInverse() throws InvalidProtocolBufferException {
        // 多个随机消息的往返测试
        String[] types = {"type1", "type2", "type3"};
        String[] signs = {"sign1", "sign2", null};

        for (String type : types) {
            for (String sign : signs) {
                ProtocolMain.Msg.Builder builder = ProtocolMain.Msg.newBuilder()
                        .setType(type);
                if (sign != null) {
                    builder.setSign(sign);
                }
                ProtocolMain.Msg original = builder.build();

                byte[] bytes = original.toByteArray();
                ProtocolMain.Msg restored = ProtocolMain.Msg.parseFrom(bytes);

                assertEquals("类型不匹配 type=" + type + ", sign=" + sign,
                        original.getType(), restored.getType());
                if (sign != null) {
                    assertEquals("标识不匹配 type=" + type + ", sign=" + sign,
                            original.getSign(), restored.getSign());
                    assertTrue(restored.hasSign());
                } else {
                    assertFalse(restored.hasSign());
                }
                assertEquals("相等性不匹配 type=" + type + ", sign=" + sign,
                        original, restored);
            }
        }
    }
}