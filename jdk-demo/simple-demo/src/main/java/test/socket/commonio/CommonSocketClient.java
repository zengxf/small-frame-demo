package test.socket.commonio;

import static test.socket.commonio.CommonSocketServer.PORT;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonSocketClient {
    public static void main( String[] args ) throws UnknownHostException, IOException {
        try ( Socket socket = new Socket( "localhost", PORT ); ) {
            OutputStream os = socket.getOutputStream();
            String msg = "test";
            log.info( "send msg [{}] to server", msg );
            os.write( msg.getBytes() );
        }
    }
}
