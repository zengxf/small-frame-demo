package test.jdkapi.juc.task;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestTest {

    public static void main( String[] args ) {

        ThreadPool.scheduled0Delay( () -> {
            System.out.println( String.format( "%tT", new Date() ) );
            // System.out.println( 1 / 0 );
            try {
                Thread.sleep( 3000L );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }, 2000, TimeUnit.MILLISECONDS );

        ThreadPool.execute( () -> {
            System.out.println( String.format( "----> %tT", new Date() ) );
            System.out.println( 1 / 0 );
        } );

    }

}
