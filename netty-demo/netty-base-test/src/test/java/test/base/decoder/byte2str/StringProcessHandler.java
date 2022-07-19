package test.base.decoder.byte2str;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/19.
 */
@Slf4j
public class StringProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            String s = (String) msg;
            System.out.println("打印: " + s);
        } else {
            super.channelRead(ctx, msg);
        }
    }

}
