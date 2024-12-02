package test.java_9_17;

import java.util.ArrayList;
import java.util.List;

/**
 * Java 10 堆分配
 * <p>
 * 参考：https://jiaojianbo.github.io/2019/02/21/java10-new-featrue/
 * <p>
 * 命令测试（使用内存太小，测不出）：
 * <pre>
 *     java -XX:AllocateHeapAt=L:\test src/main/java/test_java16/TestHeapAllocation.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class Test10HeapAllocation {

    public static void main(String[] arr) {
        List<Double> list = new ArrayList<>(1024);
        while (true) {
            list.add(Math.random());
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
            }
            if (list.size() == 1024) {
                list.clear();
                System.out.println("----");
            }
        }
    }

}
