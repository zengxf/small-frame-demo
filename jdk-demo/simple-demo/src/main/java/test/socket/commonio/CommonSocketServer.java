package test.socket.commonio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;
/**
 * TCP 测试工具：
 * <pre>
 * TCP/UDP Socket调试工具V2.3
 * USR-TCP232-Test
 * sokit
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-01-12
 */
@Slf4j
public class CommonSocketServer {

    public static final int PORT = 8234;

    public static void main( String[] args ) throws IOException, InterruptedException {
        try ( ServerSocket ss = new ServerSocket( PORT ); ) {
            log.info( "server start! port: {}", PORT );
            while ( true ) {
                Socket s = ss.accept();
                Runnable r = () -> {
                    try {
                        InputStream is = s.getInputStream();
                        while ( true ) {
                            byte[] buf = new byte[1024];
                            int index = is.read( buf );
                            if ( index == -1 ) {
                                Thread.sleep( 1000 );
                            } else {
                                String msg = new String( buf, 0, index );
                                log.info( "client: {}, msg: {}", s, msg );
                            }
                        }
                    } catch ( IOException | InterruptedException e ) {
                        e.printStackTrace();
                    }
                };
                new Thread( r, "socket-client-" + s.getPort() ).start();
            }
        }
    }

}
