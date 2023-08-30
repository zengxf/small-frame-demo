package test.jvm.cpu;

import util.SleepUtils;

/**
 * 高 CPU 占用测试
 * <br/>
 * ref: https://mp.weixin.qq.com/s/pmPQFg38JKHYDYAN9osoMQ
 * <br/>
 * Created by ZXFeng on 2022/9/16.
 */
public class HighCpuUsageTest {

    public static void main(String[] args) {
        var obj = new Object() {
            int low = 0;
            int high = 0;
        };

        Runnable lowCpu = () -> {
            while (true) {
                SleepUtils.millisecond(500L);
                obj.low++;
            }
        };
        Runnable highCpu = () -> {
            while (true) {
                obj.high++;
                if (obj.high == Integer.MAX_VALUE)
                    obj.high = 1;
            }
        };

        new Thread(lowCpu, "Low-cpu-thread").start();
        new Thread(highCpu, "High-cpu-thread").start();
    }

}
