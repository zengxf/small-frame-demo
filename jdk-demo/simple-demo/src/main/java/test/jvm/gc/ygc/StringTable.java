package test.jvm.gc.ygc;

import java.util.UUID;

/**
 * <a href="http://lovestblog.cn/blog/2016/11/06/string-intern/">原文参考</a> <br>
 * 测试 String.intern() 引起的 YGC 越来越慢 <br>
 * JVM 参数
 * 
 * <pre>
 * -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx2G -Xms2G -Xmn100M
 * </pre>
 * 
 * 分析
 * 
 * <pre>
 * YGC过程会扫描StringTable，其越大时间越长。在Full GC或者CMS GC过程会对StringTable做清理。
 * 验证：jmap -histo:live pid
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-08-28
 */
public class StringTable {

    public static void main( String[] args ) throws InterruptedException {
        Thread.sleep( 5_000L );
        for ( int i = 0; i < 1000_0000; i++ ) {
            UUID.randomUUID()
                    .toString()
                    .intern();
        }
    }

}
