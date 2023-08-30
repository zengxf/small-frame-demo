package test.new_features.jdk1_8.stream;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 并行流对拥有大量输入元素的数据流具有极大的性能提升。但是要记住一些
 * 并行流的操作，例如 reduce 和 collect 需要额外的计算（组合操作） ，这在串
 * 行执行时并不需要
 * </pre>
 *
 * <p>
 * Created by zxf on 2017-07-17
 */
@Slf4j
public class TestStream {
    public static void main(String[] args) {
        // test_generate();
        // test_iterate();
        // test_multilevelGrouping();
        // test_collectingAndThen();
        // test_partitioningBy();
        // test_toArray();
        List.of(1, 2, 3, 4, 5, 6)
                .parallelStream()
                .forEach(i -> log.info("i: [{}]", i));
    }

    static Supplier<Stream<User>> sup = () -> Stream.of( //
            new User("gz", 12, "zsf"), //
            new User("gz", 12, "bb"), //
            new User("gz", 23, "cc"), //
            new User("sy", 22, "er33"), //
            new User("sy", 22, "ds") //
    );

    // 测试：转数组
    static void test_toArray() {
        Stream<User> stream = sup.get();
        User[] users = stream.toArray(length -> {
            System.out.println("length = " + length);
            return new User[length];
        });
        System.out.println(users.length);
    }

    // 以布尔值分组
    static void test_partitioningBy() {
        Stream<User> stream = sup.get();
        Map<?, ?> booleanMap = stream.collect(Collectors.partitioningBy(u -> u.getAge() > 15));
        log.info("booleanMap: {}", booleanMap);
        System.out.println("----------");

        // 分组后再统计
        stream = sup.get();
        booleanMap = stream.collect(Collectors.partitioningBy(u -> u.getAge() > 15, //
                Collectors.counting()));
        log.info("booleanMap: {}", booleanMap);
        System.out.println("----------");

        // 分组后再分组
        stream = sup.get();
        booleanMap = stream.collect(Collectors.partitioningBy(u -> u.getAge() > 15, //
                Collectors.groupingBy(User::getCity)));
        log.info("booleanMap: {}", booleanMap);
        System.out.println("----------");
    }

    static void test_multilevelGrouping() {
        // 先用 city 分组，再用 age 分组
        Stream<User> stream = sup.get();
        Map<String, Map<Integer, List<User>>> cityMap = //
                stream.collect(Collectors.groupingBy(User::getCity, Collectors.groupingBy(User::getAge)));
        cityMap.forEach((k, v) -> {
            log.info("k: {}, v: {}", k, v);
        });

        // 分组再统计
        stream = sup.get();
        Map<String, Long> countMap = stream.collect(Collectors.groupingBy(User::getCity, Collectors.counting()));
        log.info("countMap: {}", countMap);
    }

    static void test_collectingAndThen() {
        // 分组后的组数
        Stream<User> stream = sup.get();
        Integer mapLen = stream.collect(Collectors.collectingAndThen(Collectors.groupingBy(User::getCity), map -> map.size()));
        log.info("mapLen: {}", mapLen);
    }

    static void test_iterate() {
        // 0 2 4 ...
        Stream.iterate(0, n -> n + 2) //
                .limit(3) //
                .forEach(System.out::println);
        System.out.println("------");

        // 1 1 2 3 5 8 ...
        Stream.iterate(new int[]{1, 1}, t -> new int[]{t[1], t[0] + t[1]}) //
                .limit(6)//
                .map(t -> t[0])//
                .forEach(System.out::println);
        System.out.println("------");
    }

    static void test_generate() {
        Stream.generate(Math::random) //
                .limit(3) //
                .forEach(System.out::println);
        System.out.println("------");
    }

    @Data
    @ToString(includeFieldNames = false)
    @AllArgsConstructor
    static class User {
        String city;
        Integer age;
        String name;
    }

}
