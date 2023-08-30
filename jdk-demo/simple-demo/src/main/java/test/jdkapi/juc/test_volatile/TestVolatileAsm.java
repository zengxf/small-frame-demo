package test.jdkapi.juc.test_volatile;

/**
 * 测试查看 volatile 的汇编<br>
 * [原文参考](http://blog.csdn.net/kisimple/article/details/51526034)<br>
 * 
 * 查看 CPU 架构
 * 
 * <pre>
 * Windows： echo %PROCESSOR_ARCHITECTURE%; echo %PROCESSOR_IDENTIFIER%
 * Linux: uname -a
 * </pre>
 * 
 * 启动 VM 参数
 * 
 * <pre>
 * -server -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:CompileCommand=compileonly,*Asm.success
 * 参数说明：
 * -Xcomp：强制由 JIT 编译器在第一次使用方法时编译方法。不推荐使用此选项；请使用 -Xjit:count=0 代替。
 * -XX:CompileCommand=compileonly,xx：只编译指定的方法
 * </pre>
 * 
 * 问题和解决
 * 
 * <pre>
 * 问题：Could not load hsdis-amd64.dll(或 hsdis-amd64.dll);
 * Windows 解决：
 * 1) 下载[hsdis-amd64.dll](https://sourceforge.net/projects/fcml/files/fcml-1.1.1/hsdis-1.1.1-win32-amd64.zip/download)，
 * 2) 将文件放到 %JAVA_HOME%\jre\bin\server 目录下
 * Linux 解决：
 * 1) 下载[hsdis-amd64.so](http://dl.iteye.com/topics/download/723b6bff-e85e-39e5-929e-45585041bbaf)，
 * 2) 将文件放到 $JAVA_HOME/jre/lib/amd64/server 目录下
 * </pre>
 * 
 * 查找 lock 指令
 * 
 * <p>
 * Created by zengxf on 2018-03-02
 */
public class TestVolatileAsm {

    private static volatile boolean success;

    public static void success() {
        success = true;
    }

    public static boolean isNotSuccess() {
        return !success;
    }

    public static void main( String[] args ) throws Exception {
        success();
        System.out.println( isNotSuccess() );
    }

}
