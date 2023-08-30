package test.jvm.jdb;

/**
 * 多线程测试
 * 
 * <p>
 * Created by zengxf on 2017-10-23
 * 
 * @see TestJdb.Doc
 * @see Doc
 */
public class TestThreads {
    public static void main( String[] args ) {
        Runnable a = () -> {
            log( "test 1 entry ..." );
            sleep( 1000 );
            log( "test 1 end !!!" );
        };
        Runnable b = () -> {
            log( "test 2 entry ..." );
            sleep( 1000 );
            log( "test 2 end !!!" );
        };
        new Thread( a, "test-jdb-a" ).start();
        new Thread( b, "test-jdb-b" ).start();
    }

    static void log( String msg ) {
        System.out.println( "\n\n" + msg + "\n\n" );
    }

    static void sleep( int millis ) {
        try {
            Thread.sleep( millis );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    /**
     * 用 jdb 调试
     * <p>
     * 
     * 命令
     * 
     * <pre>
     * bin> jdb -sourcepath ../src cn.simple.test.jvm.jdb.TestThreads      # 可用于查看源码
     * stop at cn.simple.test.jvm.jdb.TestThreads:15    # 行，类要全名
     * run          # 开始运行
     * next         # 下一行(F6)
     * step         # 进入方法体(F5)
     * step up      # 跳出方法体(F7)
     * cont         # 运行到下一个断点(F8)
     * list         # 查看源码
     * -- 线程有关
     * threads [threadgroup]     -- 列出线程
     * thread {thread id}        -- 设置默认线程
     * suspend [thread id(s)]    -- 挂起线程 (默认值: all)
     * resume [thread id(s)]     -- 恢复线程 (默认值: all)
     * threadgroups              -- 列出线程组
     * threadgroup {name}        -- 设置当前线程组
     * -- 调试建议
     * 1) 先暂停所有的线程
     * 2) 恢复某个要调试的线程
     * 3) 调试完，如果要调试另一个线程：则用 thread {tId} 跳转
     * </pre>
     * 
     * <p>
     * Created by zengxf on 2017-10-23
     * 
     * @see TestJdb.Doc
     */
    static class Doc {
    }
}
