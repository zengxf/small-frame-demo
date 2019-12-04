package test.long_connection;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongConnTest {

    private Logger logger = LoggerFactory.getLogger( LongConnTest.class );
    String         host   = "localhost";
    int            port   = LiveServer.PORT;

    // 输入 1 发心跳包，输入 2 发 content，5 秒内不输入 1 则服务端会自动断连
    public void testLongConn() throws Exception {
        logger.debug( "start" );
        final Socket socket = new Socket();
        socket.connect( new InetSocketAddress( host, port ) );
        Scanner scanner = new Scanner( System.in );
        int code;
        while ( true ) {
            code = scanner.nextInt();
            logger.debug( "input code:" + code );
            if ( code == 0 ) {
                break;
            } else if ( code == 1 ) {
                ByteBuffer byteBuffer = ByteBuffer.allocate( 5 );
                byteBuffer.put( (byte) 1 );
                byteBuffer.putInt( 0 );
                socket.getOutputStream()
                        .write( byteBuffer.array() );
                logger.debug( "write heart finish!" );
            } else if ( code == 2 ) {
                byte[] content = ( "hello, I'm " + hashCode() ).getBytes();
                ByteBuffer byteBuffer = ByteBuffer.allocate( content.length + 5 );
                byteBuffer.put( (byte) 2 );
                byteBuffer.putInt( content.length );
                byteBuffer.put( content );
                socket.getOutputStream()
                        .write( byteBuffer.array() );
                logger.debug( "write content finish!" );
            }
        }
        socket.close();
        scanner.close();
    }

    public static void main( String[] args ) throws Exception {
        new LongConnTest().testLongConn();
    }

}
