package cn.zxf.test.base.module;

import java.lang.reflect.Method;

/**
 * Created by zengxf on 2018/9/11.
 */
public class TestModuleLayer {

    public static void main(String[] arr) {
        ModuleLayer layer = ModuleLayer.boot();
        System.out.println(layer);
        try {
            Class<?> cls = layer.findLoader("cn.zxf.test")
                    .loadClass("cn.zxf.test.base.module.TestModuleLayer");
            Object obj = cls.getConstructor().newInstance();
            Method method = cls.getMethod("test");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {
        System.out.println("-------------------------");
    }

}
