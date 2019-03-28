package cn.zxf.btrace.demo.memory;

import java.util.ArrayList;
import java.util.List;

import cn.zxf.btrace.common.BTraceCommand;
import cn.zxf.btrace.common.Sleep;

/**
 * -XX:+UseG1GC
 * 
 * <p>
 * Created by zengxf on 2017-08-30
 */
public class MemoryDemo {

    public static void main( String[] args ) {
        Runnable r = () -> {
            List<String> list = new ArrayList<>();
            int i = 0;
            while ( true ) {

                list.add( "test-" + i++ );

                if ( list.size() > 1000_000 ) {
                    list.clear();
                    System.out.println( " - clear - " );
                    Sleep.sleep( 1000L );
                }
            }
        };

        Thread t = new Thread( r, "test-run" );
        t.start();

        BTraceCommand.execute();
    }

}
