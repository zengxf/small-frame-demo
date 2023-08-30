package test.jdkapi.reflex.method;

import java.lang.reflect.Method;

import util.SleepUtils;

/**
 * 测试反射调用 15 次后，优化生成的类
 * <p>
 * Created by zengxf on 2019-07-18
 */
public class TestMethodInvocateAsm {

    public static void main( String[] args ) throws Exception {
        // System.setProperty( "jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true" ); // 此参数无用

        SleepUtils.second( 5 );
        Method test = Test.class.getDeclaredMethod( "test" );
        for ( int i = 1; i <= 15 + 2; i++ ) {
            test.invoke( null );
            SleepUtils.second( 1 );
        }
        // 参考：MethodAccessorGenerator#generate()
        // 和 ByteVectorImpl#getData()

        // java.nio.file.Files.write( java.nio.file.Paths.get( "L:\\test.class" ), bytes ); // 此方法没用
        // 用 BTrace 导出
    }

    public static class Test {
        public static int i = 1;

        public static void test() {
            System.out.println( "test == " + i++ );
        }
    }

}
