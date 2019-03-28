package cn.zxf.test.base.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

/**
 * Created by zengxf on 2018/9/11.
 */
public class TestResource {

    public static void main(String[] arr) throws IOException {
        URL url1 = TestResource.class.getResource("TestResource.class");
        System.out.println(url1);
        URL url2 = TestResource.class.getResource("/java/lang/Object.class"); // 不同模块
        System.out.println(url2);
        URL url3 = Object.class.getResource("/java/lang/Class.class"); // 相同模块
        System.out.println(url3);
        URL url4 = Object.class.getResource("/java/sql/Driver.class"); // 不同模块
        System.out.println(url4);
        url4 = new URL("jrt:/java.sql/java/sql/Driver.class"); // 指定模块
        System.out.println(url4);

        ModuleLayer bootLayer = ModuleLayer.boot();
        Optional<Module> m = bootLayer.findModule("cn.zxf.test");
        if (m.isPresent()) {
            Module testModule = m.get();
            String resource = "test.txt";
            InputStream input = testModule.getResourceAsStream(resource);
            if (input != null) {
                System.out.println(resource + " found.");
            } else {
                System.out.println(resource + " not found.");
            }
        } else {
            System.out.println("Module com.jdojo.resource does not exist");
        }
    }

}
