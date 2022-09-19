package im.client.client;

import im.client.handler.ExceptionHandler;
import im.client.handler.LoginResponseHandler;
import im.common.codec.SimpleProtobufDecoder;
import im.common.codec.SimpleProtobufEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/19.
 */
@Service
@Slf4j
public class ChatNettyClient {

    @Value("${im-server.ip}")
    private String host;
    @Value("${im-server.port}")
    private int port;

    @Autowired
    private LoginResponseHandler loginResponseHandler;
    @Autowired
    private ExceptionHandler exceptionHandler;
    @Setter
    private GenericFutureListener<ChannelFuture> connectedListener;

    private Bootstrap bootstrap;
    private EventLoopGroup loopGroup;

    /*** 连接 / 重连 */
    public void doConnect() {
        try {
            bootstrap = new Bootstrap();
            loopGroup = new NioEventLoopGroup(1);
            bootstrap.group(loopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.remoteAddress(host, port);

            // 设置通道初始化
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("decoder", new SimpleProtobufDecoder());
                    ch.pipeline().addLast("encoder", new SimpleProtobufEncoder());
                    ch.pipeline().addLast(loginResponseHandler);
                    ch.pipeline().addLast(exceptionHandler);
                }
            });
            log.info("客户端开始连接...");

            ChannelFuture f = bootstrap.connect(); // 异步发起连接
            f.addListener(connectedListener);
        } catch (Exception e) {
            log.error("客户端连接失败", e);
        }
    }

    public void close() {
        if (loopGroup != null)
            loopGroup.shutdownGracefully();
    }

}