package im.server.session;

import im.common.dto.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Data
@Slf4j
public class ServerSession {

    public static final AttributeKey<String> KEY_USER_ID = AttributeKey.valueOf("key_user_id");
    public static final AttributeKey<ServerSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");

    private Channel channel; // 通道 用户实现服务端会话管理的核心
    private User user;
    private final String sessionId;
    private boolean isLogin = false;

    public ServerSession(Channel channel) {
        // 完成正向绑定
        this.channel = channel;
        this.sessionId = buildNewSessionId();
    }

    // 关闭连接
    public static void closeSession(ChannelHandlerContext ctx) {
        ServerSession session = ctx.channel().attr(ServerSession.SESSION_KEY).get();
        if (null != session && session.isValid()) {
            session.close();
            SessionMap.inst().removeSession(session.getSessionId());
        }
    }

    // 反向绑定，最终和 channel 通道实现双向绑定
    // 顺便加入到会话集合中
    public ServerSession reverseBind() {
        log.info("ServerSession 绑定会话 " + channel.remoteAddress());
        channel.attr(ServerSession.SESSION_KEY).set(this);
        SessionMap.inst().addSession(this);
        isLogin = true;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    private static String buildNewSessionId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public boolean isValid() {
        return getUser() != null;
    }

    // 写Protobuf数据帧
    public synchronized void writeAndFlush(Object pkg) {
        // 当系统水位过高时，系统应不继续发送消息，防止发送队列积压
        // 写 Protobuf 数据帧
        if (channel.isWritable()) { //低水位
            channel.writeAndFlush(pkg);
        } else {   // 高水位时
            log.debug("通道很忙，消息被暂存了");
            // 写入消息暂存的分布式存储
            // 等channel空闲之后，再写出去
        }
    }

    // 关闭连接
    public synchronized void close() {
        ChannelFuture future = channel.close();
        future.addListener(future1 -> {
            if (!future1.isSuccess()) {
                log.error("CHANNEL_CLOSED error ");
            }
        });
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.setSessionId(sessionId);
    }

}
