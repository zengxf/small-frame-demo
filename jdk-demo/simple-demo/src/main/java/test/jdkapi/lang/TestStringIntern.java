package test.jdkapi.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 String.intern() <br>
 * intern() 是在字符串常量池中分配 <br>
 * [参考原文](https://www.jianshu.com/p/c14364f72b7e)
 * <p>
 * 
 * "java".intern()
 * 
 * <pre>
 * 常量池中一开始就已经存在"java"字符串，所以和新对象不相等
 * </pre>
 * 
 * Java6
 * 
 * <pre>
 * 而常量池的内存在永久代进行分配，永久代和Java堆的内存是物理隔离的，
 * 会复制当前的引用，并返回，因此不相等
 * </pre>
 * 
 * Java7
 * 
 * <pre>
 * 常量池已经在Java堆上分配内存，
 * 会返回当前的引用，因此相等
 * </pre>
 * 
 * 字节码 ldc
 * 
 * <pre>
 * 也是从常量池里面去获取
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-03-15
 */
public class TestStringIntern {

    public static void main( String[] args ) {
        test1();
        test2();
        // testOOM();
    }

    static void test1() {
        String s1 = new StringBuilder().append( "String" )
                .append( "Test" )
                .toString();
        System.out.println( s1.intern() == s1 );

        String s2 = new StringBuilder().append( "ja" )
                .append( "va" )
                .toString();
        System.out.println( s2.intern() == s2 );
    }

    static void test2() {
        String s1 = new StringBuilder( "漠" ).append( "然" )
                .toString();
        System.out.println( s1.intern() == s1 ); // java7：true，s1.intern() 返回是当前的引用（即：s1）
        // java6：false，永久代会复制一份

        String s2 = new StringBuilder( "漠" ).append( "然" )
                .toString();
        System.out.println( s2.intern() == s2 ); // false：返回的是 s1 的引用
    }

    static void testOOM() {
        // Java6: -XX:PermSize=10M -XX:MaxPermSize=10M
        // Java7: -Xmx20m -Xms20m -XX:-UseGCOverheadLimit // -XX:-UseGCOverheadLimit是关闭GC占用时间过长时会报的异常
        List<String> list = new ArrayList<String>();
        // 无限循环。使用 list 对其引用，保证不被 GC，intern 方法保证其加入到常量池中
        int i = 0;
        while ( true ) {
            list.add( String.valueOf( i++ )
                    .intern() );
        }
    }

}
