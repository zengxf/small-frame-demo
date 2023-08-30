package test.socket.commonio;

import static test.socket.commonio.CommonSocketServer.PORT;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;

/**
 * 长连接
 * 
 * <p>
 * Created by zengxf on 2018-01-12
 */
@Slf4j
public class CommonSocketPersistentClient {
    public static void main( String[] args ) throws UnknownHostException, IOException, InterruptedException {
        try ( Socket s = new Socket( "localhost", PORT ); ) {
            OutputStream os = s.getOutputStream();
            String msg = "test";
            log.info( "send msg [{}] to server", msg );
            os.write( msg.getBytes() );

            InputStream is = s.getInputStream();
            while ( true ) {
                byte[] buf = new byte[1024];
                int index = is.read( buf );
                if ( index == -1 ) {
                    Thread.sleep( 1000 );
                } else {
                    msg = new String( buf, 0, index );
                    log.info( "receive server msg: {}", msg );
                }
            }
        }
    }
}
