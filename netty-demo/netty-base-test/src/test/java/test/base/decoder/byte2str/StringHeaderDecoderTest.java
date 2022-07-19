package test.base.decoder.byte2str;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test.util.ThreadUtil;

import java.nio.charset.Charset;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class StringHeaderDecoderTest {

    static String content = " 中文：测试! 123 ";

    /**
     * 字符串解码器的使用实例
     */
    @Test
    public void testStringReplayDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new StringHeaderDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        byte[] bytes = content.getBytes(Charset.forName("utf-8"));
        for (int j = 0; j < 3; j++) {
            int num = 3;
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(bytes.length * num);
            for (int k = 0; k < num; k++) {
                buf.writeBytes(bytes);
            }
            channel.writeInbound(buf);
        }
        ThreadUtil.sleep5s();
    }

}
