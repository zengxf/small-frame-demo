package cn.simple.test.new_features.jdk9.jur;

/**
 * Created by zengxf on 2017/11/3.
 */
public class TestBaseRegex {

    public static void main(String[] arr) {
        System.out.println( "(?<!\\d)\\.(?!\\d): " + "2.3".matches( "(?<!\\d)\\.(?!\\d)" ) );
        System.out.println( "(?<!\\d)\\.(?!\\d): " + "a.b".matches( "(?<!\\d)\\.(?!\\d)" ) );
    }

}
