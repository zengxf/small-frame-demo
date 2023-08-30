package test.jvm.memory;

/**
 * 测试 TLAB
 * <p>
 * 
 * 测试速度
 * 
 * <pre>
 * -XX:-UseTLAB -Xcomp -XX:-BackgroundCompilation -XX:-DoEscapeAnalysis -server
 * -XX:+UseTLAB -Xcomp -XX:-BackgroundCompilation -XX:-DoEscapeAnalysis -server
 * </pre>
 * 
 * 测试日志
 * 
 * <pre>
 * -XX:+UseTLAB  -XX:+PrintTLAB -XX:+PrintGC -XX:TLABSize=307200 -XX:-ResizeTLAB
 * </pre>
 * 
 * 改变refill waste
 * 
 * <pre>
 * 使用 -XX:TLABRefillWasteFraction 改变 refill waste
 * -XX:+UseTLAB  -XX:+PrintTLAB -XX:+PrintGC -XX:TLABSize=102400 -XX:-ResizeTLAB -XX:TLABRefillWasteFraction=100 
 * -XX:-DoEscapeAnalysis -server
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-03-19
 */
public class TestTLAB {

    public static void alloc() {
        byte[] b = new byte[2];
        b[0] = 1;
    }

    public static void main( String args[] ) {
        long b = System.currentTimeMillis();
        for ( int i = 0; i < 1000_0000; i++ ) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println( e - b );
    }

}
