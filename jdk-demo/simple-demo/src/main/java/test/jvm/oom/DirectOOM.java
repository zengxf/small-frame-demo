package test.jvm.oom;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://blog.csdn.net/a837199685/article/details/75784279">原文参考</a>
 * <p>
 * Created by zengxf on 2018-12-03
 */
public class DirectOOM {

    public static void main( String[] args ) {
        // testNotYGC();
        testNotFullGC();
    }

    /**
     * <pre>
        -Xmx64m
        -Xms64m
        -Xmn32m
        -XX:+UseConcMarkSweepGC
        -XX:+PrintGCDetails
        -XX:+DisableExplicitGC // 限制 GC 调用
        -XX:MaxDirectMemorySize=10M // 堆外10M
     * </pre>
     */
    static void testNotYGC() {
        int i = 1;
        while ( true ) {
            System.out.println( "第" + ( i++ ) + "次" );
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect( 1024 * 1024 );
            System.out.println( byteBuffer );
        }
    }

    /**
     * <pre>
        -Xmx64m
        -Xms64m
        -Xmn32m
        -XX:+PrintGCDetails
        -XX:MaxTenuringThreshold=1 // 设置进去 old gen 的寿命是 1
        -XX:MaxDirectMemorySize=10M
        -XX:+DisableExplicitGC // 不能显示 Full GC
     * </pre>
     */
    static void testNotFullGC() {
        int i = 1;
        List<ByteBuffer> list = new ArrayList<ByteBuffer>();
        while ( true ) {
            System.out.println( "第" + ( i++ ) + "次" );
            ByteBuffer.allocate( 1024 * 1024 );// 分配堆内内存
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect( 1024 / 2 );
            list.add( byteBuffer ); // 保证引用不被 YGC
        }
    }

}
