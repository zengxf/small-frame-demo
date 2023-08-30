package test.jvm.off_heap;
//package cn.simple.test.jvm.off_heap;
//
//import java.nio.ByteBuffer;
//import java.util.concurrent.TimeUnit;
//
//import sun.nio.ch.DirectBuffer;
//
///**
// * System.gc我们可以禁掉，使用 -XX:+DisableExplicitGC ，<br>
// * 其实一般在cms gc下我们通过 -XX:+ExplicitGCInvokesConcurrent 也可以做稍微高效一点的gc，也就是并行gc。
// * 
// * <p>
// * Created by zengxf on 2017-09-08
// */
// @SuppressWarnings( "restriction" )
//public class TestDirectBuffer2 {
//
//    public static void main( String[] args ) throws InterruptedException {
//        test4();
//    }
//
//    static void test() {
//        // 分配128MB直接内存
//        ByteBuffer.allocateDirect( 1024 * 1024 * 128 );
//        try {
//            TimeUnit.SECONDS.sleep( 10 );
//        } catch ( InterruptedException e ) {
//            e.printStackTrace();
//        }
//        System.out.println( "ok" );
//    }
//
//    /**
//     * 测试用例1：设置JVM参数 -Xmx100m ，运行异常，因为如果没设置-XX:MaxDirectMemorySize，<br>
//     * 则默认与-Xmx参数值相同，分配128M直接内存超出限制范围。
//     */
//    static void test1() {
//        test();
//    }
//
//    /**
//     * 测试用例2：设置JVM参数 -Xmx256m ，运行正常，因为128M小于256M，属于范围内分配。
//     */
//    static void test2() {
//        test();
//    }
//
//    /**
//     * 测试用例3：设置JVM参数 -Xmx256m -XX:MaxDirectMemorySize=100M ，运行异常，分配的直接内存128M超过限定的100M。
//     */
//    static void test3() {
//        test();
//    }
//
//    /**
//     * 测试用例4：设置JVM参数 -Xmx768m ，运行程序观察内存使用变化，<br>
//     * 会发现clean()后内存马上下降，说明使用clean()方法能有效及时回收直接缓存。
//     * 
//     * @throws InterruptedException
//     */
//    static void test4() throws InterruptedException {
//        TimeUnit.SECONDS.sleep( 10 );
//        System.out.println( "alloc start ..." );
//        // 分配512MB直接缓存
//        ByteBuffer bb = ByteBuffer.allocateDirect( 1024 * 1024 * 512 );
//        System.out.println( "alloc end" );
//
//        TimeUnit.SECONDS.sleep( 10 );
//        System.out.println( "clear start ..." );
//        // 清除直接缓存
//        ( (DirectBuffer) bb ).cleaner().clean();
//        // bb.clear(); // 最终调用 Buffer 类，因此无用
//        System.out.println( "clear end" );
//
//        TimeUnit.SECONDS.sleep( 10 );
//        System.out.println( "ok" );
//    }
//
//}
