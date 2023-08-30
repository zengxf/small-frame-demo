package test.jvm.memory;

/**
 * 测试伪共享问题
 * <p>
 * <a href="https://juejin.im/entry/5b9130aa6fb9a05d011cbabb">原文参考</a> <a href="https://mp.weixin.qq.com/s/4oU6YqxHso2ir0NXtBuaog">原文参考-微信</a>
 * <p>
 * Created by zengxf on 2018-09-07
 */
public class TestFalseSharing implements Runnable {
    public final static int       NUM_THREADS = 4;                            // change
    public final static long      ITERATIONS  = 50L * 1000L * 1000L;
    private final int             arrayIndex;

    private static VolatileLong[] longs       = new VolatileLong[NUM_THREADS];

    static {
        for ( int i = 0; i < longs.length; i++ ) {
            longs[i] = new VolatileLong();
        }
    }

    public TestFalseSharing( final int arrayIndex ) {
        this.arrayIndex = arrayIndex;
    }

    public static void main( final String[] args ) throws Exception {
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println( "duration = " + ( System.currentTimeMillis() - start ) );
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

    public void run() {
        long i = ITERATIONS + 1;
        while ( 0 != --i ) {
            longs[arrayIndex].value = i;
        }
    }

    public final static class VolatileLong {
        // @sun.misc.Contended // -XX:-RestrictContended
        public volatile long value = 0L;
        public long          p1, p2, p3, p4, p5, p6; // 填充，可以注释后对比测试
    }

}