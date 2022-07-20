package test.base.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test.util.ByteUtil;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class EmbeddedChannelTest {

    @Test
    public void testLogHandler() {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.DEBUG));
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes("中 123".getBytes(ByteUtil.UTF8));
        channel.writeInbound(buf);
    }

}
