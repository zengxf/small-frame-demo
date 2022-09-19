package im.client.sender;

import im.client.session.ClientSession;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Data
@Slf4j
public class BaseSender {

    private User user;
    private ClientSession session;

    public boolean isConnected() {
        if (null == session) {
            log.info("session is null");
            return false;
        }
        return session.isConnected();
    }

    public boolean isLogin() {
        if (null == session) {
            log.info("session is null");
            return false;
        }

        return session.isLogin();
    }

    public void sendMsg(ProtoMsg.Message message) {
        if (null == getSession() || !isConnected()) {
            log.info("连接还没成功");
            return;
        }
        Channel channel = getSession().getChannel();
        ChannelFuture cf = channel.writeAndFlush(message);
        cf.addListener(future -> {
            // 回调
            if (future.isSuccess()) {
                sendSuccess(message);
            } else {
                sendFailed(message);
            }
        });
    }

    protected void sendSuccess(ProtoMsg.Message message) {
        log.info("发送成功");
    }

    protected void sendFailed(ProtoMsg.Message message) {
        log.info("发送失败");
    }

}