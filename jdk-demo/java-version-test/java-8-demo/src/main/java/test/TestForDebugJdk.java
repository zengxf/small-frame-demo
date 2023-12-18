package test;

import java.util.ArrayList;

/**
 * IDEA 调试 JDK，<br/>
 * 参考 1：https://mp.weixin.qq.com/s/MA13SMmJPhX5izNEkohxNg
 * <br/>
 * 参考 2：https://chenxiao.blog.csdn.net/article/details/104369824
 * <br/>
 * Created by ZXFeng on 2021/12/22.
 */
public class TestForDebugJdk {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(10);
        list.add("a");
        list.trimToSize();
        System.out.println("\n\n---------> list: " + list);
        System.out.println("\n\n");
    }

}
