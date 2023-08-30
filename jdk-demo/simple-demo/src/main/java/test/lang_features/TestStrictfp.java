package test.lang_features;

/**
 * Strictfp —— Java 关键字<br>
 * strictfp, 即 strict float point (精确浮点)<br>
 * 使用 strictfp 关键字声明一个方法时，该方法中所有的 float 和 double 表达式都严格遵守 FP-strict 的限制, 符合 IEEE-754 规范 <br>
 * 不会因为不同的硬件平台所执行的结果不一致
 * <p>
 * Created by zengxf on 2018-11-27
 */
public class TestStrictfp {

    public static void main( String[] args ) {
        testCommon();
        System.out.println( "-------------" );
        testStrictfp();
    }

    static void testCommon() {
        float aFloat = 0.6710339f;
        double aDouble = 0.04150553411984792d;
        double sum = aFloat + aDouble;
        float quotient = (float) ( aFloat / aDouble );

        System.out.println( "float: " + aFloat );
        System.out.println( "double: " + aDouble );
        System.out.println( "sum: " + sum );
        System.out.println( "quotient: " + quotient );
        
        double v = 2.0d - 1.1d;
        System.out.println( v );
    }

    // strictfp // JDK 17 不需要此声明了
    static void testStrictfp() {
        float aFloat = 0.6710339f;
        double aDouble = 0.04150553411984792d;
        double sum = aFloat + aDouble;
        float quotient = (float) ( aFloat / aDouble );

        System.out.println( "float: " + aFloat );
        System.out.println( "double: " + aDouble );
        System.out.println( "sum: " + sum );
        System.out.println( "quotient: " + quotient );
        
        double v = 2.0d - 1.1d;
        System.out.println( v );
    }

}
