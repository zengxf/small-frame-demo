package cn.zxf.test.base.process;

import java.io.IOException;

/**
 * Created by zengxf on 2018/9/11.
 */
public class TestProcessBuilder {

    public static void main(String[] arr) throws IOException {
        String java9 = System.getenv("java_home");
        System.out.println(java9);
        ProcessBuilder pb = new ProcessBuilder()
                .command(java9 + "/bin/java.exe", "-Xmx10m", "-version") // 后面的参数相当于空格隔开
                .inheritIO();
        Process newProcess = pb.start();
        ProcessHandle handle = newProcess.toHandle();
        System.out.println(handle);
    }

}
