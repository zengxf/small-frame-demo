package cn.zxf.test.base.classloader;

/**
 * Created by zengxf on 2018/9/11.
 */
public class TestClassLoader {

    public static void main(String[] arr) {
        System.out.println(TestClassLoader.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println("-----------------");
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        System.out.println(ClassLoader.getPlatformClassLoader());
        System.out.println(ClassLoader.getPlatformClassLoader().getParent());
        System.out.println("-----------------");
        System.out.println(java.util.Date.class.getClassLoader());
        System.out.println(java.beans.Beans.class.getClassLoader());
        System.out.println(java.util.logging.Logger.class.getClassLoader());
        System.out.println(java.util.prefs.PreferenceChangeEvent.class.getClassLoader());
        System.out.println("-----------------");
        System.out.println(java.sql.Date.class.getClassLoader());
        System.out.println("-----------------");
        System.out.println(jdk.tools.jlink.builder.ImageBuilder.class.getClassLoader());
        System.out.println("-----------------");
    }

}
