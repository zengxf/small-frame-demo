package test.protocol;

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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import test.util.NettyDemoConfig;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/20.
 */
@Slf4j
public class JsonServer {

    private final int serverPort;
    ServerBootstrap b = new ServerBootstrap();

    public JsonServer(int port) {
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
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                            1024, 0, 4, 0, 4));
                    ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    ch.pipeline().addLast(new JsonMsgDecoder());
                }
            });
            // 6 开始绑定 server
            // 通过调用 sync 同步方法阻塞直到绑定成功
            ChannelFuture channelFuture = b.bind();
            channelFuture.addListener((future) -> {
                if (future.isSuccess()) {
                    log.info(" ========》服务器启动成功，监听端口 :: " + channelFuture.channel().localAddress());
                }
            });
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
        int port = NettyDemoConfig.SOCKET_SERVER_PORT;
        new JsonServer(port).runServer();
    }

}
