package im.client.handler;

import im.common.codec.SimpleProtobufEncoder;
import im.common.dto.User;
import im.common.dto.msg.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@Slf4j
public class ExceptionHandlerTest {

    public static class MockExceptInHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("读取出错 ------------------");
            throw new Exception("读取出错！");
        }
    }

    @Test
    public void testInboundExceptOutHandler() throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast("inHandler", new MockExceptInHandler());
                ch.pipeline().addLast("exception", new ExceptionHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        ByteBuf bytebuf = Unpooled.buffer(1024);
        ProtoMsg.Message pkg = buildLoginMsg(new User());
        SimpleProtobufEncoder.encode0(pkg, bytebuf);

        channel.writeInbound(bytebuf);
        channel.flush();

        Thread.sleep(5_000L);
    }


    private static ProtoMsg.Message buildLoginMsg(User user) {
        ProtoMsg.Message.Builder outBuilder = outerBuilder(-1);
        ProtoMsg.LoginRequest.Builder loginBuilder = ProtoMsg.LoginRequest.newBuilder()
                .setDeviceId(user.getDevId())
                .setPlatform(user.getPlatform().ordinal())
                .setToken(user.getToken())
                .setUid(user.getUid());
        return outBuilder.setLoginRequest(loginBuilder).build();
    }

    private static ProtoMsg.Message.Builder outerBuilder(long seqId) {
        ProtoMsg.Message.Builder builder = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.LOGIN_REQUEST)
                .setSessionId("-1")
                .setSequence(seqId);
        return builder;
    }

}