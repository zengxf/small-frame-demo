package test.jdkapi.lang.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 用 remove() 方法进行等待获取
 * <p>
 * Created by zengxf on 2019-02-25
 */
public class TestRefferenceQueue {

    public static void main( String[] args ) throws InterruptedException {
        ReferenceQueue<String> rq = new ReferenceQueue<>();
        String str = new String( "dd" );
        WeakReference<String> r1 = new WeakReference<>( str, rq );
        System.out.println( r1 );

        Thread thread = new Thread( () -> {
            try {
                int cnt = 0;
                Reference<?> k;
                while ( ( k = (Reference<?>) rq.remove() ) != null ) {
                    System.out.println( ( cnt++ ) + " 回收了: " + k );
                }
            } catch ( InterruptedException e ) {
                // 结束循环
            }
        } );
        thread.setDaemon( true );
        thread.start();

        System.gc();
        Thread.sleep( 1000 );
        str = null; // referentObj 要为 null
        System.gc();
    }

}
