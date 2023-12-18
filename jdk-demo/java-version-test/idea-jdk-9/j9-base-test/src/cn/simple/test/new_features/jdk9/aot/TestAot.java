package cn.simple.test.new_features.jdk9.aot;

import java.util.ArrayList;
import java.util.List;

/**
 * AOT 测试——当前只有 Linux 才有
 * <pre>
 *
 * javac -encoding UTF-8 TestAot.java
 *
 * rm -rf cn
 * mkdir -p cn/simple/test/new_features/jdk9/aot
 * cp TestAot.class cn/simple/test/new_features/jdk9/aot/
 * jaotc --output libTestAot.so --module java.base cn/simple/test/new_features/jdk9/aot/TestAot.class
 *
 * java cn.simple.test.new_features.jdk9.aot.TestAot
 * java -XX:AOTLibrary=./libTestAot.so cn.simple.test.new_features.jdk9.aot.TestAot
 * java -XX:AOTLibrary=./libTestAot.so -XX:+PrintAOT cn.simple.test.new_features.jdk9.aot.TestAot
 * </pre>
 * <p>
 * Created by zengxf on 2017/11/27.
 */
public class TestAot {

    public static void main(String[] arr) {
        System.out.println("------------------------");
        long start = System.nanoTime();
        long test = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            test++;
        }
        System.out.println("test++ use time: \t" + (System.nanoTime() - start) + " ns, test: " + test);
        System.out.println("------------------------");

        start = System.nanoTime();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5000_0000; i++) {
            list.add(i);
            list.clear();
        }
        System.out.println("list.add use time: \t" + (System.nanoTime() - start) + " ns, list.size: " + list.size());
        System.out.println("------------------------");

        System.out.println("Hello, World! -- by aot");
        System.out.println("------------------------");
    }

}
