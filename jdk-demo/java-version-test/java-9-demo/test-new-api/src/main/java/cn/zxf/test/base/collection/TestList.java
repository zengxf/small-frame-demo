package cn.zxf.test.base.collection;

import java.util.List;

/**
 * Created by zengxf on 2018/9/11.
 */
public class TestList {

    public static void main(String[] arr) {
        System.out.println(List.of(23, 34, 45, 56));
        Integer[] iArr = {100, 200};
        System.out.println(List.of(iArr));
    }

}
