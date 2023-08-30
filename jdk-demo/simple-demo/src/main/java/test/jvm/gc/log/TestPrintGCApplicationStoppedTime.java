package test.jvm.gc.log;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 打印 GC 暂停时间 <br>
 * <br>
 * 
 * 参数
 * 
 * <pre>
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGCApplicationStoppedTime
 * </pre>
 * 
 * 输出示例
 * 
 * <pre>
 * Total time for which application threads were stopped: 0.0318549 seconds, Stopping threads took: 0.0000291 seconds
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestPrintGCApplicationStoppedTime {
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
