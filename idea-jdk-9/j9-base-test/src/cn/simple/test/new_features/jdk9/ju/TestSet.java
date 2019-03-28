package cn.simple.test.new_features.jdk9.ju;

import java.util.Set;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestSet {

    public static void main(String[] arr) {
        Set<String> set = Set.of("a", "b", "c", "d");
        String[] arr1 = "a b c d e f g h".split("\\s+");
        System.out.println(set);
        System.out.println(Set.of(arr1));
        set.add("c");
//        System.out.println(Set.of("a", "b", "a"));
    }

}
