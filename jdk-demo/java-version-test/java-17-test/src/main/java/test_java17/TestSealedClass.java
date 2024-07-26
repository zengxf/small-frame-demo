package test_java17;

/**
 * Java 16 密封类
 * <p/>
 * 命令测试：
 * <pre>
 *     java --enable-preview --source 16 src/main/java/test_java16/TestSealedClass.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class TestSealedClass {

    // public static void main(String[] arr) {
    //     BaseService service = new BService();
    //     service.say();
    // }
    //
    // static abstract sealed class BaseService permits AService, BService {
    //     public void say() {
    //         System.out.println("=========");
    //     }
    // }
    //
    // static class AService extends BaseService {
    //     public void say() {
    //         super.say();
    //         System.out.println("====a====");
    //     }
    // }
    //
    // static class BService extends BaseService {
    //     public void say() {
    //         super.say();
    //         System.out.println("====b====");
    //     }
    // }

}
