package cn.zxf.netty_test.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
        System.out.println( "client接收到服务器返回的消息: " + msg );
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        System.out.println( "client 异常：" + cause );
    }
}