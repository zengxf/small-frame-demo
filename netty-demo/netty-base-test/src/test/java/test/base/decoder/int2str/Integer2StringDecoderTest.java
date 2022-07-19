package test.base.decoder.int2str;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import test.base.decoder.byte2int.Byte2IntegerDecoder;
import test.base.decoder.byte2str.StringProcessHandler;
import test.util.ThreadUtil;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
public class Integer2StringDecoderTest {

    @Test
    public void testIntegerToStringDecoder() {
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new Byte2IntegerDecoder());
                ch.pipeline().addLast(new Integer2StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 1; j <= 5; j++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(j);
            buf.writeInt(j * 11);
            channel.writeInbound(buf);
        }
        ThreadUtil.sleep5s();
    }

}
