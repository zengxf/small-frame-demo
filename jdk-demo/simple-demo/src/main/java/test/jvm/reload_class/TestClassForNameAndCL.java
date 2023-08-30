package test.jvm.reload_class;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 测试 Class.forName 和 ClassLoader 区别 <br>
 * 参考：https://blog.csdn.net/qq_27093465/article/details/52262340
 * <p>
 * Created by zengxf on 2020-06-04
 */
public class TestClassForNameAndCL {

    static String className = "test.jvm.reload_class.TestLoadOrder";

    public static void main( String[] args ) throws Exception {
        // test_forName();
        testClassloader();
    }

    static void test_forName() throws ClassNotFoundException {
        Class<?> clazz = Class.forName( className );
        System.out.println( clazz );
    }

    static void testClassloader() throws Exception {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class<?> clazz = loader.loadClass( className );
        System.out.println( clazz );

        Constructor<?>[] cs = clazz.getConstructors();
        System.out.println( Arrays.toString( cs ) );

        Method[] ms = clazz.getDeclaredMethods();
        System.out.println( Arrays.toString( ms ) );
    }

}
