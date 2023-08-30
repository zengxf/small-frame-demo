package test.jvm.gc.cms;

import java.lang.management.ManagementFactory;

/**
 * 参数
 * 
 * <pre>
 * -Xmx20m -Xms20m -Xmn10m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
 * -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75
 * -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-10-16
 */
public class TestCmsSequent {
    private static final int _1MB = 1024 * 1024;

    public static void main( String[] args ) throws Exception {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        byte[] b1 = new byte[2 * _1MB];
        byte[] b2 = new byte[2 * _1MB];
        byte[] b3 = new byte[2 * _1MB];
        byte[] b4 = new byte[4 * _1MB];
        System.out.println( b1 );
        System.out.println( b2 );
        System.out.println( b3 );
        System.out.println( b4 );
        System.in.read();
    }

}
