package test_java17;

import java.time.DayOfWeek;

/**
 * Java 12 Switch 表达式增强
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestSwitchImprove.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class TestSwitchImprove {

    public static void main(String[] arr) {
//        DayOfWeek day = LocalDate.now().getDayOfWeek();
        DayOfWeek day = DayOfWeek.MONDAY;
        switch (day) {
            case FRIDAY -> System.out.println("friday");
            case MONDAY, SATURDAY -> {
                System.out.println("1, 6");
            }
            default -> System.out.println("==");
        }
    }

}
