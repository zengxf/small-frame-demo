package test.jvm.remote.debug;

/**
 * 远程调试
 * <p>
 * 
 * JVM 参数：
 * 
 * <pre>
 *  `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000`      // >= jdk1.5
 *  `-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000`   // jdk1.4
 *      `suspend=y` 等到 eclipse 连接，服务才能用，默认为 `y`
 *      `address=0.0.0.0:8000` 默认 IP 是 127.0.0.1，可用  `netstat -na | grep 8000` 查看
 *  测试：
 *      `java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 cn.simple.test.jvm.remote.debug.TestRemoteDebug`
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-03-19
 */
// M:\project\zxf_super_demo\simple-demo\bin\main\test\jvm\remote\debug
public class TestRemoteDebug {

    public static void main( String[] args ) throws InterruptedException {
        System.out.println( System.getProperty( "java.version" ) );
        int i = 0;
        while ( true ) {
            System.out.printf( "i: %s %n", i++ );
            System.out.printf( "date: %tT %n", System.currentTimeMillis() );
            Thread.sleep( 1000L );
        }
    }

}
