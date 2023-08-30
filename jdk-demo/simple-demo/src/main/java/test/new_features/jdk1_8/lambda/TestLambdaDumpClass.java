package test.new_features.jdk1_8.lambda;

import java.util.function.Function;

/**
 * 测试产生的中间类
 * <p>
 *
 * <pre>
 * JVM 命令参数：
 *   -Djdk.internal.lambda.dumpProxyClasses // 默认当前目录
 *   -Djdk.internal.lambda.dumpProxyClasses=dir_path // 指定目录
 * Java 代码设置：
 *   System.setProperty( "jdk.internal.lambda.dumpProxyClasses", "D:/test/dump/java8" );
 *   System.setProperty( "jdk.internal.lambda.dumpProxyClasses", "" );
 * 启动示例：
 *   java -Djdk.internal.lambda.dumpProxyClasses cn.simple.test.new_features.jdk18.lambda.TestDumpClass
 *     -Djdk.internal.lambda.dumpProxyClasses=D:/test/dump/java8
 * 查看私有方法：
 *   javap -p TestLambdaDumpClass.class
 * </pre>
 *
 * @author zengxf
 */
public class TestLambdaDumpClass {
    public static void main(String[] args) {
        System.setProperty("jdk.internal.lambda.dumpProxyClasses", "D:/Data/test/dump/java8");
        // System.setProperty( "jdk.internal.lambda.dumpProxyClasses", "" );
        a();
        b();
        System.gc();
        a();
    }

    static void b() {
        String header = "This is a ";
        Function<Object, String> f = obj -> header + obj.toString();
        String v = f.apply(23);
        System.out.println(v);
    }

    static void a() {
        Runnable run = () -> {
        };
        System.out.println(run);
        run.run();
    }
}
