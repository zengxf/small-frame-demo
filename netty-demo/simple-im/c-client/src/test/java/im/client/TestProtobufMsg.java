package im.client;

import im.common.codec.SimpleProtobufDecoder;
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

import java.io.IOException;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@Slf4j
public class TestProtobufMsg {

    static class MockLoginRequestHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ProtoMsg.Message inMsg = (ProtoMsg.Message) msg;
            ProtoMsg.LoginRequest pkg = inMsg.getLoginRequest();
            log.info("id:= " + pkg.getUid());
            log.info("token:= " + pkg.getToken());
        }
    }


    /*** 解码器的使用实例 */
    @Test
    public void testProtobufDecoder() throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast("decoder", new SimpleProtobufDecoder());
                ch.pipeline().addLast("inHandler", new MockLoginRequestHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int j = 0; j < 100; j++) {
            ByteBuf bytebuf = Unpooled.buffer(1024);
            ProtoMsg.Message pkg = buildLoginMsg(new User());
            SimpleProtobufEncoder.encode0(pkg, bytebuf);
            channel.writeInbound(bytebuf);
            channel.flush();
        }

        Thread.sleep(5_000L);
    }

    /*** 构建消息 整体 的 Builder */
    private static ProtoMsg.Message.Builder outerBuilder(long seqId) {
        ProtoMsg.Message.Builder mb = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.LOGIN_REQUEST)
                .setSessionId("-1")
                .setSequence(seqId);
        return mb;
    }

    private static ProtoMsg.Message buildLoginMsg(User user) {
        ProtoMsg.Message.Builder outBuilder = outerBuilder(-1);
        ProtoMsg.LoginRequest.Builder lb = ProtoMsg.LoginRequest.newBuilder()
                .setDeviceId(user.getDevId())
                .setPlatform(user.getPlatform().ordinal())
                .setToken(user.getToken())
                .setUid(user.getUid());
        return outBuilder.setLoginRequest(lb).build();
    }


    // 序列化 Serialization & 反序列化 Deserialization
    @Test
    public void serAndDe() throws IOException {
        ProtoMsg.Message msg = buildLoginMsg(new User());
        ProtoMsg.LoginRequest outPkg = msg.getLoginRequest();
        log.info("out-id:= " + outPkg.getUid());
        log.info("out-token:= " + outPkg.getToken());

        // 将 Protobuf 对象，序列化成二进制字节数组
        byte[] data = msg.toByteArray();

        // 二进制字节数组，反序列化成 Protobuf 对象
        ProtoMsg.Message inMsg = ProtoMsg.Message.parseFrom(data);
        ProtoMsg.LoginRequest inPkg = inMsg.getLoginRequest();
        log.info("in-id:= " + inPkg.getUid());
        log.info("in-token:= " + inPkg.getToken());
    }

}
