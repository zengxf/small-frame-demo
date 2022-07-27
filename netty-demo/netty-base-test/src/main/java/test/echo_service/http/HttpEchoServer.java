package test.echo_service.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import test.util.DemoConfig;

/**
 * 测试：@Insomnia//My-OS-Test/Netty-HTTP/echo
 * <br/>
 * Created by ZXFeng on 2022/7/27.
 */
@Slf4j
public class HttpEchoServer {

    static class HttpZeroCopyInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        public void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();
            // 请求解码器和响应编码器, 等价于下面两行
            // pipeline.addLast(new HttpServerCodec());
            // 请求解码器
            pipeline.addLast(new HttpRequestDecoder());
            // 响应编码器
            pipeline.addLast(new HttpResponseEncoder());
            // HttpObjectAggregator 将 HTTP 消息的多个部分合成一条完整的 HTTP 消息
            // HttpObjectAggregator 用于解析 Http 完整请求
            // 把多个消息转换为一个单一的完全 FullHttpRequest 或是 FullHttpResponse，
            // 原因是 HTTP 解码器会在解析每个 HTTP 消息中生成多个消息对象
            //   如 HttpRequest/HttpResponse, HttpContent, LastHttpContent
            pipeline.addLast(new HttpObjectAggregator(65535));
            // 自定义的业务 handler
            pipeline.addLast(new HttpEchoHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        start();
    }

    public static void start() throws InterruptedException {
        // 创建连接监听 reactor 线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 创建连接处理 reactor 线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 服务端启动引导实例
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new HttpZeroCopyInitializer());
            // 监听端口，返回监听通道
            Channel ch = b.bind(DemoConfig.HTTP_SERVER_PORT).sync().channel();
            log.info("HTTP ECHO 服务已经启动 http://{}/", ch.localAddress());
            // 等待服务端监听到端口关闭
            ch.closeFuture().sync();
        } finally {
            // 优雅地关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
