package test.socket.bio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * BIO UDP 服务端
 * <p>
 * Created by zengxf on 2018-05-17
 */
// java cn.simple.test.socket.bio.udp.BioUdpServer
public class BioUdpServer {

    static final int PORT       = 9911;
    static final int BUF_LENGTH = 1024_0;

    public static void main( String[] args ) throws IOException {
        try ( DatagramSocket ds = new DatagramSocket( PORT ) ) {
            System.out.println( "BIO UDP " + PORT + " Server start ..." );

            byte[] buf = new byte[BUF_LENGTH];
            DatagramPacket data = new DatagramPacket( buf, BUF_LENGTH );

            while ( true ) {
                ds.receive( data );
                String ip = data.getAddress()
                        .getHostAddress();
                String msg = new String( buf );
                System.out.printf( "Client IP: %s -- Len: %s -- MSG: %s%n", ip, msg.length(), msg );
            }

        }
    }

}
