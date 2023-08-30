package test.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class MyNioClient {

    public static void main( String[] args ) throws IOException {
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking( false );
        clntChan.connect( new InetSocketAddress( "localhost", MyNioServer.PORT ) );
    }

}
