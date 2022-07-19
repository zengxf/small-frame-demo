package test.base.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import test.base.decoder.byte2int.IntegerAddDecoder;
import test.base.decoder.byte2int.IntegerProcessHandler;
import test.util.ThreadUtil;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
public class CodecTest {

    @Test
    public void testEncode() {
        ChannelInitializer<EmbeddedChannel> i = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                // ch.pipeline().addLast(new Byte2IntegerCodec());
                ch.pipeline().addLast(new IntegerDuplexHandler());
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

    @Test
    public void testDecode() {
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new Byte2IntegerCodec());
                // ch.pipeline().addLast(new IntegerDuplexHandler());
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
