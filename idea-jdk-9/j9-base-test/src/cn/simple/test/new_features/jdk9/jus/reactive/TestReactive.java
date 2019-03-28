package cn.simple.test.new_features.jdk9.jus.reactive;

import java.util.stream.IntStream;

/**
 * Created by zengxf on 2017/11/28.
 */
public class TestReactive {

    public static void main(String[] arr) {
        demo_Stream();
    }

    static void demo_Stream() {
        double a = IntStream.rangeClosed(1, 5)
                .peek(System.out::println)
                .filter(i -> i % 2 == 0)
                .peek(System.out::println)
                .mapToDouble(i -> Math.sqrt(i))
                .average()
                .getAsDouble();
        System.out.println(a);
    }

}
