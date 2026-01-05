package test.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * GLM-4.7 生成 (Kilo Code 插件)
 * <br/>
 * ProtocolMain 单元测试类
 * 测试 ProtocolMain.Msg 对象的序列化和反序列化功能
 * <br/>
 * Created by Kilo Code on 2026/1/5.
 */
@Slf4j
public class ProtocolMain_Kilo_GLM4_7_Test {

    /**
     * 构建测试用的 ProtocolMain.Msg 对象
     *
     * @return 构建好的 Msg 对象
     */
    public static ProtocolMain.Msg buildMsg() {
        ProtocolMain.Msg.Builder builder = ProtocolMain.Msg.newBuilder();
        // 设置消息类型（必需字段）
        builder.setType("LOGIN_REQUEST");
        // 设置标识（可选字段）
        builder.setSign("signature-12345");
        ProtocolMain.Msg message = builder.build();
        return message;
    }

    /**
     * 构建不包含可选字段的测试对象
     *
     * @return 构建好的 Msg 对象（只有 type 字段）
     */
    public static ProtocolMain.Msg buildMsgWithoutOptional() {
        ProtocolMain.Msg.Builder builder = ProtocolMain.Msg.newBuilder();
        // 只设置必需字段
        builder.setType("LOGOUT_REQUEST");
        ProtocolMain.Msg message = builder.build();
        return message;
    }

    /**
     * 测试方式1: PO 转 byte 和 byte 转 PO
     * 使用 toByteArray() 和 parseFrom(byte[]) 方法
     */
    @Test
    public void testPoToByteAndByteToPo() throws IOException {
        log.info("========== 测试方式1: PO 转 byte 和 byte 转 PO ==========");
        
        ProtocolMain.Msg originalMsg = buildMsg();
        printOriginalMsgInfo(originalMsg);
        
        // PO 转 byte（序列化）
        byte[] data = originalMsg.toByteArray();
        printSerializationResult(data);
        
        // byte 转 PO（反序列化）
        ProtocolMain.Msg parsedMsg = ProtocolMain.Msg.parseFrom(data);
        printParsedMsgInfo(parsedMsg);
        
        // 验证数据一致性
        verifyDataConsistency(originalMsg, parsedMsg);
        log.info("\n完整对象信息:\n{}", parsedMsg);
    }

    /**
     * 测试方式2: 使用流进行序列化和反序列化
     * 使用 writeTo() 和 parseFrom(InputStream) 方法
     */
    @Test
    public void testSerializeWithStream() throws IOException {
        log.info("========== 测试方式2: 使用流进行序列化和反序列化 ==========");
        
        ProtocolMain.Msg originalMsg = buildMsg();
        printOriginalMsgInfo(originalMsg);
        
        // PO 转 byte（序列化到输出流）
        byte[] data = serializeToStream(originalMsg);
        printSerializationResult(data);
        
        // byte 转 PO（从输入流反序列化）
        ProtocolMain.Msg parsedMsg = deserializeFromStream(data);
        printParsedMsgInfo(parsedMsg);
        
        // 验证数据一致性
        verifyDataConsistency(originalMsg, parsedMsg);
    }

    /**
     * 测试方式3: 使用带长度前缀的序列化（解决粘包问题）
     * 使用 writeDelimitedTo() 和 parseDelimitedFrom() 方法
     */
    @Test
    public void testSerializeWithLengthPrefix() throws IOException {
        log.info("========== 测试方式3: 使用带长度前缀的序列化 ==========");
        
        ProtocolMain.Msg originalMsg = buildMsg();
        printOriginalMsgInfo(originalMsg);
        
        // PO 转 byte（带长度前缀的序列化）
        byte[] data = serializeWithLengthPrefix(originalMsg);
        log.info("\n序列化结果（带长度前缀）:");
        log.info("  字节数组长度: {} 字节", data.length);
        log.info("  字节数组内容: {}", bytesToHex(data));
        
        // byte 转 PO（从带长度前缀的输入流反序列化）
        ProtocolMain.Msg parsedMsg = deserializeWithLengthPrefix(data);
        printParsedMsgInfo(parsedMsg);
        
        // 验证数据一致性
        verifyDataConsistency(originalMsg, parsedMsg);
    }

    /**
     * 测试不包含可选字段的序列化和反序列化
     */
    @Test
    public void testWithoutOptionalField() throws IOException {
        log.info("========== 测试不包含可选字段的序列化和反序列化 ==========");
        
        ProtocolMain.Msg originalMsg = buildMsgWithoutOptional();
        printOriginalMsgInfo(originalMsg);
        
        // PO 转 byte
        byte[] data = originalMsg.toByteArray();
        printSerializationResult(data);
        
        // byte 转 PO
        ProtocolMain.Msg parsedMsg = ProtocolMain.Msg.parseFrom(data);
        printParsedMsgInfo(parsedMsg);
        
        // 验证数据一致性
        verifyDataConsistency(originalMsg, parsedMsg);
    }

    /**
     * 测试中文内容的序列化和反序列化
     */
    @Test
    public void testChineseContent() throws IOException {
        log.info("========== 测试中文内容的序列化和反序列化 ==========");
        
        // 创建包含中文的 PO 对象
        ProtocolMain.Msg.Builder builder = ProtocolMain.Msg.newBuilder();
        builder.setType("登录请求");
        builder.setSign("签名-中文测试");
        ProtocolMain.Msg originalMsg = builder.build();
        
        printOriginalMsgInfo(originalMsg);
        
        // PO 转 byte
        byte[] data = originalMsg.toByteArray();
        log.info("\n序列化结果:");
        log.info("  字节数组长度: {} 字节", data.length);
        log.info("  字节数组内容: {}", bytesToHex(data));
        
        // byte 转 PO
        ProtocolMain.Msg parsedMsg = ProtocolMain.Msg.parseFrom(data);
        printParsedMsgInfo(parsedMsg);
        
        // 验证数据一致性
        verifyDataConsistency(originalMsg, parsedMsg);
        log.info("  中文内容是否正确: {}", 
                "登录请求".equals(parsedMsg.getType()) && 
                "签名-中文测试".equals(parsedMsg.getSign()));
    }

    /**
     * 测试多次序列化和反序列化
     */
    @Test
    public void testMultipleSerialization() throws IOException {
        log.info("========== 测试多次序列化和反序列化 ==========");
        
        ProtocolMain.Msg originalMsg = buildMsg();
        
        // 第一次序列化和反序列化
        byte[] data1 = originalMsg.toByteArray();
        ProtocolMain.Msg parsedMsg1 = ProtocolMain.Msg.parseFrom(data1);
        log.info("第一次序列化:");
        log.info("  字节数组长度: {} 字节", data1.length);
        log.info("  数据一致性: {}", originalMsg.equals(parsedMsg1));
        
        // 第二次序列化和反序列化
        byte[] data2 = parsedMsg1.toByteArray();
        ProtocolMain.Msg parsedMsg2 = ProtocolMain.Msg.parseFrom(data2);
        log.info("\n第二次序列化:");
        log.info("  字节数组长度: {} 字节", data2.length);
        log.info("  数据一致性: {}", parsedMsg1.equals(parsedMsg2));
        
        // 验证多次序列化的字节数组是否一致
        log.info("\n多次序列化的字节数组是否一致: {}", java.util.Arrays.equals(data1, data2));
    }

    // ==================== 辅助方法 ====================

    /**
     * 打印原始 PO 对象信息
     */
    private static void printOriginalMsgInfo(ProtocolMain.Msg msg) {
        log.info("原始 PO 对象信息:");
        printMsgInfo(msg);
    }

    /**
     * 打印反序列化后的 PO 对象信息
     */
    private static void printParsedMsgInfo(ProtocolMain.Msg msg) {
        log.info("\n反序列化后的 PO 对象信息:");
        printMsgInfo(msg);
    }

    /**
     * 打印序列化结果
     */
    private static void printSerializationResult(byte[] data) {
        log.info("\n序列化结果:");
        log.info("  字节数组长度: {} 字节", data.length);
    }

    /**
     * 验证数据一致性
     */
    private static void verifyDataConsistency(ProtocolMain.Msg originalMsg, ProtocolMain.Msg parsedMsg) {
        log.info("\n数据一致性验证:");
        log.info("  type 是否相等: {}", originalMsg.getType().equals(parsedMsg.getType()));
        log.info("  sign 是否相等: {}", originalMsg.hasSign() == parsedMsg.hasSign() 
                && (!originalMsg.hasSign() || originalMsg.getSign().equals(parsedMsg.getSign())));
        log.info("  对象是否相等: {}", originalMsg.equals(parsedMsg));
    }

    /**
     * 序列化到流
     */
    private static byte[] serializeToStream(ProtocolMain.Msg msg) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        msg.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 从流反序列化
     */
    private static ProtocolMain.Msg deserializeFromStream(byte[] data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ProtocolMain.Msg.parseFrom(inputStream);
    }

    /**
     * 带长度前缀的序列化
     */
    private static byte[] serializeWithLengthPrefix(ProtocolMain.Msg msg) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        msg.writeDelimitedTo(outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 从带长度前缀的流反序列化
     */
    private static ProtocolMain.Msg deserializeWithLengthPrefix(byte[] data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ProtocolMain.Msg.parseDelimitedFrom(inputStream);
    }

    /**
     * 打印消息信息
     */
    private static void printMsgInfo(ProtocolMain.Msg msg) {
        log.info("  type: {}", msg.getType());
        log.info("  sign: {}", msg.hasSign() ? msg.getSign() : "未设置");
        log.info("  hasSign: {}", msg.hasSign());
        log.info("  typeBytes: {}", msg.getTypeBytes());
        if (msg.hasSign()) {
            log.info("  signBytes: {}", msg.getSignBytes());
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }
}
