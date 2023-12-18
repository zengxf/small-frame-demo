package cn.simple.test.new_features.jdk9.jxt;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by zengxf on 2017/11/23.
 */
public class TestJavaCompiler {

    public static void main(String[] arr) throws IOException, URISyntaxException {
        URL url = TestJavaCompiler.class.getResource("/resources/JxtHello.java.txt");
        System.out.println("--------------------------");
        Files.readAllLines(Paths.get(url.toURI())).forEach(System.out::println);
        System.out.println("--------------------------");
        InputStream is = url.openStream();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(is, System.out, System.err);
    }

}
