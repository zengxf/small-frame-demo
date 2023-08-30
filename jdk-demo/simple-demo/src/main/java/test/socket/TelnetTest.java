package test.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TelnetTest {

    static final String password   = "a";
    static final int    telnetPort = 8800;

    public static void main( String[] args ) throws IOException {
        ServerSocket server = new ServerSocket( telnetPort );

        connection: //
        while ( true ) {
            Socket sc = server.accept();
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();

            String cmd = "";
            boolean firstSign = true;
            boolean exitSign = false;

            session: //
            while ( true ) {
                int ascii = is.read();
                char single = (char) ascii;

                if ( single == '\r' ) {
                } else if ( single == '\n' ) {
                    if ( exitSign ) {
                        if ( password.equals( cmd ) ) {
                            os.write( "\r\nShutdown OK !!!".getBytes() );
                            System.out.printf( "远程IP [%s] 主机已设置为关闭！%n", sc.getInetAddress().getHostAddress() );
                            break connection;
                        } else {
                            os.write( "密码不对！password: ".getBytes() );
                        }
                    }
                    if ( !exitSign ) {
                        switch ( cmd ) {
                            case "exit":
                                os.write( "Bye".getBytes() );
                                is.close();
                                os.close();
                                sc.close();
                                break session;
                            case "shutdown":
                                exitSign = true;
                                os.write( "password：".getBytes() );
                                break;
                            default:
                                os.write( "没有此命令\r\n".getBytes() );
                                break;
                        }
                    }
                    cmd = "";
                } else {
                    cmd += single;
                }

                if ( firstSign ) {
                    firstSign = false;
                    os.write( single );
                }
            }
        }

        server.close();
    }

}
