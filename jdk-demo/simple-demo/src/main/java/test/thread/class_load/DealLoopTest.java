package test.thread.class_load;

/**
 * 两线程都是 RUNNABLE 状态，查不出是阻塞问题
 * 
 * <p>
 * Created by zengxf on 2018-08-01
 */
public class DealLoopTest {

    static class DeadLoopClass {
        static {
            if ( true ) {
                System.out.println( Thread.currentThread() + "init DeadLoopClass" );
                while ( true ) {
                }
            }
        }
    }

    public static void main( String[] args ) {
        Runnable script = () -> {
            System.out.println( Thread.currentThread() + " start" );
            new DeadLoopClass();
            System.out.println( Thread.currentThread() + " run over" );
        };

        new Thread( script, "test-1" ).start();
        new Thread( script, "test-2" ).start();
    }

}
