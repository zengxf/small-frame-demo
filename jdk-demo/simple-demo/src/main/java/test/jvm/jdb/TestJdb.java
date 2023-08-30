package test.jvm.jdb;

import java.lang.management.ManagementFactory;

/**
 * Created by zengxf on 2017-10-20
 * 
 * @see Doc
 */
public class TestJdb {

    public static void main( String[] args ) {
        System.out.println( ManagementFactory.getRuntimeMXBean().getName() );

        int i = 5;
        int j = 6;
        int sum = add( i, j );
        System.out.println( "add sum: " + sum );

        A a = new A();
        System.out.println( a );

        sum = 0;
        for ( i = 0; i < 100; i++ )
            sum += i;

        System.out.println( "总和: " + sum );
    }

    public static int add( int augend, int addend ) {
        int sum = augend + addend;
        return sum;
    }

    static class A {
        B b = new B();
    }

    static class B {
        String a = "test 峰";
    }

    /**
     * 用 jdb 调试
     * <p>
     * 
     * 命令
     * 
     * <pre>
     * simple-demo> javac -g -d bin src\test\jvm\jdb\TestJdb.java
     * > cd bin
     * > jdb cn.simple.test.jvm.jdb.TestJdb
     * > jdb -sourcepath ../src cn.simple.test.jvm.jdb.TestJdb      # 可用于查看源码
     * help         # 查看帮助信息
     * stop at cn.simple.test.jvm.jdb.TestJdb:15        # 行，类要全名
     * stop in cn.simple.test.jvm.jdb.TestJdb.main      # 方法
     * run          # 开始运行
     * next         # 下一行(F6)
     * step         # 进入方法体(F5)
     * step up      # 跳出方法体(F7)
     * cont         # 运行到下一个断点(F8)
     * stepi        # 下一条指令
     * print i      # 打印变量的值
     * locals       # 查看所有局部变量
     * dump i       # 输出对象信息，可展开对象里面字段的值，只显示当前的
     * list         # 查看源码
     * clear class:line   # 清除行中的断点
     * clear        # 列出断点，stop 也是
     * </pre>
     * 
     * <p>
     * Created by zengxf on 2017-10-20
     */
    static class Doc {
    }
}
