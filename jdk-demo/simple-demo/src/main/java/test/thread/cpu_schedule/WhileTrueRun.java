package test.thread.cpu_schedule;

import lombok.extern.slf4j.Slf4j;

/**
 * 死循环测试
 * <br/>
 * Windows 10 测不出，单个核 100%
 * <br/>
 * Created by ZXFeng on  2021/11/4.
 */
@Slf4j
public class WhileTrueRun {

    public static void main(String[] args) {
        log.info("Start -----------");
        whileTrue();
    }


    static void whileTrue() {
        int num = 0;
        while (true) {
//            num++;
        }
    }

}
