package test.jvm.gc.log;

import java.time.LocalTime;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import util.SleepUtils;

/**
 * 输出GC日志 <br>
 * <br>
 * 
 * 参数
 * 
 * <pre>
 * -Xms200m -Xmx200m -Xmn8m
 * -Xlog:gc*,gc+ref=debug,gc+heap=debug,gc+age=trace:file=gc-%t.log:tags,uptime,time,level:filecount=10,filesize=50m
 * -Xlog:safepoint*:file=safepoints-%t.log:tags,uptime,time,level:filecount=10,filesize=50m
 * </pre>
 * 
 * 输出示例
 * 
 * <pre>
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-20
 */
public class TestJdk11PrintGC {
    public static void main( String[] args ) {
        int k = 1024;
        while ( true ) {
            int r = 1 + new Random().nextInt( 5 );
            System.out.println( LocalTime.now() + " === r: " + r + " mb" );
            // System.gc();
            IntStream.rangeClosed( 1, r * k * k )
                    .boxed()
                    .collect( Collectors.toList() );
            SleepUtils.second( 1 );
        }
    }
}
