package test.echo_service.https;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import test.util.SystemConfig;

@Slf4j
public class NettyHttpsServer {

    public static void start() throws Exception {
        // 创建连接监听 reactor 线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 创建连接处理 reactor 线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 服务端启动引导实例
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new HttpsServerInitializer());
            // 监听端口，返回监听通道
            Channel ch = b.bind(SystemConfig.SOCKET_SERVER_PORT).sync().channel();
            log.info("HTTPS ECHO 服务已经启动 https://{}:{}/",
                    SystemConfig.SOCKET_SERVER_IP, SystemConfig.SOCKET_SERVER_PORT
            );
            // 等待服务端监听到端口关闭
            ch.closeFuture().sync();
        } finally {
            // 优雅地关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        start();
    }

}