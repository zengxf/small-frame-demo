package cn.simple.test.new_features.jdk9.ju;

import java.util.List;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestList {

    public static void main(String[] arr) {
        List<String> list = List.of("a", "b");
        System.out.println(list);
        String[] arr1 = "a b c d e f g h".split("\\s+");
        System.out.println(List.of(arr1));
        list.add("b");
    }

}
