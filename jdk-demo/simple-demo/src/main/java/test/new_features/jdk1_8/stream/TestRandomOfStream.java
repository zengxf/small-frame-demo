package test.new_features.jdk1_8.stream;

import java.util.Random;

public class TestRandomOfStream {
    public static void main(String[] args) {
        Random r = new Random();

        r.ints(10, 10, 20)
                .forEach(System.out::println);

        System.out.println("----------------");
        r.doubles(10, 10.0D, 20.0D)
                .forEach(System.out::println);

        System.out.println("----------------");
        r.longs(10, Long.MAX_VALUE - 20L, Long.MAX_VALUE - 10L)
                .forEach(System.out::println);
    }
}
