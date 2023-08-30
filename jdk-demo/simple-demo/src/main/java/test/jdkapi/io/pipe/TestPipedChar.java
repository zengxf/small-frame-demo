package test.jdkapi.io.pipe;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 测试字符<br>
 * PipedReader.read() 会间隔 1s 不停的循环
 * 
 * <p>
 * Created by zengxf on 2018-02-23
 */
public class TestPipedChar {

    public static void main( String[] args ) throws IOException {
        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter( in );

        Runnable rd = () -> {
            int receive = 0;
            try {
                while ( ( receive = in.read() ) != -1 ) {
                    System.out.print( (char) receive );
                }
            } catch ( IOException ex ) {
            }
        };

        Thread printThread = new Thread( rd, "PrintThread" );
        printThread.start();

        int receive = 0;
        try {
            while ( ( receive = System.in.read() ) != -1 ) { // in.read() 会等待用户输入
                out.write( receive );
            }
        } finally {
            out.close();
        }

    }

}
