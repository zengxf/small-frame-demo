package test.jvm.gc.log;

/**
 * 
 * 验证 FullGC 没有调用 MinorGC <br>
 * <br>
 * 
 * 参数
 * 
 * <pre>
 * -verbose:gc
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2017-09-29
 */
public class TestFullGcNonMinorGc {
    public static void main( String[] args ) {
        System.gc();
        System.out.println( "ok" );
    }
}
