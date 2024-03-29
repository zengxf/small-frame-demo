package im.client.handler;

import im.client.session.ClientSession;
import im.common.ProtoInstant;
import im.common.dto.msg.ProtoMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
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
public class LoginResponseHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ChatMsgHandler chatMsgHandler;
    @Autowired
    private HeartBeatClientHandler heartBeatClientHandler;

    /*** 业务逻辑处理 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断消息实例
        if (msg == null || !(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }
        // 判断类型
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        ProtoMsg.HeadType headType = ((ProtoMsg.Message) msg).getType();
        if (!headType.equals(ProtoMsg.HeadType.LOGIN_RESPONSE)) {
            super.channelRead(ctx, msg);
            return;
        }
        // 判断返回是否成功
        ProtoMsg.LoginResponse info = pkg.getLoginResponse();
        ProtoInstant.ResultCodeEnum result = ProtoInstant.ResultCodeEnum.values()[info.getCode()];

        if (!result.equals(ProtoInstant.ResultCodeEnum.SUCCESS)) {
            // 登录失败
            log.error(result.getDesc());
        } else {
            // 登录成功
            ClientSession.loginSuccess(ctx, pkg);
            ChannelPipeline p = ctx.pipeline();
            // 移除登录响应处理器
            p.remove(this);
            // 在编码器后面，动态插入心跳处理器
            p.addAfter("encoder", "heartbeat", heartBeatClientHandler);
            p.addAfter("encoder", "chat", chatMsgHandler);
            heartBeatClientHandler.channelActive(ctx);
        }
    }

}
