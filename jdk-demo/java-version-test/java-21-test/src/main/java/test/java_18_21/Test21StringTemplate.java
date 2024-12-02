package test.java_18_21;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.StringTemplate.RAW;
import static java.lang.StringTemplate.STR;
import static java.util.FormatProcessor.FMT;

/**
 * 字符串模板（预览）
 * <p/>
 * 参考：https://javaguide.cn/java/new-features/java21.html
 * <p/>
 * Created by ZXFeng on 2024/12/2
 */
public class Test21StringTemplate {

    public static void main(String[] args) {
        test3();
        test2();
        test1();
        test0();
    }

    static String f_name = "zxf01";

    // 测试：多行
    static void test0() {
        String time = STR. "The current time is \{
                // 可以写代码块
                DateTimeFormatter
                        .ofPattern("HH:mm:ss")
                        .format(LocalTime.now())
                }." ;
        // 最终输出为：The current time is 16:51:02.
        System.out.println(time);
    }

    // 测试：表达式
    static void test1() {
        int x = 10, y = 20;
        String s = STR. "\{ x } + \{ y } = \{ x + y }" ; // "10 + 20 = 30"
        System.out.println(s);
    }

    // 测试：局部变量、静态/非静态字段、方法
    static void test2() {
        String name = "ZXF";
        String message;

        // variable
        message = STR. "Greetings \{ name }!" ;
        System.out.println("msg: " + message);

        // method
        message = STR. "Greetings \{ getName() }!" ;
        System.out.println("msg: " + message);

        // field
        message = STR. "Greetings \{ f_name }!" ;
        System.out.println("msg: " + message);
    }

    static String getName() {
        return "zxf02";
    }

    // 测试：三种模板处理器
    static void test3() {
        String name = "ZXF";

        // STR
        String message = STR. "姓名：\{ name }." ;
        System.out.println("msg: " + message);

        // FMT
        message = FMT. "姓名：%-5s\{ name }." ;
        System.out.println("msg: " + message);

        // RAW
        StringTemplate st = RAW. "姓名：\{ name }." ;
        message = STR.process(st);
        System.out.println("msg: " + message);
    }

}
