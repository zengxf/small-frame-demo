package test.java_9_17;

/**
 * Java 16 密封类
 * <p/>
 * 命令测试：
 * <pre>
 *     java --enable-preview --source 16 src/main/java/test_java16/TestSealedClass.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class Test16SealedClass {

    public static void main(String[] arr) {
        BaseService bService = new BService();
        bService.say();

        System.out.println("------------------");

        BaseService aaService = new AAService();
        aaService.say();
    }


    // ------------------------

    static abstract sealed class BaseService permits AService, BService {
        public void say() {
            System.out.println("==== X ====");
        }
    }


    // ------------------------

    static non-sealed class AService extends BaseService {
        public void say() {
            super.say();
            System.out.println("==== A ====");
        }
    }

    static class AAService extends AService {
        public void say() {
            super.say();
            System.out.println("==== AA ====");
        }
    }


    // ------------------------

    static final class BService extends BaseService {
        public void say() {
            super.say();
            System.out.println("==== B ====");
        }
    }

}
