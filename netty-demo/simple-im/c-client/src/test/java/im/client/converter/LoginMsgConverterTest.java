package im.client.converter;

import im.client.session.ClientSession;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.UUID;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@Slf4j
public class LoginMsgConverterTest {

    @Test
    public void testConverter() {
        ProtoMsg.Message message = LoginMsgConverter.build(getUser(), getSession());
        log.info("session id:= " + message.getSessionId());

        ProtoMsg.LoginRequest pkg = message.getLoginRequest();
        log.info("uid:= " + pkg.getUid());
        log.info("token:= " + pkg.getToken());
    }

    private ClientSession getSession() {
        // 创建会话
        ClientSession session = new ClientSession(new EmbeddedChannel());
        session.setConnected(true);
        return session;
    }

    private User getUser() {
        User user = new User();
        user.setUid("1");
        user.setToken(UUID.randomUUID().toString());
        user.setDevId(UUID.randomUUID().toString());
        return user;
    }

}