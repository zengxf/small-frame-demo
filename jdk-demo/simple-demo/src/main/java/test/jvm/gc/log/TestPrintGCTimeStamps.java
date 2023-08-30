package test.jvm.gc.log;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 输出GC的时间戳（以基准时间的形式） <br>
 * <br>
 * 
 * 例1
 * 
 * <pre>
 * 参数
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * 
 * 输出示例
 * 2.096: [GC (System.gc()) [PSYoungGen: 3972K->1536K(5632K)] 25344K->25500K(202240K), 0.0212804 secs] [Times: user=0.24 sys=0.00, real=0.02 secs] 
 * 2.117: [Full GC (System.gc()) [PSYoungGen: 1536K->0K(5632K)] [ParOldGen: 23964K->803K(196608K)] 25500K->803K(202240K), [Metaspace: 4201K->4201K(1056768K)], 0.0055075 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
 * 2.124: [GC (Allocation Failure) [PSYoungGen: 2946K->2176K(5632K)] 3749K->2980K(202240K), 0.0021476 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * </pre>
 * 
 * 例2
 * 
 * <pre>
 * 参数
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGC -XX:+PrintGCTimeStamps
 * 
 * 输出示例
 * 2.010: [GC (System.gc())  133043K->133070K(202240K), 0.0272968 secs]
 * 2.038: [Full GC (System.gc())  133070K->803K(202240K), 0.0055370 secs]
 * 2.045: [GC (Allocation Failure)  3750K->2979K(202240K), 0.0021762 secs]
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestPrintGCTimeStamps {
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
