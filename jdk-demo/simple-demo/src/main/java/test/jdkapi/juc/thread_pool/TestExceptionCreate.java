package test.jdkapi.juc.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果一个线程由于执行期间因失败而终止，则创建一个新线程，抛出异常的线程则直接退出
 * 
 * <p>
 * Created by zengxf on 2017-09-25
 */
public class TestExceptionCreate {
    public static void main( String[] args ) {
        ExecutorService exe = Executors.newFixedThreadPool( 3 );
        for ( int i = 0; i < 12; i++ ) {
            int j = i;
            exe.execute( () -> {
                System.out.println( "j: " + j + ", thread: " + Thread.currentThread().getName() );
                if ( j > 5 && j < 10 )
                    throw new RuntimeException( "error - " + j );
            } );
        }
        exe.shutdown();
    }
}
