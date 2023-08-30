package test.jvm.memory;

import java.lang.management.ManagementFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 参数设置测试：年轻代和老年代的比例
 * 
 * <pre>
 * -Xmx200m -Xms200m -XX:NewRatio=2
 * 
 * NewRatio=2 表示：年轻代和老年代的比例为 1:2
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-21
 */
public class TestNewRatio {
    public static void main( String[] args ) {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        while ( true ) {
            IntStream.rangeClosed( 1, 200 ).boxed().collect( Collectors.toList() );
        }
    }
}
