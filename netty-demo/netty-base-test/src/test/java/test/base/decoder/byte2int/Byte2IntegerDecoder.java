package test.base.decoder.byte2int;

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
public class Byte2IntegerDecoder extends ByteToMessageDecoder {

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        while (in.readableBytes() >= 4) {
            Integer anInt = in.readInt();
            log.info("解码出一个整数: " + anInt);
            out.add(anInt);
        }
    }

}