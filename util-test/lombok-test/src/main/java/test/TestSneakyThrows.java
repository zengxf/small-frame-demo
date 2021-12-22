package test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2021/12/22.
 */
@Slf4j
public class TestSneakyThrows {

    public static void main(String[] args) {
        test();
    }

    @SneakyThrows
    public static void test() {
        long t1 = System.currentTimeMillis();
        Thread.sleep(666);
        long t2 = System.currentTimeMillis();
        log.info("test cost {} ms", t2 - t1);
    }

}
