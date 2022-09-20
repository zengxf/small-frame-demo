package im.client.session;

import im.common.dto.User;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@Slf4j
public class ClientSessionTest {

    @Test
    public void testSessionBind() {
        ClientSession session = new ClientSession(new EmbeddedChannel());
        session.setUser(new User());
        log.info("user: [{}]", session.getUser());

        Channel channel = session.getChannel();
        Attribute<ClientSession> getSession = channel.attr(ClientSession.SESSION_KEY);
        log.info("get-user: [{}]", getSession.get().getUser());
    }

}