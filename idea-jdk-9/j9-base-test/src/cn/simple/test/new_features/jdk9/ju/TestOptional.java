package cn.simple.test.new_features.jdk9.ju;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by zengxf on 2017/10/10.
 */
public class TestOptional {

    public static void main(String[] arr) {
        List<Optional<Integer>> optionalList = List.of(Optional.of(1),
                Optional.empty(),
                Optional.of(2),
                Optional.empty(),
                Optional.of(3));

        // ifPresentOrElse
        optionalList.stream()
                .forEach(p -> p.ifPresentOrElse(System.out::println,
                        () -> System.out.println("Empty")));
        System.out.println("------------");

        // or
        optionalList.stream()
                .map(p -> p.or(() -> Optional.of(0)))
                .forEach(System.out::println);
        System.out.println("------------");


        // stream
        // java8
        List<Integer> list8 = optionalList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        System.out.println(list8);
        // java9 stream
        List<Integer> list9 = optionalList.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        System.out.println(list9);
    }

}
