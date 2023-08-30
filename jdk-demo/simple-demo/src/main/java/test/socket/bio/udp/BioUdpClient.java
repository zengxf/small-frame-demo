package test.socket.bio.udp;

import static test.socket.bio.udp.BioUdpServer.PORT;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.stream.IntStream;

/**
 * BIO UDP 客户端
 * <p>
 * Created by zengxf on 2018-05-17
 */
public class BioUdpClient {

    // static String IP = "127.0.0.1";
    // static String IP = "192.168.1.161";
    // static String IP = "10.1.1.2";
    static String IP        = "10.1.1.1";

    static String CHARSET   = "UTF-8";
    static int    TEST_SIZE = 1024 * 20;

    public static void main( String[] args ) throws IOException {
        InetAddress address = InetAddress.getByName( IP );

        String msg = getMessage( TEST_SIZE );
        System.out.printf( "len: %s -- msg: %s %n", msg.length(), msg );

        try ( DatagramSocket socket = new DatagramSocket() ) {
            byte[] messageByte = msg.getBytes( CHARSET );
            DatagramPacket packet = new DatagramPacket( messageByte, messageByte.length, address, PORT );
            socket.send( packet );
        }

        System.out.println( "send end ..." );
    }

    static char[] arr = "abcdefg-1234567-中文测试-zxf-峰".toCharArray();

    static String getMessage( int size ) {
        int length = arr.length;
        char[] charArr = new char[size];
        IntStream.range( 0, size )
                .forEach( i -> {
                    int index = i % length;
                    charArr[i] = arr[index];
                } );
        return new String( charArr );
    }

}
