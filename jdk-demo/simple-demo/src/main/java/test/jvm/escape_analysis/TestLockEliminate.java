package test.jvm.escape_analysis;

/**
 * 测试锁消除
 * 
 * <pre>
 * 测试1 锁不消除：-server -XX:+DoEscapeAnalysis -XX:-EliminateLocks (用时：130~140ms)
 * 测试2 锁消除　：-server -XX:+DoEscapeAnalysis -XX:+EliminateLocks (用时：110~120ms)
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-03-06
 */
public class TestLockEliminate {
    public static String getString( String s1, String s2 ) {
        StringBuffer sb = new StringBuffer();
        sb.append( s1 );
        sb.append( s2 );
        return sb.toString();
    }

    public static void main( String[] args ) {
        long tsStart = System.currentTimeMillis();
        for ( int i = 0; i < 1000000; i++ ) {
            getString( "TestLockEliminate ", "Suffix" );
        }
        System.out.println( "一共耗费：" + ( System.currentTimeMillis() - tsStart ) + " ms" );
    }
}
