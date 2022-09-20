package im.client.session;

import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Data
@Slf4j
public class ClientSession {

    public static final AttributeKey<ClientSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");

    /*** 用户实现客户端会话管理的核心 */
    private Channel channel;
    private User user;
    /*** 保存登录后的服务端 sessionId */
    private String sessionId;
    private boolean isConnected = false;
    private boolean isLogin = false;


    // 连接成功之后，绑定通道
    public ClientSession(Channel channel) {
        // 正向的绑定
        this.channel = channel;
        this.sessionId = UUID.randomUUID().toString();
        // 反向的绑定
        channel.attr(ClientSession.SESSION_KEY).set(this);
    }

    // 登录成功之后，设置 sessionId
    public static void loginSuccess(ChannelHandlerContext ctx, ProtoMsg.Message pkg) {
        Channel channel = ctx.channel();
        ClientSession session = channel.attr(ClientSession.SESSION_KEY).get();
        session.setSessionId(pkg.getSessionId());
        session.setLogin(true);
        log.info("登录成功");
    }

    // 获取 channel
    public static ClientSession getSession(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        ClientSession session = channel.attr(ClientSession.SESSION_KEY).get();
        return session;
    }

    // 关闭通道
    public void close() {
        isConnected = false;
        ChannelFuture cf = channel.close();
        cf.addListener(future -> {
            if (future.isSuccess()) {
                log.error("连接顺利断开");
            }
        });
    }

}
