package test_juc;

/**
 * <p/>
 * Created by ZXFeng on 2024/7/26
 */
public class TempTest {

    public static void main(String[] args) {
        int p = Runtime.getRuntime().availableProcessors();
        System.out.printf("cpus: [%d]%n", p);

        int v = Integer.numberOfLeadingZeros(p - 1);
        System.out.printf("v: [%d]%n", v);

        int i = 1 << (33 - v);
        System.out.printf("i: [%d]%n", i);
    }

}
