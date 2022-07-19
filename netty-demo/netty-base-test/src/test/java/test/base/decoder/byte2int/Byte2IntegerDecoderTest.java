package test.base.decoder.byte2int;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test.util.ThreadUtil;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class Byte2IntegerDecoderTest {

    @Test
    public void testByteToIntegerDecoder() {
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                // ch.pipeline().addLast(new Byte2IntegerDecoder());
                // ch.pipeline().addLast(new Byte2IntegerReplayDecoder());
                ch.pipeline().addLast(new IntegerAddDecoder());
                ch.pipeline().addLast(new IntegerProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);

        for (int j = 1; j <= 5; j++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(j);
            buf.writeInt(j * 11);
            buf.writeInt(j * 111);
            channel.writeInbound(buf);
        }

        ThreadUtil.sleep5s();
    }

}
