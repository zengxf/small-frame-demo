package util;

/**
 * <br/>
 * Created by ZXFeng on 2022/3/3.
 */
public class Print {

    public static void tco(String msg) {
        System.out.printf("[%s]: %s%n", Thread.currentThread().getName(), msg);
    }

}
