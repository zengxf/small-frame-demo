package im.server.handler;

import im.common.cocurrent.FutureTaskScheduler;
import im.common.dto.msg.ProtoMsg;
import im.server.processor.ChatRedirectProcessor;
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
public class ChatRedirectHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ChatRedirectProcessor chatRedirectProcessor;

    /*** 收到消息 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断消息实例
        if (null == msg || !(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }

        // 判断消息类型
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        ProtoMsg.HeadType headType = ((ProtoMsg.Message) msg).getType();
        if (!headType.equals(chatRedirectProcessor.type())) {
            super.channelRead(ctx, msg);
            return;
        }

        // 反向导航
        ServerSession session = ctx.channel().attr(ServerSession.SESSION_KEY).get();
        // 判断是否登录
        if (null == session || !session.isLogin()) {
            log.error("用户尚未登录，不能发送消息");
            return;
        }

        // 异步处理IM消息转发的逻辑
        FutureTaskScheduler.add(() -> {
            chatRedirectProcessor.action(session, pkg);
        });
    }

}
