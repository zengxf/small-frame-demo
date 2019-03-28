package cn.simple.test.new_features.jdk9.ju;

import java.util.Map;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestMap {

    public static void main(String[] arr) {
        Map<String, Integer> map = Map.of("a", 1);
        System.out.println(map);
        map = Map.of("a", 1, "c", 3, "b", 2);
        System.out.println(map);
    }

}
