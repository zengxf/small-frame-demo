package test.jvm.gc.log;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 输出GC日志。和 -XX:+PringGC 一样 <br>
 * <br>
 * 
 * 参数
 * 
 * <pre>
 * -Xms200m -Xmx200m -Xmn8m
 * -verbose:gc
 * 或 -verbosegc 
 * </pre>
 * 
 * 输出示例
 * 
 * <pre>
 * [GC (System.gc())  25344K->25572K(202240K), 0.0218945 secs]
 * [Full GC (System.gc())  25572K->803K(202240K), 0.0053540 secs]
 * [GC (Allocation Failure)  3748K->3011K(202240K), 0.0022981 secs]
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestVerboseGC {
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
