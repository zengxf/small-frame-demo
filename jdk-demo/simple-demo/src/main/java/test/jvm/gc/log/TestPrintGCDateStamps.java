package test.jvm.gc.log;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 输出GC的时间戳（以日期的形式，如 2017-09-04T21:53:59.234+0800） <br>
 * <br>
 * 
 * 例1
 * 
 * <pre>
 * 参数
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGCDetails -XX:+PrintGCDateStamps
 * 
 * 输出示例
 * 2017-09-26T12:13:15.180+0800: [GC (System.gc()) [PSYoungGen: 2928K->384K(5632K)] 92322K->92369K(202240K), 0.0227100 secs] [Times: user=0.22 sys=0.00, real=0.02 secs] 
 * 2017-09-26T12:13:15.203+0800: [Full GC (System.gc()) [PSYoungGen: 384K->0K(5632K)] [ParOldGen: 91985K->803K(196608K)] 92369K->803K(202240K), [Metaspace: 4194K->4194K(1056768K)], 0.0058702 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
 * 2017-09-26T12:13:15.211+0800: [GC (Allocation Failure) [PSYoungGen: 2947K->2240K(5632K)] 3750K->3043K(202240K), 0.0023178 secs] [Times: user=0.11 sys=0.00, real=0.00 secs]
 * </pre>
 * 
 * 例2
 * 
 * <pre>
 * 参数
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGC -XX:+PrintGCDateStamps
 * 
 * 输出示例
 * 2017-09-26T12:14:14.266+0800: [GC (System.gc())  132987K->133014K(202240K), 0.0266006 secs]
 * 2017-09-26T12:14:14.292+0800: [Full GC (System.gc())  133014K->803K(202240K), 0.0062148 secs]
 * 2017-09-26T12:14:14.300+0800: [GC (Allocation Failure)  3750K->3043K(202240K), 0.0022690 secs]
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestPrintGCDateStamps {
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
