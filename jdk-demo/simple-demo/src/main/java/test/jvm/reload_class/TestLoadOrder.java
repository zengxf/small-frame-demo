package test.jvm.reload_class;

import lombok.extern.slf4j.Slf4j;

/**
 * 反编译可看到：普通块和成员变量初始化都在构造器里面<br>
 * 静态变量初始化在静态块里面<br>
 * 顺序是：静态变量 -> 静态块 -> 成员变量 -> 普通块 -> 构造器<br>
 * <p>
 * Created by zengxf on 2020-06-04
 */
@Slf4j
public class TestLoadOrder {

    private int              a  = 2;
    private int              b;
    private int              c;
    private final int        d;
    private static int       sa = 10;
    private static int       sb;
    private static final int sc;

    static {
        sb = 20;
        sc = 33;
        log.info( "静态块 1" );
        log.info( "{},{},{}", sa, sb, sc );
    }
    static {
        sa = 110;
        sb = 30;
        log.info( "静态块 2" );
        log.info( "{},{},{}", sa, sb, sc );
    }
    {
        b = 3;
        d = 10;
        log.info( "普通块 1" );
        log.info( "{},{},{},{}", a, b, c, d );
    }
    {
        log.info( "普通块 2" );
        log.info( "{},{},{},{}", a, b, c, d );
    }

    public TestLoadOrder() {
        c = 44;
        log.info( "构造器" );
        log.info( "{},{},{},{}", a, b, c, d );
    }

    public static void main( String[] args ) throws Exception {
        TestLoadOrder.class.getClass();
        log.info( "------------" );
        new TestLoadOrder();
    }

}
