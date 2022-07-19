package test.base.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
public class Integer2ByteEncoderTest {

    @Test
    public void testIntegerToByteDecoder() {
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new Integer2ByteEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 0; j < 3; j++) {
            channel.writeAndFlush(j);
        }
        channel.flush();
        // 取得通道的出站数据帧
        ByteBuf buf = channel.readOutbound();
        while (null != buf) {
            System.out.println("o = " + buf.readInt());
            buf = channel.readOutbound();
        }
    }

}
