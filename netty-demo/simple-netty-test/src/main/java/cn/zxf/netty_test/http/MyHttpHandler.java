package cn.zxf.netty_test.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class MyHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // 1

    @Override
    protected void channelRead0( ChannelHandlerContext ctx, FullHttpRequest msg ) throws Exception {
        System.out.println( "class:" + msg.getClass()
                .getName() );
        DefaultFullHttpResponse response = new DefaultFullHttpResponse( HttpVersion.HTTP_1_1, HttpResponseStatus.OK, //
                Unpooled.wrappedBuffer( "test".getBytes() ) ); // 2

        HttpHeaders heads = response.headers();
        heads.add( HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN + "; charset=UTF-8" );
        heads.add( HttpHeaderNames.CONTENT_LENGTH, response.content()
                .readableBytes() ); // 3
        heads.add( HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE );

        ctx.write( response );
    }

    @Override
    public void channelReadComplete( ChannelHandlerContext ctx ) throws Exception {
        System.out.println( "channelReadComplete" );
        super.channelReadComplete( ctx );
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        System.out.println( "exceptionCaught" );
        if ( null != cause )
            cause.printStackTrace();
        if ( null != ctx )
            ctx.close();
    }

}