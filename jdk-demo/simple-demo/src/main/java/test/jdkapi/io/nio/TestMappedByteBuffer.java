package test.jdkapi.io.nio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * <a href="https://www.jianshu.com/p/f90866dcbffc">原文参考</a><br>
 * 效率比 read 和 write 系统调用高
 * <p>
 * Created by zengxf on 2019-01-08
 */
public class TestMappedByteBuffer {

    @SuppressWarnings( "resource" )
    public static void main( String[] args ) {
        File file = new File( "D:\\test\\read\\aa.txt" );
        long len = file.length();
        byte[] ds = new byte[(int) len];

        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile( file, "r" ).getChannel()
                    .map( FileChannel.MapMode.READ_ONLY, 0, len );
            for ( int offset = 0; offset < len; offset++ ) {
                byte b = mappedByteBuffer.get();
                ds[offset] = b;
            }

            Scanner scan = new Scanner( new ByteArrayInputStream( ds ) ).useDelimiter( " " );
            while ( scan.hasNext() ) {
                System.out.print( scan.next() + " " );
            }
        } catch ( IOException e ) {
        }
    }

}
