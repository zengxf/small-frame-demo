package test.jvm.oom;

import java.util.Map;
import java.util.Random;

/**
 * <pre>
 * -Xmx100m -XX:+UseParallelGC
 * -XX:-UseGCOverheadLimit      // 可以关闭此检测。如果垃圾回收耗费了98%的时间，但是回收的内存还不到2%，那么JVM会认为即将发生OOM，让程序提前结束
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class GCOverhead {
    @SuppressWarnings( { "rawtypes", "unchecked" } )
    public static void main( String args[] ) throws Exception {
        Map map = System.getProperties();
        Random r = new Random();
        while ( true ) {
            map.put( r.nextInt(), "java 9" );
        }
    }
}
