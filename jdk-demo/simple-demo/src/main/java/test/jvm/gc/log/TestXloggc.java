package test.jvm.gc.log;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 日志文件的输出路径 <br>
 * <br>
 * 
 * 参数
 * 
 * <pre>
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGC 
 * -XX:+PrintGCDetails 
 * -XX:+PrintGCTimeStamps
 * -XX:+PrintGCDateStamps
 * -XX:+PrintHeapAtGC 
 * -Xloggc:C:\Users\Administrator\Desktop/gc.log
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestXloggc {
    public static void main( String[] args ) {
        int k = 1024;
        while ( true ) {
            int r = 1 + new Random().nextInt( 5 );
            System.out.println( "r: " + r + " mb" );
            System.gc();
            IntStream.rangeClosed( 1, r * k * k ).boxed().collect( Collectors.toList() );
        }
    }
}
