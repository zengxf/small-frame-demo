package test.jvm.jit;

import test.jdkapi.juc.test_volatile.TestVolatileAsm;

/**
 * hsdis 配置参考 {@link TestVolatileAsm} <br>
 * [原文参考1](http://blog.csdn.net/hengyunabc/article/details/26898657) <br>
 * [原文参考2](http://www.cnblogs.com/stevenczp/p/7975776.html) <br>
 * [工具源码](https://github.com/AdoptOpenJDK/jitwatch)
 * <p>
 * 
 * 工具启动
 * 
 * <pre>
 * // maven 要跳过测试
 * mvn clean compile 
 * mvn package -Dmaven.test.skip=true 
 * mvn install -Dmaven.test.skip=true 
 * mvn exec:java
 * // gradle 也要跳过测试
 * gradlew clean build run
 * </pre>
 * 
 * 测试类启动参数
 * 
 * <pre>
 * -server -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading  -XX:+PrintAssembly -XX:+LogCompilation -XX:LogFile=jit.log
 * </pre>
 * 
 * 配置
 * 
 * <pre>
 * 1) 点击"Open Log"，配置日志文件： M:\project\zxf_super_demo\simple-demo\jit.log
 * 2) 点击"Config"，
 *      配置源目录：              M:\project\zxf_super_demo\simple-demo\src
 *      和配置 class 目录： M:\project\zxf_super_demo\simple-demo\bin
 * 3) 点击"Start"，开始分析
 * </pre>
 * 
 * <p>
 * Created by zengxf on 2018-03-16
 */
public class TestJITWatch {

    public volatile long sum = 0;

    public int add( int a, int b ) {
        int temp = a + b;
        long sums = sum;
        sums += temp;
        sum = sums;
        return temp;
    }

    public static void main( String[] args ) {
        TestJITWatch test = new TestJITWatch();

        int sum = 0;

        for ( int i = 0; i < 1000000; i++ ) {
            sum = test.add( sum, 1 );
        }

        System.out.println( "Sum:" + sum );
        System.out.println( "Test.sum:" + test.sum );
    }

}
