package test.jvm.gc.log;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 输出GC的详细日志 <br>
 * <br>
 * 
 * 参数
 * 
 * <pre>
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGCDetails
 * </pre>
 * 
 * 输出示例
 * 
 * <pre>
 * [GC (System.gc()) [PSYoungGen: 3971K->1472K(5632K)] 25360K->25452K(202240K), 0.0221434 secs] [Times: user=0.25 sys=0.00, real=0.02 secs] 
 * [Full GC (System.gc()) [PSYoungGen: 1472K->0K(5632K)] [ParOldGen: 23980K->803K(196608K)] 25452K->803K(202240K), [Metaspace: 4199K->4199K(1056768K)], 0.0053296 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
 * [GC (Allocation Failure) [PSYoungGen: 2945K->2144K(5632K)] 3748K->2947K(202240K), 0.0021399 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestPrintGCDetails {
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
