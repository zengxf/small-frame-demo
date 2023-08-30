package test.jvm.jmm;

import java.util.stream.IntStream;

import lombok.Data;
import util.SleepUtils;

/**
 * 测试 final 域会不会未正确初始化 <br/>
 * 参考：https://blog.csdn.net/j3T9Z7H/article/details/106204652
 * <p>
 * Created by zengxf on 2018-02-24
 */
public class TestFinal {

    public static void main(String[] args) throws Exception {
        Runnable newRun = () -> {
            new TestFinalInner(10);
        };

        Runnable readRun = () -> {
            TestFinalInner a = TestFinalInner.instance;
            System.out.println(Thread.currentThread().getName() + " ----> " + a);
        };

        IntStream.range(1, 10).forEach(i -> {
            new Thread(readRun, "test-" + i).start();
        });

        new Thread(newRun, "test-init").start();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            System.out.println("==== main new ----> " + new TestFinalInner(i));
        });
        System.out.println("==== main ----> " + TestFinalInner.instance);
    }

    @Data
    static class TestFinalInner {
        final int final_i;
        int common_i;
        int[] arr;
        static volatile TestFinalInner instance;

        TestFinalInner(int fi) {
            System.out.println(Thread.currentThread().getName() + " 正在 new 实例化");
            instance = this;
            SleepUtils.second(1); // 模拟长时操作
            final_i = fi;
            common_i = 1;
            arr = IntStream.range(1, 10).toArray();
        }
    }

}
