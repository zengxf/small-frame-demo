package test;

/**
 * 测试 Java8 字符没优化时的内存占用情况
 * <pre>
 * jmap -histo:live $pid | head -n 10
 * jmap -histo:live $pid | more
 * </pre>
 * Created by ZXFeng on 2022/4/1.
 */
public class TestJava8String {

    public static void main(String[] args) throws InterruptedException {
        String en = "abc";
        String zh = "中文";
        System.out.println(en);
        System.out.println(zh);
        Thread.sleep(1800_000L);
    }

}
