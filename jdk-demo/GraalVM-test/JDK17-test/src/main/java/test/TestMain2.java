package test;

import java.util.Arrays;

/**
 * <p/>
 * Created by ZXFeng on 2023/11/22
 */
public class TestMain2 {

    public static void main(String[] args) {
        System.out.printf("args: [%s]%n", Arrays.toString(args));
        String jvmPK = "test.sign";
        System.out.printf("jvm-arg => [-D%s=%s]%n", jvmPK, System.getProperty(jvmPK));

        System.out.printf("Test ----------------%n");
        System.out.printf("中文%n");
        System.out.printf("Test ----------------%n");
        System.out.printf("M2   ----------------%n");
    }

}
