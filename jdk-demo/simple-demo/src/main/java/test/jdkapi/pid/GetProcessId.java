package test.jdkapi.pid;

import java.lang.management.ManagementFactory;

/**
 * 获取 java 进程号(ID)
 * 
 * <p>
 * Created by zengxf on 2017-11-06
 */
public class GetProcessId {

    public static void main( String[] args ) {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );
        System.out.println( ManagementFactory.getRuntimeMXBean().getName().split( "@" )[0] );
    }

}
