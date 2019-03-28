package cn.zxf.netty_test.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class MyHttpServer {

    static int port = 8088;

    public static void main( String[] args ) throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group( group )
                .channel( NioServerSocketChannel.class )
                .childHandler( new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel( SocketChannel ch ) throws Exception {
                        System.out.println( "initChannel ch:" + ch );
                        ch.pipeline()
                                .addLast( "decoder", new HttpRequestDecoder() ) // 1
                                .addLast( "encoder", new HttpResponseEncoder() ) // 2
                                .addLast( "aggregator", new HttpObjectAggregator( 512 * 1024 ) ) // 3
                                .addLast( "my-handler", new MyHttpHandler() ) //
                        ; // 4
                    }
                } )
                .option( ChannelOption.SO_BACKLOG, 128 ) // determining the number of connections queued
                .childOption( ChannelOption.SO_KEEPALIVE, Boolean.TRUE );

        b.bind( port )
                .sync();
    }

}
