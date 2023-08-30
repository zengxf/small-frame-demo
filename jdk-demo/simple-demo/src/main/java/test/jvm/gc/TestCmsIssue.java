package test.jvm.gc;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * -Xms500m -Xmx500m -Xmn200m
 * -XX:+UseConcMarkSweepGC 
 * -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=90
 * 解决：加上 => 
 * -XX:+ScavengeBeforeFullGC
 * -XX:+CMSScavengeBeforeRemark
 * 日志：
 * -Xloggc:C:\Users\Administrator\Desktop/gc.log
 * -XX:+PrintGCDetails
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-21
 */
public class TestCmsIssue {
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        allocateMemory();
        System.out.println( "end ..." );
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        Thread.sleep( 100_000L );
    }

    static void allocateMemory() {
        int k = 1024;
        int m = k * k;
        int size = 450 * m;
        int len = size / ( 20 * k );

        List<byte[]> list =
                // new ArrayList<>(len);
                new ArrayList<>();

        for ( int i = 0; i < len; i++ ) {
            try {
                byte[] arr = new byte[20 * k];
                list.add( arr );
            } catch ( OutOfMemoryError e ) {
                System.out.println( "error: " + i );
            }
        }
    }
}
