package cn.zxf.test.base.version;

import java.lang.Runtime.Version;

/**
 * 测试版本
 * <p>
 * Created by zengxf on 2018/9/11.
 */
public class TestVersion {

    public static void main(String[] arr) {
        Version version = Version.parse("9.0.1-ea+132");
        System.out.println(version);
        System.out.println(version.major());
        System.out.println(version.minor());
        System.out.println(version.build().orElse(0));
    }

}
