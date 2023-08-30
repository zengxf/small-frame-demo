package test.jvm.class_structure;

/**
 * 测试本地变量槽位和长度
 * <p>
 * 可用 jclasslib 查看，<br>
 * int, byte，引用和其他类型占用 1 字宽，<br>
 * long, double 占用 2 字宽，<br>
 * 1 字= 4bit
 * 
 * <p>
 * Created by zengxf on 2018-03-09
 */
// M:\project\zxf_super_demo\simple-demo\bin\test\jvm\class_structure
public class TestClassLocalVariable {

    /**
     * long 和 double 占用 2 字宽
     */
    static void testLongDouble( long a1, long a2 ) {
        long b1 = 10, b2 = 11, b3 = 13;
        double d1 = 2.2, d2 = 3.3;
        System.out.println( b1 + b2 + b3 );
        System.out.println( d1 + d2 );
    }

    /**
     * int, byte，引用和其他类型占用 1 字宽
     */
    static void testIntByte( int a1, int a2 ) {
        byte b1 = 10, b2 = 11, b3 = 13;
        float d1 = 2.2f, d2 = 3.3f;
        int[] arr = { 2, 3, 4 };
        System.out.println( b1 + b2 + b3 );
        System.out.println( d1 + d2 );
        System.out.println( arr );
    }

    /**
     * 会替换上面变量的槽位
     */
    static void testVariableIndex() {
        {
            int a1 = 2, a2 = 3;
            System.out.println( a1 + a2 );
        }
        {
            int a1 = 2, a2 = 3, a3 = 3;
            System.out.println( a1 + a2 - a3 );
        }
        int a1 = 2, a2 = 3, a3 = 3, a4 = 3;
        System.out.println( a1 + a2 - a3 - a4 );
    }

}
