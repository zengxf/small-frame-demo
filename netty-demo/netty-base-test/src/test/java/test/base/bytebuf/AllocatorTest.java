package test.base.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/15.
 */
@Slf4j
public class AllocatorTest {

    @Test
    public void showAllocParam() {
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBufAllocator allocator = ctx.alloc();
                ByteBufAllocator allocator1 = ctx.channel().alloc();
                log.info("{} - has: {}", allocator, System.identityHashCode(allocator));
                log.info("{} - has: {}", allocator1, System.identityHashCode(allocator1));

                ByteBuf buffer = allocator.buffer();
                buffer.writeInt(0x1234);
                log.info("{}", buffer);
                log.info("{} - has: {}", buffer.alloc(), System.identityHashCode(buffer.alloc()));
                ctx.fireChannelRead(buffer);
            }
        });
        channel.writeInbound(new Object());
    }

}
