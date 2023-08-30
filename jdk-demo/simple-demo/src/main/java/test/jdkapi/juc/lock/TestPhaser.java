package test.jdkapi.juc.lock;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

import util.SleepUtils;

/**
 * 1) 类似 CyclicBarrier，等待所有的线程到达再执行 <br>
 * 2) 类似 CountDownLatch，countDown() 与 await()
 * <p>
 * 解释参考：https://segmentfault.com/a/1190000015979879
 * <p>
 * Created by zengxf on 2018-02-27
 */
public class TestPhaser {

    public static void main( String[] args ) {
        // test1();
        // test2();
        TierTest.main();
    }

    // 测试分层
    static class TierTest {
        private static final int TASKS_PER_PHASER = 4; // 每个Phaser对象对应的工作线程（任务）数

        static void main() {
            int repeats = 3; // 指定任务最多执行的次数
            Phaser phaser = new Phaser() {
                @Override
                protected boolean onAdvance( int phase, int registeredParties ) {
                    System.out.println( "---------------PHASE[" + phase + "],Parties[" + registeredParties + "] ---------------" );
                    return phase + 1 >= repeats || registeredParties == 0;
                }
            };

            Tasker[] taskers = new Tasker[10];
            build( taskers, 0, taskers.length, phaser ); // 根据任务数,为每个任务分配Phaser对象

            for ( int i = 0; i < taskers.length; i++ ) { // 执行任务
                Thread thread = new Thread( taskers[i] );
                thread.start();
            }
        }

        static void build( Tasker[] taskers, int lo, int hi, Phaser phaser ) {
            if ( hi - lo > TASKS_PER_PHASER ) {
                for ( int i = lo; i < hi; i += TASKS_PER_PHASER ) {
                    int j = Math.min( i + TASKS_PER_PHASER, hi );
                    build( taskers, i, j, new Phaser( phaser ) );
                }
            } else {
                for ( int i = lo; i < hi; ++i )
                    taskers[i] = new Tasker( i, phaser );
            }

        }

        static class Tasker implements Runnable {
            private final Phaser phaser;
            private int          count;

            Tasker( int i, Phaser phaser ) {
                this.count = i;
                this.phaser = phaser;
                this.phaser.register();
            }

            @Override
            public void run() {
                while ( !phaser.isTerminated() ) { // 只要Phaser没有终止, 各个线程的任务就会一直执行
                    phaser.arriveAndAwaitAdvance(); // 等待其它参与者线程到达
                    Thread currentThread = Thread.currentThread();
                    System.out.println( currentThread.getName() + ": 执行完任务，count=" + count );
                }
            }
        }
    }

    // 等待所有的线程到达再执行
    static void test1() {
        int count = 3;
        Phaser p = new Phaser( count );
        Runnable r = () -> {
            Thread currentThread = Thread.currentThread();
            SleepUtils.second( 1 );
            System.out.println( currentThread.getName() + " in ..." );
            p.arriveAndAwaitAdvance();
            SleepUtils.second( 1 );
            System.out.println( currentThread.getName() + " in ... 2" );
            p.arriveAndAwaitAdvance();
            System.out.println( currentThread.getName() + " OK." );
        };
        IntStream.rangeClosed( 1, count * 2 )
                .forEach( i -> {
                    new Thread( r, "test-" + i ).start();
                } );
    }

    // countDown() 与 await()
    static void test2() {
        int count = 3;
        Phaser p = new Phaser( 1 );
        Runnable r = () -> {
            Thread currentThread = Thread.currentThread();
            System.out.println( currentThread.getName() + " in ..." );
            p.awaitAdvance( p.getPhase() ); // countDownLatch.await()
            System.out.println( currentThread.getName() + " OK." );
        };
        IntStream.rangeClosed( 1, count )
                .forEach( i -> {
                    new Thread( r, "test-" + i ).start();
                } );
        SleepUtils.second( 1 );
        System.out.println( "main ----" );
        p.arrive(); // countDownLatch.countDown()
    }

}
