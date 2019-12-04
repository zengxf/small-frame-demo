package test.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
        System.out.println( "server receive message :" + msg );
        ctx.channel()
                .writeAndFlush( "yes server already accept your message" + msg );
        ctx.close(); // 主动关闭客户端的连接
    }

    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channelActive >>>>>>>>" );
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        System.out.println( "服务端异常：" + cause );
    }

}