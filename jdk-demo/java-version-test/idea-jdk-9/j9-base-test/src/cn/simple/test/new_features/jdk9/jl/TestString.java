package cn.simple.test.new_features.jdk9.jl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <pre>
 *     -XX:-CompactStrings
 *     -XX:+CompactStrings  拼接性能好
 * </pre>
 *
 * Created by zengxf on 2017/11/24.
 */
public class TestString {

    public static void main(String[] arr) {
        long launchTime = System.currentTimeMillis();
        List<String> strings = IntStream.rangeClosed(1, 10_000_000)
                .mapToObj(Integer::toString)
                .collect(Collectors.toList());
        long runTime = System.currentTimeMillis() - launchTime;
        System.out.println("Generated " + strings.size() + " strings in " + runTime + " ms.");

        launchTime = System.currentTimeMillis();
        String appended = strings.stream()
                .limit(100_000)
                .reduce("", (left, right) -> left + right);
        runTime = System.currentTimeMillis() - launchTime;
        System.out.println("Created string of length " + appended.length() + " in " + runTime + " ms.");
    }


}
