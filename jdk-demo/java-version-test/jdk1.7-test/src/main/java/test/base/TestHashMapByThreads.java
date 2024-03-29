package test.base;

import java.util.HashMap;

/**
 * Java 7 会出错 <br>
 * [参考原文](http://www.importnew.com/20386.html)
 * 
 * <p>
 * Created by zengxf on 2018-03-14
 */
public class TestHashMapByThreads {

    private static HashMap<Integer, String> map = new HashMap<Integer, String>( 2, 0.75f );

    public static void main( String[] args ) {
        map.put( 5, "C" );

        new Thread( "Thread1" ) {
            public void run() {
                map.put( 7, "B" );
                System.out.println( map );
            };
        }.start();

        new Thread( "Thread2" ) {
            public void run() {
                map.put( 3, "A" );
                System.out.println( map );
            };
        }.start();
    }

}
