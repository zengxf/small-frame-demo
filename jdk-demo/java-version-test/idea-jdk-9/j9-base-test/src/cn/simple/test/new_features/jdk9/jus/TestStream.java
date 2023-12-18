package cn.simple.test.new_features.jdk9.jus;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestStream {

    public static void main(String[] arr) {
//        ofNullable();
//        iterate();
        dropWhileAndTakeWhile();
    }

    public static void dropWhileAndTakeWhile() {
        List<Integer> list = List.of(1, 3, 5, 4, 6, 7, 8, 9);
        System.out.println("Original Stream: " + list);

        List<Integer> list2 = list.stream()
                .dropWhile(n -> n % 2 == 1)
                .collect(Collectors.toList());
        System.out.println("After using dropWhile(n -> n % 2 == 1): " + list2);

        List<Integer> list3 = list.stream()
                .takeWhile(n -> n % 2 == 1)
                .collect(Collectors.toList());
        System.out.println("After using takeWhile(n -> n % 2 == 1): " + list3);
    }

    static void iterate() {
        List<Integer> list = Stream.iterate(1, n -> n <= 10, n -> n + 1)
                .collect(Collectors.toList());
        System.out.println("Integers from 1 to 10: " + list);
    }

    static void ofNullable() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, null);
        map.put(4, "four");

        Set<String> nonNullValues = map.entrySet()
                .stream()
                .flatMap(e -> Stream.ofNullable(e.getValue()))
                .collect(Collectors.toSet());

        System.out.println(nonNullValues);
    }

}
