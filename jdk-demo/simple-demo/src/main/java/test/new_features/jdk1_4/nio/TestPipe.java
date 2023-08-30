package test.new_features.jdk1_4.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * <pre>
 * 两个线程间单向传输数据的连接
 * 把数据写到sink channel中，这些数据可以同过source channel再读取出来
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-08-14
 */
public class TestPipe {
    public static void main( String[] args ) throws IOException {
        Pipe pipe = Pipe.open();

        {
            Pipe.SinkChannel sinkChannel = pipe.sink();
            String newData = "New String to write to file..." + System.currentTimeMillis();
            ByteBuffer buf = ByteBuffer.allocate( 48 );
            buf.clear();
            buf.put( newData.getBytes() );
            buf.flip();
            while ( buf.hasRemaining() ) {
                sinkChannel.write( buf );
            }
        }

        {
            Pipe.SourceChannel sourceChannel = pipe.source();
            ByteBuffer buf = ByteBuffer.allocate( 48 );
            int bytesRead = sourceChannel.read( buf );
            System.out.println( bytesRead );
            System.out.println( new String(buf.array()) );
        }
    }
}
