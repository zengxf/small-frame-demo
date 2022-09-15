package im.server.handler;

import im.server.session.ServerSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Service
@ChannelHandler.Sharable
@Slf4j
public class ServerExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            // 远程连接，已经关闭
            log.error(cause.getMessage());
            log.error("客户端已经关闭连接");
            ServerSession.closeSession(ctx); // 做下线处理
        } else {
            log.error("出错！", cause);
        }
    }

    /*** 通道 Read 读取 Complete 完成，做刷新操作 */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ServerSession.closeSession(ctx);
    }

}
