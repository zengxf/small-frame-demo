package im.server.handler;

import im.common.codec.SimpleProtobufDecoder;
import im.common.codec.SimpleProtobufEncoder;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;
import im.server.ServerApplication;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@Slf4j
public class LoginRequestHandlerTest {

    @Autowired
    private LoginRequestHandler loginRequestHandler;


    @Test
    public void testLogin() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new SimpleProtobufDecoder());
                ch.pipeline().addLast("login", loginRequestHandler);
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);

        User user = new User();
        ProtoMsg.Message loginMsg = this.buildLoginMsg(user);
        ByteBuf bytebuf = Unpooled.buffer(1024);
        SimpleProtobufEncoder.encode0(loginMsg, bytebuf);

        channel.writeInbound(bytebuf);
        channel.flush();
    }

    private ProtoMsg.Message buildLoginMsg(User user) {
        ProtoMsg.Message.Builder outer = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.LOGIN_REQUEST)
                .setSessionId(UUID.randomUUID().toString())
                .setSequence(-1);
        ProtoMsg.LoginRequest.Builder lb = ProtoMsg.LoginRequest.newBuilder()
                .setDeviceId(user.getDevId())
                .setPlatform(user.getPlatform().ordinal())
                .setToken(user.getToken())
                .setUid(user.getUid());
        return outer.setLoginRequest(lb).build();
    }

}