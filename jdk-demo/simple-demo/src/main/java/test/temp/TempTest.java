package test.temp;

import lombok.extern.slf4j.Slf4j;

// M:\zxf-demo-github\zxf_super_demo\simple-demo\bin\main\test\temp
@Slf4j
public class TempTest {

    public static void main(String[] args) throws Exception {
        test(10, 10);
        test(10, 3);
        test(2, 3);
    }

    static void test(int a, int b) {
        log.info("\n\n\n---------- a: [{}], b: [{}] ----------", a, b);
        sign:
        if (a > 5) {
            log.info("--------- 1");
            if (b > 5) {
                log.info("--------- 2");
                break sign;
            }
            log.info("--------- 3");
        }
        log.info("--------- 4");
    }

}
