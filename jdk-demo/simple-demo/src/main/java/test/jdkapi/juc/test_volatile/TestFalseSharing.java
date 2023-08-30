package test.jdkapi.juc.test_volatile;

/**
 * 测试伪共享解决方案 - 并没有测试出什么
 * <p>
 * 
 * Java8
 * 
 * <pre>
 * 类注解：@sun.misc.Contended
 * 解锁参数：-XX:-RestrictContended，因为默认用户代码下是无效的
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-03-09
 */
public class TestFalseSharing implements Runnable {
    public final static int             NUM_THREADS = 4;
    public final static long            ITERATIONS  = 500L * 1000L * 1000L;
    private final int                   arrayIndex;

    private static VolatileLong4Java8[] longs4J8    = new VolatileLong4Java8[NUM_THREADS];
    private static VolatileLong4Java7[] longs4J7    = new VolatileLong4Java7[NUM_THREADS];
    private static VolatileLong4None[]  longs4None  = new VolatileLong4None[NUM_THREADS];
    static {
        for ( int i = 0; i < longs4J8.length; i++ ) {
            longs4J8[i] = new VolatileLong4Java8();
            longs4J7[i] = new VolatileLong4Java7();
            longs4None[i] = new VolatileLong4None();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while ( 0 != --i ) {
            longs4J8[arrayIndex].value = i;
        }
    }

    public TestFalseSharing( final int arrayIndex ) {
        this.arrayIndex = arrayIndex;
    }

    public static void main( final String[] args ) throws Exception {
        long start = System.nanoTime();
        runTest();
        System.out.println( "duration = " + ( System.nanoTime() - start ) );
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for ( int i = 0; i < threads.length; i++ ) {
            threads[i] = new Thread( new TestFalseSharing( i ) );
        }

        for ( Thread t : threads ) {
            t.start();
        }

        for ( Thread t : threads ) {
            t.join();
        }
    }

    public final static class VolatileLong4None {
        public volatile long value = 0L;
    }

    // long padding避免false sharing
    // 按理说jdk7以后long padding应该被优化掉了，但是从测试结果看padding仍然起作用
    public final static class VolatileLong4Java7 {
        volatile long        p0, p1, p2, p3, p4, p5, p6;
        public volatile long value = 0L;
        volatile long        q0, q1, q2, q3, q4, q5, q6;
    }

    // jdk8新特性，Contended注解避免false sharing
    // Unlock: -XX:-RestrictContended
    // @SuppressWarnings( "restriction" )
    // @jdk.internal.vm.annotation.Contended
    public final static class VolatileLong4Java8 {
        public volatile long value = 0L;
    }
}
