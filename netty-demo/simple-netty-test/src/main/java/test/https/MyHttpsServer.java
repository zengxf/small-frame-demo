package test.https;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyHttpsServer {

    static int port = 8088;

    public static void main( String[] args ) throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group( group )
                .channel( NioServerSocketChannel.class )
                .childHandler( new SSLChannelInitializer() )
                .option( ChannelOption.SO_BACKLOG, 128 ) // determining the number of connections queued
                .childOption( ChannelOption.SO_KEEPALIVE, Boolean.TRUE );

        b.bind( port )
                .sync();
    }

}
