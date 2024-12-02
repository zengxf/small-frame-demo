package test.java_18_21;

/**
 * 中文控制台输出乱码测试：
 * <p/>
 * 用 Gradle (8.11) 构建运行时，会乱码；
 * <br/>
 * 用 IDEA (2023.2.7) 构建运行时，不会乱码。
 * <p/>
 * Created by ZXFeng on 2024/12/2
 */
public class Test21OutZh {

    public static void main(String[] args) {
        System.out.println("-----------");
        System.out.println("中文测试");
        System.out.println("-----------");
    }

}
