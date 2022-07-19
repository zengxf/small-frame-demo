package test.base.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class Integer2ByteEncoder extends MessageToByteEncoder<Integer> {

    @Override
    public void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) {
        out.writeInt(msg);
        log.info("encoder Integer = " + msg);
    }

}
