package test.jdkapi.list;

import java.util.ArrayList;

/**
 * trimToSize(); // 清空 list 内部数组尾部为 null 的元素，以节省空间
 *
 * <p>
 * Created by zengxf on 2017-09-29
 */
public class TestArrayList {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(10);
        list.add("a");
        list.trimToSize();
        System.out.println(list);
    }
}
