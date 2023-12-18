package cn.simple.test.new_features.jdk9.ju;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by zengxf on 2017/10/9.
 */
public class TestServiceLoader {
    public static void main(String[] arr) {
        ServiceLoader loader = ServiceLoader.load(SayService.class);
        Iterator<SayService> iterator = loader.iterator();
        if (iterator.hasNext()) {
            SayService say = iterator.next();
            say.say();
        }
    }

    static class SayService {
        void say() {
            System.out.println("hello");
        }
    }
}
