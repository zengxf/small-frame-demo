package test.new_features.jdk1_8.lambda;

/**
 * 测试产生的中间类
 * <p>
 *
 * <pre>
 * 查看私有方法：
 *   javap -p TestLambdaDumpSimple.class
 * </pre>
 * <p>
 * <p>
 * 生成的 Lambda 类：
 * <pre>
 * package test.new_features.jdk1_8.lambda;
 *
 * final class TestLambdaDumpSimple$$Lambda$1 implements Runnable {
 *     private TestLambdaDumpSimple$$Lambda$1() {
 *     }
 *
 *     public void run() {
 *          TestLambdaDumpSimple.lambda$a$0();
 *     }
 * }
 * </pre>
 *
 * <p>
 * <br/>
 * Created by ZXFeng on 2023/3/15.
 */
public class TestLambdaDumpSimple {
    public static void main(String[] args) {
        System.setProperty("jdk.internal.lambda.dumpProxyClasses", "D:/Data/test/dump/java8");
        a();
    }

    static void a() {
        Runnable run = () -> {
            System.out.println("------------");
        };
        System.out.println(run);
        run.run();
    }
}
