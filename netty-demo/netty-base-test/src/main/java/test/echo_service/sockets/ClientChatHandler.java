package test.echo_service.sockets;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
@Slf4j
public class ClientChatHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /*** 输出从服务端收到的响应 */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("msg: [{}]", msg);
    }

}
