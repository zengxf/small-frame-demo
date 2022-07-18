package test.util;

import java.util.concurrent.locks.LockSupport;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/18.
 */
public class ThreadUtil {

    public static void sleepSeconds(int second) {
        LockSupport.parkNanos(second * 1000L * 1000L * 1000L);
    }

}
