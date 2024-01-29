package test;

import java.util.stream.IntStream;

/**
 * <p/>
 * Created by ZXFeng on 2024/1/29
 */
public class NumUtils {

    public static int sum(int... numArr) {
        return IntStream.of(numArr)
                .sum();
    }

}
