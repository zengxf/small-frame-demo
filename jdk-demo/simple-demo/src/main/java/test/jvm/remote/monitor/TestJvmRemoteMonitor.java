package test.jvm.remote.monitor;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 远程 JVM 监控
 * <p>
 * 
 * <pre>
   ----
   No Authenticate 无密码无认证：
   Start 命令：                                                    
       java                                                  \
          -Dcom.sun.management.jmxremote                     \
          -Djava.rmi.server.hostname=192.168.1.117           \
          -Dcom.sun.management.jmxremote.port=8888           \
          -Dcom.sun.management.jmxremote.ssl=false           \
          -Dcom.sun.management.jmxremote.authenticate=false  \
          cn.simple.test.jvm.remote.monitor.TestJvmRemoteMonitor
   ----
   JConsole 连接：
       192.168.1.117:8888
   VisualVM 连接：
       Add Remote Host：192.168.1.117
       Add JMX Connection：8888
 * </pre>
 * 
 * <pre>
   ----
   Authenticate 密码认证：
       Reference: http://www.voidcn.com/article/p-qnwtydxi-bkw.html
   Authenticate 文件配置：
       printf "admin test \nmonitor test \n" > password.txt
       printf "admin readwrite \n monitor readonly \n" > access.txt
       chmod 0400 password.txt
       Explain 解释：`readwrite` 可操作，如执行 GC；`readonly` 只能查看。密码文件用户名前不能有空格
   Start 命令：                                                    
       java                                                  \
          -Dcom.sun.management.jmxremote                     \
          -Djava.rmi.server.hostname=192.168.1.117           \
          -Dcom.sun.management.jmxremote.port=8888           \
          -Dcom.sun.management.jmxremote.ssl=false           \
          -Dcom.sun.management.jmxremote.authenticate=true   \
          -Dcom.sun.management.jmxremote.password.file=password.txt \
          -Dcom.sun.management.jmxremote.access.file=access.txt     \
          cn.simple.test.jvm.remote.monitor.TestJvmRemoteMonitor
   ----
   JConsole 连接：
       192.168.1.117:8888            # 设置用户名和密码
   VisualVM 连接：
       Add Remote Host：192.168.1.117
       Add JMX Connection：8888      # 设置用户名和密码
 * </pre>
 * 
 * <pre>
 * 其他问题：
 *     不要启用 ssl
 * </pre>
 * <p>
 * Created by zengxf on 2018-03-19
 */
// M:\project\zxf_super_demo\simple-demo\bin\main\test\jvm\remote\monitor
public class TestJvmRemoteMonitor {

    public static class HoldMemoryTask implements Runnable {
        static Map<Long, byte[]> map  = new WeakHashMap<Long, byte[]>();

        int                      sign = 0;

        @Override
        public void run() {
            while ( true ) {
                map.put( System.nanoTime(), new byte[512] );
                millisecond( 1 );
                if ( sign++ == 5000 ) {
                    sign = 0;
                    map.clear();
                    System.out.printf( "[%tT] map-clear! %n", System.currentTimeMillis() );
                }
            }
        }
    }

    public static void main( String[] args ) {
        new Thread( new HoldMemoryTask(), "test-001" ).start();
        System.out.println( "test start" );
    }

    public static final void millisecond( long milliseconds ) {
        try {
            TimeUnit.MILLISECONDS.sleep( milliseconds );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

}
