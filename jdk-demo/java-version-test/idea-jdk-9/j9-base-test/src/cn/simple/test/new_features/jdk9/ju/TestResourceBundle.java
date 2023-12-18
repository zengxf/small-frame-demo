package cn.simple.test.new_features.jdk9.ju;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国际化
 * <p>
 * Created by zengxf on 2017/11/22.
 */
public class TestResourceBundle {

    public static void main(String[] arr) {
        ResourceBundle res = ResourceBundle.getBundle("resources/test"); // xx_zh_CN.properties，默认 zh_CN
        System.out.println(res.keySet());
        ResourceBundle bundle = ResourceBundle.getBundle("resources/test", Locale.CHINESE); // xx_zh_CN.properties
        String label = bundle.getString("label"); // label=测试
        System.out.println(label);
    }

}
