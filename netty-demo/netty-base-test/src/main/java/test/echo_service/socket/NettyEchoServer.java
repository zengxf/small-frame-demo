package test.echo_service.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import test.util.DemoConfig;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class NettyEchoServer {

    private final int serverPort;
    ServerBootstrap b = new ServerBootstrap();

    public NettyEchoServer(int port) {
        this.serverPort = port;
    }

    public void runServer() {
        // 创建 reactor 线程组
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            // 1 设置 reactor 线程组
            b.group(bossLoopGroup, workerLoopGroup);
            // 2 设置 nio 类型的 channel
            b.channel(NioServerSocketChannel.class);
            // 3 设置监听端口
            b.localAddress(serverPort);
            // 4 设置通道的参数
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
            b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 5 装配子通道流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                // 有连接到达时会创建一个 channel
                protected void initChannel(SocketChannel ch) {
                    // ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(NettyEchoServerHandler.INSTANCE);
                }
            });
            // 6 开始绑定 server
            // 通过调用 sync 同步方法阻塞直到绑定成功
            ChannelFuture channelFuture = b.bind();
            channelFuture.addListener((future) -> {
                if (future.isSuccess()) {
                    log.info(" ========》反应器线程 回调 服务器启动成功，监听端口: " + channelFuture.channel().localAddress());
                }
            });
            log.info(" 调用线程执行的，服务器启动成功，监听端口: " + channelFuture.channel().localAddress());

            // 7 等待通道关闭的异步任务结束
            // 服务监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8 优雅关闭 EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = DemoConfig.SOCKET_SERVER_PORT;
        new NettyEchoServer(port).runServer();
    }

}
