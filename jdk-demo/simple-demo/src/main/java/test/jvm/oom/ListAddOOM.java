package test.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * List 不停添加元素 OOM (方便 MAT 分析)
 * <br/>
 * JVM param:
 * <pre>
 * -Xms20m -Xmx20m -XX:+PrintGC
 * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=list-heap.bin
 * </pre>
 * <br/>
 * Created by ZXFeng on 2022/9/16.
 */
public class ListAddOOM {

    public static void main(String[] args) {
        List<Integer> okIntList = new ArrayList<>();
        List<Integer> oomIntList = new ArrayList<>();

        Runnable okRun = () -> {
            int i = 0;
            while (true) {
                okIntList.add(i++);
                if (okIntList.size() > 1000)
                    okIntList.clear();
            }
        };
        Runnable oomRun = () -> {
            int i = 0;
            while (true) {
                oomIntList.add(i++);
            }
        };

        new Thread(okRun, "OK--thread").start();
        new Thread(oomRun, "OOM--thread").start();
    }

}
