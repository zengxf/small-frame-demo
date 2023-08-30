package test.jvm.gc;

import java.util.ArrayList;

/**
 * <pre>
 * -Xms200m -Xmx200m -Xmn8m
 * -XX:+PrintGCDateStamps
 * -XX:+PrintGCTimeStamps -XX:+PrintGCDetails
 * -verbose:gc -Xloggc:C:\Users\Administrator\Desktop/gc.log
 * </pre>
 * 
 * <pre>
 * 在线GC分析工具：http://gceasy.io
 * 桌面工具（下载后构建）：https://github.com/jewes/gchisto
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestGcLog {
    public static void main( String[] args ) {
        while ( true ) {
            new ArrayList<>( 200 );
        }
    }
}
