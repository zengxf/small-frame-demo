package test;

/**
 * 测试 Java11 字符优化后的内存占用情况
 * <pre>
 * jmap -histo:live $pid | head -n 10
 * jmap -histo:live $pid | more
 * </pre>
 * Created by ZXFeng on 2022/4/1.
 */
public class TestJava11String {

    public static void main(String[] args) throws InterruptedException {
        String en = "abc";
        String zh = "中文一";
        String mix = "中abc";
        System.out.println(en);
        System.out.println(zh);
        System.out.println(mix);

        String s1 = en.substring(0, 1);
        System.out.println(s1);

        s1 = zh.substring(0, 1);
        System.out.println(s1);

        s1 = mix.substring(0, 1);
        System.out.println(s1);

        // 相当于是 U+1D4D1
        String u16 = "\uD835\uDCD1";    // https://www.compart.com/en/unicode/U+1D4D1
        System.out.println(u16.length());
        System.out.println(u16.substring(0, 1));

        // Thread.sleep(1800_000L);
    }

}
