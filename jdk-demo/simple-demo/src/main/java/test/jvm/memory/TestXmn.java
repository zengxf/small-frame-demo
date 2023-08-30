package test.jvm.memory;

import java.lang.management.ManagementFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 参数设置测试：年轻代大小
 * 
 * <pre>
 * -Xmx200m -Xms200m -Xmn100m
 * 
 * Xmn 表示：年轻代的大小
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-21
 */
public class TestXmn {
    public static void main( String[] args ) {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        while ( true ) {
            IntStream.rangeClosed( 1, 200 ).boxed().collect( Collectors.toList() );
        }
    }
}
