package cn.simple.test.new_features.jdk9.base;

/**
 * Created by zengxf on 2017/10/10.
 */
public class TestSyntax {

    public static void main(String[] arr) {
//        underline();
//        tryWithResources();
//        testInterface();
        System.out.println(TestSyntax.class.getClassLoader());
        System.out.println(TestSyntax.class.getClassLoader().getParent());
    }

    static void testInterface() {
        IUser user = new UserImpl();
        System.out.println(user.isAtEvenPos('b'));
        System.out.println(user.isAtEvenPos('c'));
    }

    static void underline() {
        // 下划线成为关键字
//        int _ = 2;
//        System.out.println(_);
        int __ = 2;
        System.out.println(__);
    }

    static void tryWithResources() {
        MyResource r1 = new MyResource("r1");
        MyResource r2 = new MyResource("r2");
        try (r1; r2) {
            r1.test();
            r2.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        useResource(r1);
    }

    static void useResource(MyResource res) {
        try (res) {
            res.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyResource implements AutoCloseable {
        final String name;

        MyResource(String name) {
            this.name = name;
        }

        @Override
        public void close() throws Exception {
            System.out.println(name + " -> close : " + System.currentTimeMillis());
        }

        void test() {
            System.out.println(name + " test ...");
        }

    }

}
