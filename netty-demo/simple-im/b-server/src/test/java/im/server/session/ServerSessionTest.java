package im.server.session;

import im.common.dto.User;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@Slf4j
public class ServerSessionTest {

    @Test
    public void testSessionBind() {
        ServerSession serverSession = new ServerSession(new EmbeddedChannel());
        serverSession.setUser(new User());
        log.info("user: [{}]", serverSession.getUser());

        serverSession.reverseBind();
        Channel channel = serverSession.getChannel();

        Attribute<ServerSession> session = channel.attr(ServerSession.SESSION_KEY);
        log.info("get-user: [{}]", session.get().getUser());
    }

    @Test
    public void testSessionFind() {
        User user = new User();
        for (int i = 0; i < 5; i++) {
            ServerSession serverSession = new ServerSession(new EmbeddedChannel());
            serverSession.setUser(user);
            serverSession.reverseBind();
        }
        List<ServerSession> list = SessionMap.inst().getSessionsBy(user.getUid());
        log.info("找到的 session 数量：[{}]", list.size());
        list.forEach(s ->
                log.info("session-id: [{}]", s.getSessionId())
        );
    }

}