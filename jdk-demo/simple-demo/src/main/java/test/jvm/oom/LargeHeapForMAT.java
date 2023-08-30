package test.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试大堆 <br>
 * [原文参考](http://www.techpaste.com/2015/07/how-to-analyse-large-heap-dumps)
 * <p>
 * 
 * 测试参数
 * 
 * <pre>
 * 推荐文件后缀：*.hprof
 * -Xmx2G -Xms2G -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="D:/oom/f.dump"
 * </pre>
 * 
 * 修改 MemoryAnalyzer.ini
 * 
 * <pre>
 * -Xms6144m
 * -Xmx8192m
 * -XX:+UseConcMarkSweepGC
 * -XX:+UseParNewGC
 * -XX:+CMSParallelRemarkEnabled
 * -XX:+CMSClassUnloadingEnabled
 * -XX:+UseCMSInitiatingOccupancyOnly
 * </pre>
 * 
 * 生成 html zip 文件
 * 
 * <pre>
 * // Linux
 *   ./ParseHeapDump.sh /opt/heap_dump/jvm.hprof org.eclipse.mat.api:suspects
 *   ./ParseHeapDump.sh /opt/heap_dump/jvm.hprof org.eclipse.mat.api:overview
 *   ./ParseHeapDump.sh /opt/heap_dump/jvm.hprof org.eclipse.mat.api:top_components
 * // Windows
 *   ParseHeapDump.bat D:\oom\f.dump org.eclipse.mat.api:suspects
 *   ParseHeapDump.bat D:\oom\f.dump org.eclipse.mat.api:overview
 *   ParseHeapDump.bat D:\oom\f.dump org.eclipse.mat.api:top_components
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-03-16
 */
public class LargeHeapForMAT {

    // 5M
    public static class BigObj {
        byte[] byteArr = new byte[1024 * 1024]; // 1M
        int[]  intArr  = new int[1024 * 1024];  // 4M
    }

    public static void main( String[] args ) throws InterruptedException {
        long b = System.currentTimeMillis();
        List<BigObj> list = new ArrayList<>();
        for ( int i = 0; i < 400; i++ ) {
            list.add( new BigObj() );
            Thread.sleep( 1 );
        }
        long e = System.currentTimeMillis();
        System.out.println( e - b );
    }

}
