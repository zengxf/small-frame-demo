package test.jdkapi.juc;

import java.util.concurrent.locks.LockSupport;

/**
 * park() 得加对象，jstack 才能看的到 <br>
 * unpark() 得一个一个搞
 * <p>
 * park() 支持响应中断，但要线程自己控制
 * 
 * <p>
 * Created by zengxf on 2018-02-22
 */
public class TestLockSupport {

    public static void main( String[] args ) throws InterruptedException {
        testInterrupt();
    }

    static void testInterrupt() throws InterruptedException {
        TestLockSupportInner lock = new TestLockSupportInner();
        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + " --------->" );
            LockSupport.park( lock );
            System.out.println( Thread.currentThread().getName() + " ---------> done" );
        };
        Thread t1 = new Thread( r );
        t1.start();
        Thread.sleep( 1000L );
        System.out.println( "main --->" );
        System.out.println( "t1 - isInterrupted 1 - " + t1.isInterrupted() );
        t1.interrupt();
        System.out.println( "t1 - isInterrupted 2 - " + t1.isInterrupted() );
    }

    static void testParkUnpark() throws InterruptedException {
        TestLockSupportInner lock = new TestLockSupportInner();
        Runnable r = () -> {
            System.out.println( Thread.currentThread().getName() + " --------->" );
            LockSupport.park( lock );
            System.out.println( Thread.currentThread().getName() + " ---------> done" );
        };
        Thread t1 = new Thread( r );
        t1.start();
        Thread t2 = new Thread( r );
        t2.start();
        Thread.sleep( 1000L );
        System.out.println( "main --->" );
        LockSupport.unpark( t1 );
        LockSupport.unpark( t2 );
    }

    static class TestLockSupportInner {
    }

}
