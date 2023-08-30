package test.socket;

/**
 * 并非提供端口，而是远程JVM设置
 * 
 * <p>
 * Created by zxf on 2017-05-17
 */
@Deprecated // 只能参考
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

// jconsole 远程连接
// Linux:
// 设置密码
// echo "admin 123" > /base-data/jdk1.8.0_91/jre/lib/management/jmxremote.password
// 修改文件权限
// chmod 0400 /base-data/jdk1.8.0_91/jre/lib/management/jmxremote.password

