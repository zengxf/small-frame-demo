package test.echo_service.sockets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
public class ServerChatHandler extends SimpleChannelInboundHandler<String> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("恭喜进入安全聊天器\n");
        channels.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /*** 消息群发 */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // Send the received message to all channels but the current one.
        for (Channel c : channels) {
            if (c != ctx.channel()) {
                // 不是本人
                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
            } else {
                c.writeAndFlush("[本人] " + msg + '\n');
            }
        }
        // 收到 bye，关闭连接
        if ("bye".equalsIgnoreCase(msg)) {
            ctx.close();
        }
    }

}