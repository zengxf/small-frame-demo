package im.common.codec;

import im.common.ProtoInstant;
import im.common.dto.msg.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Slf4j
public class SimpleProtobufEncoder extends MessageToByteEncoder<ProtoMsg.Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoMsg.Message msg, ByteBuf out) {
        encode0(msg, out);
    }

    public static void encode0(ProtoMsg.Message msg, ByteBuf out) {
        out.writeShort(ProtoInstant.MAGIC_CODE);
        out.writeShort(ProtoInstant.VERSION_CODE);

        byte[] bytes = msg.toByteArray();   // 将 ProtoMsg.Message 对象转换为 byte
        int length = bytes.length;          // 读取消息的长度
        log.info("encoder length = {}", length);

        // 先将消息长度写入，也就是消息头
        out.writeInt(length);
        // 消息体中包含我们要发送的数据
        out.writeBytes(bytes);
    }

}