package test.jdkapi.juc.lock;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * 限制访问资源的线程数目 <br>
 * acquire() 与 release() 配对
 * 
 * <p>
 * Created by zengxf on 2018-02-22
 */
public class TestSemaphore {

    public static void main( String[] args ) {
        test_common();
        // test_throwError();
        // test_fair();
        // test_aqs();
    }

    static void test_aqs() {
        int count = 1;
        Semaphore sp = new Semaphore( count, false );

        Runnable r1 = () -> {
            try {
                sp.acquire();
                System.out.println( Thread.currentThread()
                        .getName() + " --------- 1" );
                sp.acquire();
                System.out.println( Thread.currentThread()
                        .getName() + " --------- 2" );
            } catch ( InterruptedException e ) {
            }
        };
        new Thread( r1, "----test-acquire-[获取-1]------" ).start();

        Runnable r2 = () -> {
            try {
                sp.acquire();
                System.out.println( Thread.currentThread()
                        .getName() + " --------- 1" );
            } catch ( InterruptedException e ) {
            }
        };
        new Thread( r2, "----test-acquire-[获取-2]------" ).start();

        Runnable r3 = () -> {
            sp.release();
            sp.release();
            sp.release();
            System.out.println( Thread.currentThread()
                    .getName() + " --------- 1" );
        };
        new Thread( r3, "----test-acquire-[释放]------" ).start();
    }

    static void test_throwError() {
        int count = 3;

        Semaphore sp = new Semaphore( count );

        Runnable r = () -> {
            try {
                sp.acquire();
                System.out.println( Thread.currentThread()
                        .getName() + " --------- 1" );
                throw new RuntimeException( "=====" );
            } catch ( InterruptedException e ) {
            } finally {
                sp.release();
            }
        };

        IntStream.rangeClosed( 1, count + 2 )
                .forEach( i -> {
                    new Thread( r, "test-" + i ).start();
                } );
    }

    static void test_common() {
        int count = 3;

        Semaphore sp = new Semaphore( count );

        Runnable r = () -> {
            try {
                Thread currentThread = Thread.currentThread();
                sp.acquire();
                System.out.println( currentThread.getName() + " --------- 1" );
                sp.release();

                sp.acquire();
                System.out.println( currentThread.getName() + " --------- 2" );
                sp.release();

                sp.acquire();
                System.out.println( currentThread.getName() + " --------- 3" );
                sp.release();
            } catch ( InterruptedException e ) {
            }
        };

        IntStream.rangeClosed( 1, count + 2 )
                .forEach( i -> {
                    new Thread( r, "test-" + i ).start();
                } );
    }

    static void test_fair() {
        int count = 2;

        Semaphore sp = new Semaphore( count, false );

        Runnable r = () -> {
            try {
                sp.acquire();
                System.out.println( Thread.currentThread()
                        .getName() + " --------- 1" );

                sp.acquire();
                System.out.println( Thread.currentThread()
                        .getName() + " --------- 2" );

                sp.release();
                sp.release();

                sp.acquire();
                System.out.println( Thread.currentThread()
                        .getName() + " --------- 3" );
                sp.release();
            } catch ( InterruptedException e ) {
            }
        };

        IntStream.rangeClosed( 1, 2 )
                .forEach( i -> {
                    new Thread( r, "test-" + i ).start();
                } );
    }

}
