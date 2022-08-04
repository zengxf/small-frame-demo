package test.echo_service.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import test.util.DemoConfig;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class NettyEchoClient {

    private int serverPort;
    private String serverIp;
    Bootstrap b = new Bootstrap();

    public NettyEchoClient(String ip, int port) {
        this.serverPort = port;
        this.serverIp = ip;
    }

    public void runClient() {
        // 创建 reactor 线程组
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            // 1 设置 reactor 线程组
            b.group(workerLoopGroup);
            // 2 设置 nio 类型的 channel
            b.channel(NioSocketChannel.class);
            // 3 设置监听端口
            b.remoteAddress(serverIp, serverPort);
            // 4 设置通道的参数
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            // 5 装配通道流水线
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                    ch.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            ChannelFuture f = null;
            boolean connected = false;
            while (!connected) {
                f = b.connect();
                f.addListener((ChannelFuture futureListener) -> {
                    if (futureListener.isSuccess()) {
                        log.info("EchoClient 客户端连接成功!");
                    } else {
                        log.info("EchoClient 客户端连接失败!");
                    }
                });
                // 阻塞,直到连接完成
                // f.sync();
                f.awaitUninterruptibly();
                if (f.isCancelled()) {
                    log.info("用户取消连接!");
                    return;
                } else if (f.isSuccess()) {
                    connected = true;
                }
            }
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            log.info("请输入发送内容: ");
            GenericFutureListener sendCallBack = (future) -> {
                if (future.isSuccess()) {
                    log.info("发送成功!");
                } else {
                    log.info("发送失败!");
                }
            };
            while (scanner.hasNext()) {
                String next = scanner.next();
                if ("exit".equals(next))
                    break;
                byte[] bytes = (LocalDate.now() + " >> " + next).getBytes("UTF-8");
                // 发送 ByteBuf
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                ChannelFuture writeAndFlushFuture = channel.writeAndFlush(buffer);
                writeAndFlushFuture.addListener(sendCallBack);
                log.info("请输入发送内容: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭 EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = DemoConfig.SOCKET_SERVER_PORT;
        String ip = DemoConfig.SOCKET_SERVER_IP;
        new NettyEchoClient(ip, port).runClient();
    }

}