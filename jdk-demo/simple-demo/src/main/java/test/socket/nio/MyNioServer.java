package test.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class MyNioServer {

    static final int PORT = 9900;

    public static void main( String[] args ) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking( false );
        ServerSocket serverSocket = server.socket();
        serverSocket.bind( new InetSocketAddress( PORT ) ); // start
        Selector selector = Selector.open();
        server.register( selector, SelectionKey.OP_ACCEPT );

        // listen
        System.out.println( "listen while!" );
        while ( true ) {
            if ( selector.select( 3000 ) == 0 ) {
                System.out.print( "." );
                continue;
            }
            Iterator<SelectionKey> keyIter = selector.selectedKeys()
                    .iterator();
            while ( keyIter.hasNext() ) {
                SelectionKey key = keyIter.next();
                if ( key.isAcceptable() ) {
                    System.out.println( "isAcceptable!" );
                } else if ( key.isReadable() ) {
                    System.out.println( "isReadable!" );
                } else if ( key.isValid() && key.isWritable() ) {
                    System.out.println( "isWritable!" );
                }
                keyIter.remove();
            }
        }
    }

}
