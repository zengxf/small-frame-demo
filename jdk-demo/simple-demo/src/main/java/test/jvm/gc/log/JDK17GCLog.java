package test.jvm.gc.log;

import lombok.extern.slf4j.Slf4j;
import util.SleepUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 JDK 17 GC 日志 <br/>
 * <b>要先创建 logs 文件夹，要不然启动不了</b>
 * <pre>
 *     -XX:+PrintVMOptions              // 打印显式参数
 *     -XX:+PrintCommandLineFlags       // 打印显式隐式参数
 *     -Xms20m -Xmx20m                  // 易于 OOM
 *     -XX:+PrintGC                     // 简单 GC 日志
 *     -XX:+PrintGCDetails              // 更详细的 GC 日志
 *     -Xloggc:logs/test-1-gc.log       // 设置文件
 *     -XX:+PrintGCDateStamps           // 加上日期和时间（JDK 17 不支持）
 * </pre>
 * 新参数，参考：<br/>
 * https://stackoverflow.com/questions/54144713/is-there-a-replacement-for-the-garbage-collection-jvm-args-in-java-11
 * <br/>
 * Java 17 升级指南： https://juejin.cn/post/7117531586232320031#heading-6 <br/>
 * <pre>
 *     -XX:+PrintGCTimeStamps
 *     -XX:+PrintGCDateStamps    ==>  decoration options
 *                                    -Xlog:::time,level,tags
 *
 *     -XX:+PrintGCDetails       ==>  -Xlog:gc*
 *
 *     -XX:+UseGCLogFileRotation
 *     -XX:NumberOfGCLogFiles
 *     -XX:GCLogFileSize          ==>  output options
 *                                     -Xlog::::filecount=5,filesize=128M
 *
 * 总：--------
 *     -Xlog:gc*:file=logs/test-2-gc.log
 *     -Xlog:gc*=debug:file=logs/test-3-gc.log
 *     -Xlog:gc*,safepoint:logs/m-gc.log:time,uptime:filecount=10,filesize=1K
 * </pre>
 * <br/>
 * Created by ZXFeng on 2022/9/17.
 */
@Slf4j
public class JDK17GCLog {

    static int ti = 0;

    public static void main(String[] args) {
        new Thread(sysGc10s(), "oom-t-" + ti++).start();
        startOomThread();
        startErrThread();
    }

    static void startOomThread() {
        new Thread(oom(), "oom-t-" + ti++).start();
    }

    static void startErrThread() {
        new Thread(err(), "err-t-" + ti++).start();
    }

    static Runnable err() {
        return () -> {
            try {
                long seconds = 20L;
                log.info("暂停 {}s 退出！", seconds);
                SleepUtils.second(seconds);
                throw new OutOfMemoryError("Test-OOM");
            } catch (Throwable e) {
                log.error("出现异常！msg: [{}]", e.getMessage());
            } finally {
                startErrThread(); // 不间断启动
            }
        };
    }

    // 每 10 秒 GC 一次
    static Runnable sysGc10s() {
        return () -> {
            while (true) {
                System.gc();
                SleepUtils.second(10L);
            }
        };
    }

    // OOM
    static Runnable oom() {
        List<Integer> oomList = new ArrayList<>();
        return () -> {
            try {
                int i = 0;
                while (true) {
                    oomList.add(i++);
                    if (oomList.size() % 10_0000 == 0)
                        SleepUtils.second(1L);
                }
            } catch (Throwable e) {
                log.error("oom-test 出现异常！msg: [{}]", e.getMessage());
            } finally {
                oomList.clear();                // 清空继续

                long seconds = 5L;
                log.info("休息 {} 秒继续 OOM!", seconds);
                SleepUtils.second(seconds);  // 休息几秒

                startOomThread();
            }
        };
    }

}
