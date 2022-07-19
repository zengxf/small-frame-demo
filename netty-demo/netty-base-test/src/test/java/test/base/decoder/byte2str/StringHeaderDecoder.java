package test.base.decoder.byte2str;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class StringHeaderDecoder extends ByteToMessageDecoder { // 头是一个整数

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        // 可读大小小于 int，头还没读满，return
        if (buf.readableBytes() < 4) {
            return;
        }
        // 头已经完整
        // 在真正开始从 buffer 读取数据之前，调用 markReaderIndex() 设置回滚点
        // 回滚点为 header 的 readIndex 位置
        buf.markReaderIndex();
        // checkpoint
        int length = buf.readInt();
        // 从 buffer 中读出头的大小，这会使得 readIndex 前移
        // 剩余长度不够 body 体，reset 读指针
        if (buf.readableBytes() < length) {
            // 读指针回滚到 header 的 readIndex 位置处，没进行状态的保存
            buf.resetReaderIndex();
            return;
        }
        // 读取数据，编码成字符串
        byte[] inBytes = new byte[length];
        buf.readBytes(inBytes, 0, length);
        out.add(new String(inBytes, "UTF-8"));
    }

}
