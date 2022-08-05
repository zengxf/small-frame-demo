package test.echo_service.websocket;

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
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import test.util.DemoConfig;

@Slf4j
public final class WebSocketEchoServer {

    // 流水线装配器
    static class EchoInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        public void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();
            // 请求解码器
            pipeline.addLast(new HttpRequestDecoder());
            // 响应编码器
            pipeline.addLast(new HttpResponseEncoder());
            // HttpObjectAggregator 将 HTTP 消息的多个部分合成一条完整的 HTTP 消息
            // HttpObjectAggregator 用于解析 HTTP 完整请求
            // 把多个消息转换为一个单一的完全 FullHttpRequest 或是 FullHttpResponse，
            pipeline.addLast(new HttpObjectAggregator(65535));
            // WebSocket 报文处理，包括数据压缩与解压
            pipeline.addLast(new WebSocketServerProtocolHandler(
                    "/ws", "echo", true, 10 * 1024
            ));
            // 增加网页的处理逻辑
            pipeline.addLast(new WebPageHandler());
            // TextWebSocketFrameHandler 是自定义逻辑处理器，
            pipeline.addLast(new TextWebSocketFrameHandler());
        }
    }


    /*** 启动 */
    public static void start(String ip) throws Exception {
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
                    .childHandler(new EchoInitializer());
            // 监听端口，返回同步通道
            Channel ch = b.bind(DemoConfig.SOCKET_SERVER_PORT).sync().channel();
            log.info("WebSocket 服务已经启动 http://{}:{}/", ip, DemoConfig.SOCKET_SERVER_PORT);
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        /**
         * 使用 WLAN ip
         * C:/xx> ipconfig
         * 无线局域网适配器 WLAN:
         *    连接特定的 DNS 后缀 . . . . . . . : xx.com
         *    本地链接 IPv6 地址. . . . . . . . : fe80::cc5b:70d9:4740:208d%7
         *    IPv4 地址 . . . . . . . . . . . . : 10.10.48.186
         *    子网掩码  . . . . . . . . . . . . : 255.255.255.0
         *    默认网关. . . . . . . . . . . . . : 10.10.48.1
         * -----------------------------
         * 添加路由回环（mask 要使用 4 个 255）
         *     route add 10.10.48.186 mask 255.255.255.255 10.10.48.1
         * WireShark 抓包过滤命令
         *     (ip.src == 10.10.48.186 and ip.dst == 10.10.48.186) and (http or websocket)
         * 删除路由回环
         *     route delete 10.10.48.186 mask 255.255.255.255 10.10.48.1
         */
        WebSocketEchoServer.start("10.10.48.186");
    }

}
