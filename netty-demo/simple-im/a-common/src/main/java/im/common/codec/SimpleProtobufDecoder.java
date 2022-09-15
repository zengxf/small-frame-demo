package im.common.codec;

import im.common.ProtoInstant;
import im.common.dto.msg.ProtoMsg;
import im.common.exception.InvalidFrameException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Slf4j
public class SimpleProtobufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object outMsg = decode0(ctx, in);
        if (outMsg != null) {
            out.add(outMsg);  // 获取业务消息
        }
    }

    public static Object decode0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 标记一下当前的 readIndex 的位置
        in.markReaderIndex();
        // 判断包头长度
        if (in.readableBytes() < 8) { // 不够包头
            return null;
        }
        // 读取魔数
        short magic = in.readShort();
        if (magic != ProtoInstant.MAGIC_CODE) {
            String error = "客户端口令不对: " + ctx.channel().remoteAddress();
            // 异常连接，直接报错，关闭连接
            throw new InvalidFrameException(error);
        }
        // 读取版本
        short version = in.readShort();
        if (version != ProtoInstant.VERSION_CODE) {
            String error = "协议的版本不对: " + ctx.channel().remoteAddress();
            // 异常连接，直接报错，关闭连接
            throw new InvalidFrameException(error);
        }

        int length = in.readInt(); // 读取传送过来的消息的长度。

        log.info("parse msg length = {}", length);
        if (length < 0) {   // 长度如果小于 0
            ctx.close();    // 非法数据，关闭连接
        }
        log.info("decoder length = {}", in.readableBytes());
        if (in.readableBytes() < length) {  // 读到的消息体长度如果小于传送过来的消息长度
            in.resetReaderIndex();          // 重置读取位置
            return null;
        }

        byte[] array = new byte[length];
        in.readBytes(array, 0, length);

        // 字节转成对象
        ProtoMsg.Message outMsg = ProtoMsg.Message.parseFrom(array);
        return outMsg;
    }

}
