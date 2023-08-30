package test.thread.common_op;

/**
 * 简单测试线程中断的几个方法
 * <p>
 * Created by zengxf on 2018-08-02
 */
public class SimpleTestThreadInterrupt {

    public static void main( String[] args ) {
        System.out.println( "--" );
        Thread.currentThread()
                .interrupt(); // 中断
        System.out.println( "-- " + Thread.currentThread()
                .isInterrupted() ); // 返回中断标识 true
        System.out.println( "-- " + Thread.interrupted() ); // 返回中断标识 true，并清空标识
        System.out.println( "-- " + Thread.interrupted() ); // 返回中断标识 false，并清空标识
        System.out.println( "-- " + Thread.currentThread()
                .isInterrupted() ); // 返回中断标识 false

        try {
            Thread.sleep( 10 ); // 标识为 false, 可以阻塞
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

}
