package test.jvm.gc.log;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 在进行GC的前后打印出堆的信息 <br>
 * <br>
 * 
 * 参数
 * 
 * <pre>
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGC -XX:+PrintHeapAtGC
 * </pre>
 * 
 * 输出示例
 * 
 * <pre>
 * {Heap before GC invocations=59 (full 3):
 *  PSYoungGen      total 5632K, used 5632K [0x00000000ff800000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 3072K, 100% used [0x00000000ff800000,0x00000000ffb00000,0x00000000ffb00000)
 *   from space 2560K, 100% used [0x00000000ffb00000,0x00000000ffd80000,0x00000000ffd80000)
 *   to   space 2560K, 0% used [0x00000000ffd80000,0x00000000ffd80000,0x0000000100000000)
 *  ParOldGen       total 196608K, used 83225K [0x00000000f3800000, 0x00000000ff800000, 0x00000000ff800000)
 *   object space 196608K, 42% used [0x00000000f3800000,0x00000000f8946708,0x00000000ff800000)
 *  Metaspace       used 4190K, capacity 4662K, committed 4864K, reserved 1056768K
 *   class space    used 432K, capacity 472K, committed 512K, reserved 1048576K
 * [GC (Allocation Failure)  88857K->88905K(202240K), 0.0305105 secs]
 * Heap after GC invocations=59 (full 3):
 *  PSYoungGen      total 5632K, used 2560K [0x00000000ff800000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 3072K, 0% used [0x00000000ff800000,0x00000000ff800000,0x00000000ffb00000)
 *   from space 2560K, 100% used [0x00000000ffd80000,0x0000000100000000,0x0000000100000000)
 *   to   space 2560K, 0% used [0x00000000ffb00000,0x00000000ffb00000,0x00000000ffd80000)
 *  ParOldGen       total 196608K, used 86345K [0x00000000f3800000, 0x00000000ff800000, 0x00000000ff800000)
 *   object space 196608K, 43% used [0x00000000f3800000,0x00000000f8c52708,0x00000000ff800000)
 *  Metaspace       used 4190K, capacity 4662K, committed 4864K, reserved 1056768K
 *   class space    used 432K, capacity 472K, committed 512K, reserved 1048576K
 * }
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestPrintHeapAtGC {
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
