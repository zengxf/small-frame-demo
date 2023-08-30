package test.jvm.oom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 当出现堆溢出 OOM 时 - 当前线程会挂 - 其他线程还能运行 <br>
 * 原文参考：https://dwz.cn/n9EuRRoJ
 * <p>
 * Created by zengxf on 2019-06-27
 */
// JVM: -Xms16m -Xmx32m
public class HeapOomThreadDead {

    public static void main(String[] args) {
        new Thread(() -> {
            List<byte[]> list = new ArrayList<byte[]>();
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread() + "==");
                byte[] b = new byte[1024 * 1024 * 1];
                list.add(b);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 线程二
        new Thread(() -> {
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread() + "==");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
