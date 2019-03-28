package cn.simple.test.new_features.jdk9.jup;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by zengxf on 2017/11/23.
 */
public class TestPreferences {

    public static void main(String[] arr) throws BackingStoreException {
        Preferences root = Preferences.userRoot();
        System.out.println("user-root: " + root);
        System.out.println("user-root: " + Arrays.toString(root.keys()));
//        System.out.println("system-root: " + Arrays.toString(Preferences.systemRoot().keys()));
//        System.out.println("system-root: " + Preferences.systemRoot());
        Preferences node = root.node("resources/test_zh_CN.properties");
        System.out.println("node: " + node);
        System.out.println("test: " + node.get("test", "无"));
//        System.out.println(Preferences.systemRoot().get("JAVA_HOME", "无"));
    }

}
