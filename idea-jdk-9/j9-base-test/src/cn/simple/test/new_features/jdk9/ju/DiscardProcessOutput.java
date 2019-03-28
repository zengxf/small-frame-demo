package cn.simple.test.new_features.jdk9.ju;

import java.io.IOException;

/**
 * 丢弃子进程的输出
 * <p>
 * Created by zengxf on 2017/10/10.
 */
public class DiscardProcessOutput {

    public static void main(String[] args) {
        System.out.println("Using Redirect.INHERIT:");
        startProcess(ProcessBuilder.Redirect.INHERIT);
        System.out.println("\nUsing Redirect.DISCARD:");
        startProcess(ProcessBuilder.Redirect.DISCARD);
    }

    public static void startProcess(ProcessBuilder.Redirect outputDest) {
        try {
            ProcessBuilder pb = new ProcessBuilder()
                    .command("java", "-version")
                    .redirectOutput(outputDest)
                    .redirectError(outputDest);
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
