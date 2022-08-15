package test.echo_service.sockets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import test.util.SystemConfig;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import static test.util.SSLContextHelper.createServerSSLContext;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
public class SecureChatServer {

    static class SecureChatServerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel sc) throws Exception {
            ChannelPipeline pipeline = sc.pipeline();
            // 创建服务端 SSL 上下文实例
            SSLContext serverSSLContext = createServerSSLContext();
            // 通过上下文实例，创建服务端的 SSL 引擎
            SSLEngine sslEngine = serverSSLContext.createSSLEngine();
            // 单向认证：在服务端设置不需要验证对端身份，无需客户端证实自己的身份
            sslEngine.setNeedClientAuth(false);
            // 在握手的时候，使用服务端模式
            sslEngine.setUseClientMode(false);
            // 创建 SslHandler 处理器
            ChannelHandler sslHandler = new SslHandler(sslEngine);
            // 将处理器加入到流水线
            pipeline.addLast(sslHandler);
            // 添加分包器
            pipeline.addLast("framer",
                    new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter())
            );
            // 添加字符串解码器
            pipeline.addLast("decoder", new StringDecoder());
            // 添加字符串编码器
            pipeline.addLast("encoder", new StringEncoder());
            // 添加聊天处理器
            pipeline.addLast("handler", new ServerChatHandler());
        }
    }

    /*** 开始安全聊天服务程序 */
    public void start(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new SecureChatServerInitializer());   // 装配子通道流水线
            ChannelFuture cf = b.bind(port).sync();
            cf.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new SecureChatServer().start(
                SystemConfig.SOCKET_SERVER_PORT
        );
    }

}
