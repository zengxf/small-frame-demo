package im.client.sender;

import im.client.Client1Application;
import im.client.handler.ExceptionHandler;
import im.client.handler.LoginResponseHandler;
import im.client.session.ClientSession;
import im.common.codec.SimpleProtobufDecoder;
import im.common.codec.SimpleProtobufEncoder;
import im.common.dto.User;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Client1Application.class)
@Slf4j
public class LoginSenderTest {

    @Value("${im-server.ip}")
    private String host;
    @Value("${im-server.port}")
    private int port;

    @Autowired
    private LoginSender loginSender;
    @Autowired
    private LoginResponseHandler loginResponseHandler;
    @Autowired
    private ExceptionHandler exceptionHandler;

    private Channel channel;
    private Bootstrap bootstrap = new Bootstrap();

    private void initBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.remoteAddress(host, port);
    }

    // -----------
    // -----------
    // -----------

    GenericFutureListener<ChannelFuture> connectedListener1 = (ChannelFuture future) -> {
        if (!future.isSuccess()) {
            log.info("连接失败!");
        } else {
            log.info("测试用例：连接成功!");
            channel = future.channel();
            // 创建会话
            ClientSession session = new ClientSession(channel);
            session.setConnected(true);
        }
    };

    @Test
    public void testConnectSever() throws InterruptedException {
        initBootstrap();

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast("decoder", new SimpleProtobufDecoder());
                ch.pipeline().addLast("encoder", new SimpleProtobufEncoder());
            }
        });
        log.info("测试用例：客户端开始连接");
        // 异步发起连接
        ChannelFuture f = bootstrap.connect();
        f.addListener(connectedListener1);

        f.sync();
        f.channel().closeFuture().sync();

        log.info("测试用例执行完成");
        f.channel().close();
    }

    // ---------------
    // ---------------
    // ---------------

    GenericFutureListener<ChannelFuture> connectedListener2 = (ChannelFuture future) -> {
        if (!future.isSuccess()) {
            log.info("连接失败!");
        } else {
            log.info("测试用例：连接成功!");
            channel = future.channel();
            // 创建会话
            ClientSession session = new ClientSession(channel);
            session.setConnected(true);
            startLogin(session);
        }
    };

    private void startLogin(ClientSession session) {
        User user = new User();
        user.setUid("1");
        user.setToken(UUID.randomUUID().toString());
        user.setDevId(UUID.randomUUID().toString());
        session.setUser(user);

        loginSender.setUser(user);
        loginSender.setSession(session);
        loginSender.sendLoginMsg();
    }

    @Test
    public void testLoginSender() throws InterruptedException {
        initBootstrap();

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast("decoder", new SimpleProtobufDecoder());
                ch.pipeline().addLast("encoder", new SimpleProtobufEncoder());
            }
        });
        log.info("测试用例：客户端开始连接");

        ChannelFuture f = bootstrap.connect();//异步发起连接
        f.addListener(connectedListener2);

        f.channel().closeFuture().sync();
        log.info("测试用例执行完成");

        f.channel().close();
    }

    @Test
    public void testLoginResponseHandler() throws InterruptedException {
        initBootstrap();

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast("encoder", new SimpleProtobufEncoder());
                ch.pipeline().addLast("decoder", new SimpleProtobufDecoder());
                ch.pipeline().addLast(loginResponseHandler);
                ch.pipeline().addLast(exceptionHandler);
            }
        });
        log.info("测试用例：客户端开始连接");

        ChannelFuture f = bootstrap.connect(); // 异步发起连接
        f.addListener(connectedListener2);

        f.channel().closeFuture().sync();
        log.info("测试用例执行完成");

        f.channel().close();
    }

}
