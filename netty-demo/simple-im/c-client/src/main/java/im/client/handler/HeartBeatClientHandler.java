package im.client.handler;

import im.client.converter.HeartBeatMsgConverter;
import im.client.session.ClientSession;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@ChannelHandler.Sharable
@Service
@Slf4j
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    // 心跳的时间间隔，单位 SECONDS
    private static final int HEARTBEAT_INTERVAL = 50;

    // 在 Handler 被加入到 Pipeline 时，开始发送心跳
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ClientSession session = ClientSession.getSession(ctx);
        User user = session.getUser();
        HeartBeatMsgConverter builder = new HeartBeatMsgConverter(user, session);
        ProtoMsg.Message message = builder.build();
        // 发送心跳
        this.heartBeat(ctx, message);
    }

    // 使用定时器，发送心跳报文
    public void heartBeat(ChannelHandlerContext ctx, ProtoMsg.Message heartbeatMsg) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                log.info(" 发送 HEART_BEAT 消息");
                ctx.writeAndFlush(heartbeatMsg);
                // 递归调用，发送下一次的心跳
                heartBeat(ctx, heartbeatMsg);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

    /*** 接受到服务器的心跳回写 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断消息实例
        if (msg == null || !(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }
        // 判断类型
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        ProtoMsg.HeadType headType = pkg.getType();
        if (headType.equals(ProtoMsg.HeadType.HEART_BEAT)) {
            log.info("收到回写的 HEART_BEAT 消息");
        } else {
            super.channelRead(ctx, msg);
        }
    }

}
