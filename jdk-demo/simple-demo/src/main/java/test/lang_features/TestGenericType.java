package test.lang_features;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 测试泛型(桥接方法)<br>
 * 编译时类型擦除，相差类型自动转换<br>
 * 可反编译查看
 */
// M:\project\zxf_super_demo\simple-demo\bin\main\test\lang_features
public class TestGenericType {

    public interface Fun {
        void test();
    }

    public static class ListUtils {
        public static < T > T one( List<T> list ) {
            if ( list == null || list.isEmpty() )
                return null;
            return list.get( 0 );
        }
    }

    public static class MyFun implements Fun, Runnable {
        @Override
        public void run() {
        }

        @Override
        public void test() {
        }
    }

    public < T > void test1( T t ) {
    }

    public < T extends Runnable > void test1( T t ) {
    }

    public < T extends Fun & Runnable > void test1( T t ) {
        t.test();
        t.run();
    }

    public void testList() {
        List<String> list = new ArrayList<>();
        list.add( "" );
        String a = list.get( 0 );
        a.isEmpty();
        a = ListUtils.one( list );
    }

    public static class MyFunction implements Function<String, Integer> {
        @Override
        public Integer apply( String t ) {
            return null;
        }
    }

}
