package test.new_features.jdk1_8.lambda;

/**
 * 测试生成的函数<br>
 * 有 this 引用则生成非静态方法，无 this 则生成静态方法<br>
 * 调用时 Lambda 类对象每次都是新生成的<br>
 * 
 * <p>
 * Created by zengxf on 2018-03-23
 */
// M:\project\zxf_super_demo\simple-demo\bin\test\new_features\jdk1_8\lambda
public class TestLambdaGenerateFunction {

    public static void main( String[] args ) {
        System.setProperty( "jdk.internal.lambda.dumpProxyClasses", "D:/test/dump/java8" );

        new TestLambdaGenerateFunction().test1();
        new TestLambdaGenerateFunction().test1();

        new TestLambdaGenerateFunction().test2();
        new TestLambdaGenerateFunction().test2();

        test3();
        test3();
    }

    private void testPrivate( String a ) {
        System.out.println( "testPrivate -> " + a );
    }

    public void test1() {
        int a = 1;
        String b = "dd";
        Runnable r = () -> {
            System.out.println( b + "\t" + a + "\t" + this );
            this.testPrivate( "test" );
        };
        r.run();
        System.out.println( r + "\n" );
    }

    public void test2() {
        int a = 1;
        String b = "dd";
        Runnable r = () -> {
            System.out.println( b + "\t" + a );
        };
        r.run();
        System.out.println( r + "\n" );
    }

    static void test3() {
        int a = 1;
        String b = "dd";
        Runnable r = () -> {
            System.out.println( b + "\t" + a );
        };
        r.run();
        System.out.println( r + "\n" );
    }

}
