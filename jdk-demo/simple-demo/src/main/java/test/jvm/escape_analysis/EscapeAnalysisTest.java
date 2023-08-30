package test.jvm.escape_analysis;

import java.lang.management.ManagementFactory;

/**
 * 逃逸分析
 * 
 * <pre>
 * 1) -Xmx3G -Xmn2G -server -verbose:gc -XX:-DoEscapeAnalysis // java 8 默认是开启的，所以要先禁用再测试
 * 2) -Xmx3G -Xmn2G -server -verbose:gc -XX:+DoEscapeAnalysis
 * 3) -Xmx3G -Xmn2G -server -verbose:gc -XX:+DoEscapeAnalysis -XX:-TieredCompilation 
 *    // 关闭分层编译；分层编译对逃逸分析还是有影响的。users > 10000
 * 4) -Xmx3G -Xmn2G -server -verbose:gc -XX:+DoEscapeAnalysis -XX:-TieredCompilation -XX:CompileThreshold=5000 
 *    // 提早触发编译行为，减少在堆上生成User对象。users > 5000
 * 5) -Xmx3G -Xmn2G -server -verbose:gc -XX:+DoEscapeAnalysis -XX:-TieredCompilation -XX:CompileThreshold=5000 -XX:-BackgroundCompilation 
 * // 取消后台编译，改成立即编译。users = 5000
 * 6) -Xmx3G -Xmn2G -server -verbose:gc -XX:+DoEscapeAnalysis -XX:-TieredCompilation -XX:CompileThreshold=5000 -XX:-BackgroundCompilation 
 *      -XX:-EliminateAllocations
 *    // 禁用标量替换，则不会进行优化。users = 200_0000
 * 
 * A) jmap -histo PID | findStr User    // 查看 User 实例数
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-11-24
 */
public class EscapeAnalysisTest {

    public static void main( String[] args ) throws Exception {
        System.out.println( "\npid:\t" + ManagementFactory.getRuntimeMXBean().getName().split( "@" )[0] + "\n" );

        long start = System.nanoTime();
        int sum = 0;
        int count = 100_0000;
        // warm up
        for ( int i = 0; i < count; i++ ) {
            sum += fn( i );
        }

        Thread.sleep( 500 );

        for ( int i = 0; i < count; i++ ) {
            sum += fn( i );
        }

        System.out.println( sum );
        System.out.println( "time: " + ( System.nanoTime() - start ) );
        System.in.read();
    }

    private static int fn( int age ) {
        User user = new User( age );
        int i = user.getAge();
        return i;
    }
}

class User {
    private final int age;

    public User( int age ) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
