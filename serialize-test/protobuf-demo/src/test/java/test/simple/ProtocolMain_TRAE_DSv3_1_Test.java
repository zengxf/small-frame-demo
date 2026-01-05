package test.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * 使用 TRAE DSv3.1 生成 <br/>
 * (用 TRAE 插件体验不太好，没有 Kilo Code 爽)
 * <br/>
 * ProtocolMain 单元测试
 * <br/>
 * 测试 ProtocolMain.Msg 的序列化和反序列化功能
 * 使用 JUnit 断言进行验证
 */
@Slf4j
public class ProtocolMain_TRAE_DSv3_1_Test {

    /**
     * 构建测试用的 ProtocolMain.Msg 对象
     */
    public static ProtocolMain.Msg buildMsg() {
        ProtocolMain.Msg.Builder builder = ProtocolMain.Msg.newBuilder();
        builder.setType("test_message_type");
        builder.setSign("test_signature");
        ProtocolMain.Msg message = builder.build();
        return message;
    }

    /**
     * 测试 PO 转 byte 数组的基本功能
     * 验证对象能够正确序列化为字节数组
     */
    @Test
    public void testPoToByteArray() {
        ProtocolMain.Msg message = buildMsg();
        
        // PO 转 byte 数组
        byte[] data = message.toByteArray();
        
        log.info("序列化后的字节数组长度: {}", data.length);
        log.info("原始消息类型: {}", message.getType());
        log.info("原始消息标识: {}", message.getSign());
        
        // 使用 JUnit 断言验证序列化结果
        assertNotNull("序列化结果不应为null", data);
        assertTrue("序列化结果长度应大于0", data.length > 0);
    }

    /**
     * 测试 byte 数组转 PO 的基本功能
     * 验证字节数组能够正确反序列化为对象
     */
    @Test
    public void testByteArrayToPo() throws IOException {
        ProtocolMain.Msg originalMessage = buildMsg();
        
        // PO 转 byte 数组
        byte[] data = originalMessage.toByteArray();
        
        // byte 数组转 PO
        ProtocolMain.Msg deserializedMessage = ProtocolMain.Msg.parseFrom(data);
        
        log.info("反序列化成功");
        log.info("原始消息类型: {}", originalMessage.getType());
        log.info("反序列化消息类型: {}", deserializedMessage.getType());
        
        // 使用 JUnit 断言验证反序列化结果正确
        assertEquals("消息类型应一致", originalMessage.getType(), deserializedMessage.getType());
        assertEquals("消息标识应一致", originalMessage.getSign(), deserializedMessage.getSign());
    }

    /**
     * 测试序列化到文件并读取
     * 验证文件读写功能
     */
    @Test
    public void testSerializeToFile() throws IOException {
        ProtocolMain.Msg message = buildMsg();
        
        // 序列化到字节数组
        byte[] data = message.toByteArray();
        
        // 写入文件
        String filePath = "test_msg.dat";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        }
        
        log.info("消息已序列化到文件: {}", filePath);
        
        // 从文件读取
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));
        
        // 反序列化
        ProtocolMain.Msg fileMsg = ProtocolMain.Msg.parseFrom(fileData);
        
        // 验证文件读写正确性
        assertEquals("文件读取的消息类型应一致", message.getType(), fileMsg.getType());
        assertEquals("文件读取的消息标识应一致", message.getSign(), fileMsg.getSign());
        
        // 清理测试文件
        Files.deleteIfExists(Paths.get(filePath));
        log.info("测试文件已清理");
    }

    /**
     * 第 1 种方式: 完整的序列化 & 反序列化测试
     * 使用 toByteArray() 和 parseFrom() 方法
     */
    @Test
    public void testSerAndDes1() throws IOException {
        ProtocolMain.Msg message = buildMsg();
        
        // 将 Protobuf 对象序列化成二进制字节数组 (PO 转 byte)
        byte[] data = message.toByteArray();
        log.info("序列化数据长度: {}", data.length);
        
        // 可以用于网络传输, 保存到内存或外存
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(data);
        data = outputStream.toByteArray();
        
        // 二进制字节数组反序列化成 Protobuf 对象 (byte 转 PO)
        ProtocolMain.Msg inMsg = ProtocolMain.Msg.parseFrom(data);
        
        printMsgInfo(inMsg);
        log.info("toString:= \n{}", inMsg);
        
        // 使用 JUnit 断言验证数据一致性
        assertEquals("消息类型应一致", message.getType(), inMsg.getType());
        assertEquals("消息标识应一致", message.getSign(), inMsg.getSign());
    }

    /**
     * 第 2 种方式: 使用流进行序列化 & 反序列化
     * 使用 writeTo() 和 parseFrom(InputStream) 方法
     */
    @Test
    public void testSerAndDes2() throws IOException {
        ProtocolMain.Msg message = buildMsg();
        
        // 序列化到二进制流 (PO 转 byte)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        
        // 从二进制流反序列化成 Protobuf 对象 (byte 转 PO)
        ProtocolMain.Msg inMsg = ProtocolMain.Msg.parseFrom(inputStream);
        
        printMsgInfo(inMsg);
        
        // 使用 JUnit 断言验证数据一致性
        assertEquals("消息类型应一致", message.getType(), inMsg.getType());
        assertEquals("消息标识应一致", message.getSign(), inMsg.getSign());
    }

    /**
     * 第 3 种方式: 带字节长度的序列化 & 反序列化
     * 使用 writeDelimitedTo() 和 parseDelimitedFrom() 方法
     * 解决粘包问题：[字节长度][字节数据]
     */
    @Test
    public void testSerAndDes3() throws IOException {
        ProtocolMain.Msg message = buildMsg();
        
        // 序列化到二进制流，带长度前缀 (PO 转 byte)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeDelimitedTo(outputStream);
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        
        // 从二进制流反序列化成 Protobuf 对象，带长度解析 (byte 转 PO)
        ProtocolMain.Msg inMsg = ProtocolMain.Msg.parseDelimitedFrom(inputStream);
        
        printMsgInfo(inMsg);
        
        // 使用 JUnit 断言验证数据一致性
        assertEquals("消息类型应一致", message.getType(), inMsg.getType());
        assertEquals("消息标识应一致", message.getSign(), inMsg.getSign());
    }

    /**
     * 测试可选字段的功能
     * 验证可选字段的正确处理
     */
    @Test
    public void testOptionalField() {
        // 测试有 sign 字段的情况
        ProtocolMain.Msg.Builder builder1 = ProtocolMain.Msg.newBuilder();
        builder1.setType("type_with_sign");
        builder1.setSign("optional_sign");
        ProtocolMain.Msg msgWithSign = builder1.build();
        
        // 使用 JUnit 断言验证可选字段
        assertTrue("sign 字段应存在", msgWithSign.hasSign());
        assertEquals("sign 字段值应正确", "optional_sign", msgWithSign.getSign());
        
        // 测试没有 sign 字段的情况
        ProtocolMain.Msg.Builder builder2 = ProtocolMain.Msg.newBuilder();
        builder2.setType("type_without_sign");
        ProtocolMain.Msg msgWithoutSign = builder2.build();
        
        assertFalse("sign 字段不应存在", msgWithoutSign.hasSign());
        
        log.info("有标识字段的消息: {}", msgWithSign);
        log.info("无标识字段的消息: {}", msgWithoutSign);
    }

    /**
     * 测试边界情况：空字符串和特殊字符
     */
    @Test
    public void testEdgeCases() throws IOException {
        // 测试空字符串
        ProtocolMain.Msg.Builder builder1 = ProtocolMain.Msg.newBuilder();
        builder1.setType("");
        builder1.setSign("");
        ProtocolMain.Msg emptyMsg = builder1.build();
        
        byte[] data = emptyMsg.toByteArray();
        ProtocolMain.Msg deserializedEmptyMsg = ProtocolMain.Msg.parseFrom(data);
        
        assertEquals("空字符串类型应一致", "", deserializedEmptyMsg.getType());
        assertEquals("空字符串标识应一致", "", deserializedEmptyMsg.getSign());
        
        // 测试特殊字符
        ProtocolMain.Msg.Builder builder2 = ProtocolMain.Msg.newBuilder();
        builder2.setType("特殊字符测试：中文、😀、\\n");
        builder2.setSign("sign_with_special_chars");
        ProtocolMain.Msg specialMsg = builder2.build();
        
        data = specialMsg.toByteArray();
        ProtocolMain.Msg deserializedSpecialMsg = ProtocolMain.Msg.parseFrom(data);
        
        assertEquals("特殊字符类型应一致", "特殊字符测试：中文、😀、\\n", deserializedSpecialMsg.getType());
        assertEquals("特殊字符标识应一致", "sign_with_special_chars", deserializedSpecialMsg.getSign());
        
        log.info("空字符串测试通过");
        log.info("特殊字符测试通过");
    }

    /**
     * 打印消息信息
     */
    private static void printMsgInfo(ProtocolMain.Msg msg) {
        log.info("消息类型: {}", msg.getType());
        if (msg.hasSign()) {
            log.info("消息标识: {}", msg.getSign());
        } else {
            log.info("消息标识: [无]");
        }
    }

    /**
     * 性能测试：多次序列化和反序列化
     */
    @Test
    public void testPerformance() throws IOException {
        int iterations = 1000;
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < iterations; i++) {
            ProtocolMain.Msg message = buildMsg();
            byte[] data = message.toByteArray();
            ProtocolMain.Msg deserialized = ProtocolMain.Msg.parseFrom(data);
            
            // 使用 JUnit 断言验证数据正确性
            assertEquals("第 " + (i + 1) + " 次迭代消息类型应一致", 
                         message.getType(), deserialized.getType());
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        log.info("性能测试: {} 次序列化/反序列化耗时: {} ms", iterations, duration);
        assertTrue("性能测试应在合理时间内完成", duration < 10000); // 10秒内完成
    }

    /**
     * 测试异常情况：无效字节数组
     */
    @Test(expected = com.google.protobuf.InvalidProtocolBufferException.class)
    public void testInvalidByteArray() throws IOException {
        // 创建无效的字节数组
        byte[] invalidData = new byte[]{0, 1, 2, 3, 4};
        
        // 应抛出 InvalidProtocolBufferException
        ProtocolMain.Msg.parseFrom(invalidData);
    }

    /**
     * 测试构建器模式
     */
    @Test
    public void testBuilderPattern() {
        ProtocolMain.Msg.Builder builder = ProtocolMain.Msg.newBuilder();
        builder.setType("builder_test");
        builder.setSign("builder_sign");
        
        ProtocolMain.Msg message = builder.build();
        
        // 验证构建器创建的对象
        assertEquals("构建器创建的类型应正确", "builder_test", message.getType());
        assertEquals("构建器创建的标识应正确", "builder_sign", message.getSign());
        assertTrue("构建器创建的标识应存在", message.hasSign());
    }
}
