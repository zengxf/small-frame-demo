package test.jdkapi.io.file;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * 测试类加载资源文件
 */
public class TestLoadResource {
    static final String FILE_PATH = "META-INF/MANIFEST.MF";

    public static void main(String[] args) throws IOException {
        loadFile();
    }

    static void loadFile() throws IOException {
        System.out.println("\n");

        // 取得所有资源文件的 URL
        Enumeration<URL> urls = TestLoadResource.class
                .getClassLoader()
                .getResources(FILE_PATH);

        // 遍历所有的 URL
        while (urls.hasMoreElements()) {
            System.out.println("----------------------");
            URL url = urls.nextElement();
            System.out.println(url);
            System.out.println("protocol: " + url.getProtocol());

            InputStream is = url.openStream();
            String text = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            // System.out.println(text);
            System.out.println("length: " + text.length());
        }

        System.out.println("\n");
    }

}
