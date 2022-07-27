package test.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;
import test.util.DemoConfig;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class ProtoBufSendClient {

    static String content = " 中测试：11111 ";
    private int serverPort;
    private String serverIp;
    Bootstrap b = new Bootstrap();

    public ProtoBufSendClient(String ip, int port) {
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
                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                    ch.pipeline().addLast(new ProtobufEncoder());
                }
            });
            ChannelFuture f = b.connect();
            f.addListener((ChannelFuture futureListener) -> {
                if (futureListener.isSuccess()) {
                    log.info("EchoClient 客户端连接成功!");
                } else {
                    log.info("EchoClient 客户端连接失败!");
                }
            });
            // 阻塞, 直到连接完成
            f.sync();
            Channel channel = f.channel();
            // 发送 Protobuf 对象
            for (int i = 0; i < 3; i++) {
                MsgProto.Msg user = build(i, i + " -> " + content);
                channel.writeAndFlush(user);
                log.info("发送报文数：" + user);
            }
            channel.flush();
            // 7 等待通道关闭的异步任务结束
            // 服务监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭 EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            workerLoopGroup.shutdownGracefully();
        }
    }

    public MsgProto.Msg build(int id, String content) {
        MsgProto.Msg.Builder builder = MsgProto.Msg.newBuilder();
        builder.setId(id);
        builder.setContent(content);
        return builder.build();
    }

    public static void main(String[] args) {
        int port = DemoConfig.SOCKET_SERVER_PORT;
        String ip = DemoConfig.SOCKET_SERVER_IP;
        new ProtoBufSendClient(ip, port).runClient();
    }

}