package test.lang_features;

/**
 * 测试方法标记<br>
 * 实现泛型时会生成桥接方法
 * 
 * <p>
 * Created by zengxf on 2018-03-23
 */
// M:\project\zxf_super_demo\simple-demo\bin\test\illustration
public class TestMethodFlags {

    public static interface IFunction< T, R > {
        R apply( T t );
    }

    public static class MyFun implements IFunction<String, Integer> {

        @Override
        public Integer apply( String t ) {
            return 1;
        }

    }

    public static void main( String[] args ) {
        MyFun fun = new MyFun();
        fun.apply( "test" );
    }

}
