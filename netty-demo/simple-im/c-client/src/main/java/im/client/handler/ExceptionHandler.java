package im.client.handler;

import im.client.client.CommandController;
import im.client.session.ClientSession;
import im.common.exception.InvalidFrameException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@ChannelHandler.Sharable
@Service
@Slf4j
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private CommandController commandController;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof InvalidFrameException) {
            log.error(cause.getMessage());
            ClientSession.getSession(ctx).close();
        } else {
            log.error("捕获到异常，异常消息：{}", cause.getMessage(), cause);
            ctx.close();
            if (commandController == null) {
                log.info("Unit test !!!");
                return;
            }
            // 开始重连
            commandController.setConnectFlag(false);
            commandController.startConnectServer();
        }
    }

    /**
     * 通道 Read 读取 Complete 完成
     * 做刷新操作 ctx.flush()
     */
    // public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //     // ctx.flush();
    // }

}