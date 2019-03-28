package cn.simple.test.new_features.jdk9.jvm.log;

/**
 * <pre>
 * 查看帮助：
 *     java -Xlog:help
 *
 * 参数：
 *     -Xlog:gc=trace:stdout:level,time,tags
 *
 *     file=jvm%p_%t.log
 *     file=jvm.log::filesize=1M,filecount= 3
 *
 *     -Xlog:gc*=trace:file=gc.log:time
 *     -Xlog:gc*=trace:file=gc.log:none
 *     -Xlog:gc*=trace:file=gc.log:none:filesize=m,filecount=10
 *     -Xlog:gc*=debug,exit*=off
 *     -Xlog:startuptime::hostname,uptime,level,tags
 * </pre>
 * <p>
 * Created by zengxf on 2017/10/9.
 */
public class TestJvmLog {

    public static void main(String[] arr) {
        System.out.println("ok");
    }

}
