package test.jvm.memory;

import java.lang.management.ManagementFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 参数优先级设置测试：年轻代大小
 * 
 * <pre>
 * -Xmx200m -Xms200m -Xmn100m -XX:NewSize=20m -XX:MaxNewSize=150m
 * 
 * -XX:NewSize 与 -XX:MaxNewSize 优先级高
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-21
 */
public class TestXmnVsNewSize {
    public static void main( String[] args ) {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        while ( true ) {
            IntStream.rangeClosed( 1, 200 ).boxed().collect( Collectors.toList() );
        }
    }
}
