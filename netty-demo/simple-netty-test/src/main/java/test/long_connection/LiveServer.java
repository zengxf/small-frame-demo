package test.long_connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class LiveServer {

    static final int      PORT   = 8088;
    private static Logger logger = LoggerFactory.getLogger( LiveServer.class );

    public static void main( String[] args ) throws Exception {
        logger.debug( "start server with port:" + PORT );
        new LiveServer().start();
    }

    public void start() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group( group )
                .channel( NioServerSocketChannel.class )
                .childHandler( new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel( SocketChannel ch ) throws Exception {
                        logger.debug( "initChannel ch:" + ch );
                        ch.pipeline()
                                .addLast( "decoder", new LiveDecoder() ) // 1
                                .addLast( "encoder", new LiveEncoder() ) // 2
                                .addLast( "handler", new LiveHandler() ); // 4
                    }
                } )
                .option( ChannelOption.SO_BACKLOG, 128 ) // determining the number of connections queued
                .childOption( ChannelOption.SO_KEEPALIVE, Boolean.TRUE );

        b.bind( PORT )
                .sync();
    }
}
