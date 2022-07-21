package test.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/21.
 */
@Slf4j
public class SimpleMsgTest {

    public static SimpleMsg.Msg buildMsg() {
        SimpleMsg.Msg.Builder builder = SimpleMsg.Msg.newBuilder();
        builder.setId(68);
        builder.setContent("中-11111");
        builder.setStatus(1);
        builder.setUserName("顺为");
        SimpleMsg.Msg message = builder.build();
        return message;
    }


    // 第 1 种方式: 序列化 & 反序列化
    @Test
    public void serAndDes1() throws IOException {
        SimpleMsg.Msg message = buildMsg();
        // 将 Protobuf 对象，序列化成二进制字节数组
        byte[] data = message.toByteArray();
        // 可以用于网络传输, 保存到内存或外存
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(data);
        data = outputStream.toByteArray();
        // 二进制字节数组, 反序列化成 Protobuf 对象
        SimpleMsg.Msg inMsg = SimpleMsg.Msg.parseFrom(data);
        log.info("devId:= " + inMsg.getId());
        log.info("content:= " + inMsg.getContent());
        log.info("ByteString:= " + inMsg.getContentBytes());
        log.info("toString:= " + inMsg);
    }

    // 第 2 种方式: 序列化 & 反序列化
    @Test
    public void serAndDes2() throws IOException {
        SimpleMsg.Msg message = buildMsg();
        // 序列化到二进制流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        // 从二进流, 反序列化成 Protobuf 对象
        SimpleMsg.Msg inMsg = SimpleMsg.Msg.parseFrom(inputStream);
        log.info("devId:= " + inMsg.getId());
        log.info("content:= " + inMsg.getContent());
    }

    // 第 3 种方式: 序列化 & 反序列化
    // 带字节长度：[字节长度][字节数据], 解决粘包问题
    @Test
    public void serAndDes3() throws IOException {
        SimpleMsg.Msg message = buildMsg();
        // 序列化到二进制流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeDelimitedTo(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        // 从二进流, 反序列化成 Protobuf 对象
        SimpleMsg.Msg inMsg = SimpleMsg.Msg.parseDelimitedFrom(inputStream);
        log.info("devId:= " + inMsg.getId());
        log.info("content:= " + inMsg.getContent());
    }

}