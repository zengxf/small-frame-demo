package test.java_9_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Java 9 try 增强
 * <p>
 * 参考：https://www.runoob.com/java/java9-try-with-resources-improvement.html
 * <p>
 * 命令测试：
 * <pre>
 *     java src/main/java/test_java16/TestTryImprove.java
 * </pre>
 * Created by zengxf on 2021/3/30.
 */
public class Test9TryImprove {

    public static void main(String[] args) throws IOException {
        System.out.println(readData("test"));
    }

    static String readData(String message) throws IOException {
        Reader inputString = new StringReader(message);
        BufferedReader br = new BufferedReader(inputString);
        try (br) {
            return br.readLine();
        }
    }

}
