package test.new_features.jdk1_8.ju;

import java.util.Arrays;

/**
 * 提供多个 Java8 的操作
 * 
 * <p>
 * Created by zengxf on 2018-03-08
 */
public class TestArrays {
    static int[] arr = new int[10];

    public static void main( String[] args ) {
        test_fill();
        print();

        test_parallelPrefix();
        print();

        test_setAll();
        print();

        Arrays.parallelSort( arr ); // 并行排序
        print();
    }

    // 设置值
    static void test_setAll() {
        Arrays.setAll( arr, i -> 2 * i );
    }

    // 累加
    static void test_parallelPrefix() {
        Arrays.parallelPrefix( arr, ( i1, i2 ) -> i1 + i2 );
    }

    // 填充相同的值
    static void test_fill() {
        Arrays.fill( arr, 1 );
    }

    static void print() {
        System.out.println( Arrays.toString( arr ) );
    }
}
