package test.jdkapi.juc;

import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created by ZXFeng on 2023/2/12.
 */
@Slf4j
public class TestThreadLocal {

    public static void main(String[] args) {
        testInit();
    }

    /*** remove() 之后，会重新调用 initialValue() */
    static void testInit() {
        ThreadLocal<String> tl = ThreadLocal.withInitial(() -> "OK!");
        log.info("get 1: [{}]", tl.get());

        tl.remove();

        log.info("get 1: [{}]", tl.get());
    }

}
