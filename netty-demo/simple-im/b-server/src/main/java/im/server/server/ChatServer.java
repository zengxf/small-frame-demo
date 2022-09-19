package im.server.server;

import im.common.codec.SimpleProtobufDecoder;
import im.common.codec.SimpleProtobufEncoder;
import im.server.handler.LoginRequestHandler;
import im.server.handler.ServerExceptionHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
@Service
@Slf4j
public class ChatServer {

    @Value("${im-server.port}")
    private int port;
    @Autowired
    private LoginRequestHandler loginRequestHandler;
    @Autowired
    private ServerExceptionHandler serverExceptionHandler;

    public void run() {
        // 启动引导器
        ServerBootstrap b = new ServerBootstrap();
        // 连接监听线程组
        EventLoopGroup bg = new NioEventLoopGroup(1);
        // 传输处理线程组
        EventLoopGroup wg = new NioEventLoopGroup();

        try {
            // 1 设置reactor 线程
            b.group(bg, wg);
            // 2 设置nio类型的channel
            b.channel(NioServerSocketChannel.class);
            // 3 设置监听端口
            b.localAddress(new InetSocketAddress(port));
            // 4 设置通道选项
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 5 装配流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                // 有连接到达时会创建一个channel
                protected void initChannel(SocketChannel ch) {
                    // 管理pipeline中的Handler
                    ch.pipeline().addLast(new SimpleProtobufDecoder());
                    ch.pipeline().addLast(new SimpleProtobufEncoder());
                    // 在流水线中添加handler来处理登录, 登录后删除
                    ch.pipeline().addLast("login", loginRequestHandler);
                    ch.pipeline().addLast(serverExceptionHandler);
                }
            });
            // 6 开始绑定server
            // 通过调用sync同步方法阻塞直到绑定成功
            ChannelFuture channelFuture = b.bind().sync();
            log.info("服务启动, 端口 " + channelFuture.channel().localAddress());
            // 7 监听通道关闭事件
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8 优雅关闭 EventLoopGroup，
            wg.shutdownGracefully();
            bg.shutdownGracefully();
        }
    }

}
