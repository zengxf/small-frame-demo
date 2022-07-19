package test.base.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class String2IntegerEncoderTest {

    @Test
    public void testStringToIntegerDecoder() {
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new Integer2ByteEncoder());
                ch.pipeline().addLast(new String2IntegerEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 1; j <= 3; j++) {
            String s = "i am " + j * 23;
            System.out.println("s: " + s);
            channel.writeAndFlush(s);
        }
        channel.flush();
        ByteBuf buf = channel.readOutbound();
        while (null != buf) {
            System.out.println("o = " + buf.readInt());
            buf = channel.readOutbound();
        }
    }

}
