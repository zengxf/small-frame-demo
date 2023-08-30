package test.jdkapi.io.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 合并文件
 * <p>
 * Created by zengxf on 2019-05-30
 */
public class MergeFile {

    public static void main( String[] args ) throws Exception {
        final String f1 = "C:\\Users\\Administrator\\Desktop\\aa\\test\\test1.txt";
        final String f2 = "C:\\Users\\Administrator\\Desktop\\aa\\test\\test2.txt";
        String out = "C:\\Users\\Administrator\\Desktop\\aa\\test\\test-out.txt";

        Vector<InputStream> vector = new Vector<>();
        vector.add( new FileInputStream( f1 ) );
        vector.add( new FileInputStream( f2 ) );
        Enumeration<InputStream> list = vector.elements();

        SequenceInputStream sis = new SequenceInputStream( list );
        OutputStream os = new FileOutputStream( out );

        byte[] bf = new byte[1024];
        int flag = -1;
        while ( ( flag = sis.read( bf ) ) != -1 ) {
            os.write( bf, 0, flag );
            os.flush();
            System.out.println( "flag: " + flag );
        }

        os.close();
        sis.close();

        System.out.println( "ok" );
    }

}
