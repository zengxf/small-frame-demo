package im.client.connection;

import im.client.BaseClientAppTest5;
import im.common.codec.SimpleProtobufDecoder;
import im.common.codec.SimpleProtobufEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created by ZXFeng on 2023/2/28.
 */
@Slf4j
public class TestConn extends BaseClientAppTest5 {

    @Value("${im-server.ip}")
    private String host;
    @Value("${im-server.port}")
    private int port;


    /*** 连接 / 重连 */
    public Bootstrap doConnect(int i) {
        Bootstrap bootstrap = null;
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(new NioEventLoopGroup(1));
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.remoteAddress(host, port);

            // 设置通道初始化
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast("decoder", new SimpleProtobufDecoder());
                    ch.pipeline().addLast("encoder", new SimpleProtobufEncoder());
                }
            });
            log.info("[{}] 客户端开始连接...", i);

            ChannelFuture f = bootstrap.connect(); // 异步发起连接
            f.addListener((lf) -> {
                if (lf.isSuccess()) {
                    log.info("[{}] 连接成功!", i);
                } else {
                    log.info("[{}] 连接失败!", i);
                }
            });
        } catch (Exception e) {
            log.error("[{}] 客户端连接失败", i, e);
        }
        return bootstrap;
    }

    @Test
    public void conn5000() {
        int size = 5000;
        List<Bootstrap> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Bootstrap cb = this.doConnect(i);
            if (cb != null)
                list.add(cb);
        }
        log.info("测试完成！size: [{}]", list.size());
    }

}
