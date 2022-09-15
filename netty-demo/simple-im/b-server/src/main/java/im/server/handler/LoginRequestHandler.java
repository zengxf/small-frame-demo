package im.server.handler;

import im.common.cocurrent.CallbackTask;
import im.common.cocurrent.CallbackTaskScheduler;
import im.common.dto.msg.ProtoMsg;
import im.server.processor.LoginProcessor;
import im.server.session.ServerSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Service
@ChannelHandler.Sharable
@Slf4j
public class LoginRequestHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private LoginProcessor loginProcessor;
    @Autowired
    private ChatRedirectHandler chatRedirectHandler;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("收到一个新的连接，但是没有登录 {}", ctx.channel().id());
        super.channelActive(ctx);
    }

    /*** 收到消息 */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null == msg || !(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }

        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        ProtoMsg.HeadType headType = pkg.getType();      // 取得请求类型
        if (!headType.equals(loginProcessor.type())) {
            super.channelRead(ctx, msg);
            return;
        }

        ServerSession session = new ServerSession(ctx.channel());
        // 异步任务，处理登录的逻辑
        CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() {
                boolean r = loginProcessor.action(session, pkg);
                return r;
            }

            //异步任务返回
            @Override
            public void onBack(Boolean r) {
                if (r) {
                    ctx.pipeline().addAfter("login", "chat", chatRedirectHandler);
                    ctx.pipeline().addAfter("login", "heartBeat", new HeartBeatServerHandler());

                    ctx.pipeline().remove("login");
                    log.info("登录成功:" + session.getUser());
                } else {
                    ServerSession.closeSession(ctx);
                    log.info("登录失败:" + session.getUser());
                }
            }

            // 异步任务异常
            @Override
            public void onException(Throwable t) {
                ServerSession.closeSession(ctx);
                log.info("登录失败:" + session.getUser());
            }
        });
    }

}
