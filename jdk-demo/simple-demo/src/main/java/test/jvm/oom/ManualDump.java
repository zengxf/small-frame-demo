package test.jvm.oom;

import lombok.extern.slf4j.Slf4j;
import util.SleepUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于手动 dump 的测试
 * <br/>
 * JVM param:
 * <pre>
 * 启动
 *     -Xms20m -Xmx20m -XX:+PrintGC
 * Dump(文件在当前项目目录下)
 *     jps
 *     jcmd 22300 GC.heap_dump heap-dump.bin
 * </pre>
 * <br/>
 * Created by ZXFeng on 2022/9/17.
 */
@Slf4j
public class ManualDump {

    static int ti = 0;

    public static void main(String[] args) {
        startOomThread();
    }

    static void startOomThread() {
        new Thread(oom(), "oom-t-" + ti++).start();
    }

    static Runnable oom() {
        List<Integer> oomList = new ArrayList<>();
        return () -> {
            try {
                int i = 0;
                while (true) {
                    oomList.add(i++);
                    if (oomList.size() % 10_0000 == 0)
                        SleepUtils.second(10L);
                }
            } catch (Throwable e) {
                log.error("oom-test 出现异常！msg: [{}]", e.getMessage());
            } finally {
                long seconds = 15L;
                log.info("休息 {} 秒继续 OOM!", seconds);
                SleepUtils.second(seconds);  // 休息几秒

                startOomThread();
            }
        };
    }

}
