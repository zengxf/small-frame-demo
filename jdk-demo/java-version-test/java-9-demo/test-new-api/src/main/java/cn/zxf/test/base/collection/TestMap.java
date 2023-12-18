package cn.zxf.test.base.collection;

import java.util.Map;

/**
 * Created by zengxf on 2018/9/11.
 */
public class TestMap {

    public static void main(String[] arr) {
        System.out.println(Map.of(23, 34, 45, 56));
        System.out.println(
                Map.ofEntries(
                        Map.entry("a", 32),
                        Map.entry("ab", 323)
                )
        );
    }

}
