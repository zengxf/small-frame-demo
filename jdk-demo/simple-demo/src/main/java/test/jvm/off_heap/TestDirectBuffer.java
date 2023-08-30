package test.jvm.off_heap;
//package cn.simple.test.jvm.off_heap;
//
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.List;
//
//import sun.nio.ch.DirectBuffer;
//
///**
// * 测试 JVM 参数
// * 
// * <pre>
// * 1) -verbose:gc -XX:+PrintGCDetails -XX:MaxDirectMemorySize=40M
// * 2) -verbose:gc -XX:+PrintGCDetails -XX:MaxDirectMemorySize=40M -XX:+DisableExplicitGC
// * </pre>
// * 
// * <p>
// * Created by zengxf on 2017-09-08
// */
// @SuppressWarnings( "restriction" )
//public class TestDirectBuffer {
//    static int k = 1024;
//
//    public static void main( String[] args ) {
//        test_base();
//        // test_ref();
//    }
//
//    /**
//     * JVM 参数：<br>
//     * -verbose:gc -XX:+PrintGCDetails -XX:MaxDirectMemorySize=40M <br>
//     * -verbose:gc -XX:+PrintGCDetails -XX:MaxDirectMemorySize=40M -XX:+DisableExplicitGC <br>
//     */
//    static void test_base() {
//        int i = 0;
//        while ( true ) {
//            DirectBuffer bf = (DirectBuffer) ByteBuffer.allocateDirect( 10 * k * k );
//            bf.cleaner().clean(); // 会清理
//            // System.gc();
//            System.out.println( "loop " + ++i );
//        }
//    }
//
//    /**
//     * 测试引用的影响 <br>
//     * JVM 参数：<br>
//     * -verbose:gc -XX:+PrintGCDetails -XX:MaxDirectMemorySize=40M <br>
//     * System.gc() 在 Bits.reserveMemory() 触发 <br>
//     */
//    static void test_ref() {
//        int i = 0;
//        List<ByteBuffer> list = new ArrayList<>();
//        while ( true ) {
//            ByteBuffer bf = ByteBuffer.allocateDirect( 10 * k * k ); // 有引用的话，则不回收
//            list.add( bf );
//            System.out.println( "loop " + ++i );
//        }
//    }
//}
