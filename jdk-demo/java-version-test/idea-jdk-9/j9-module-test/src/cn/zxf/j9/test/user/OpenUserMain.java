package cn.zxf.j9.test.user;

import cn.zxf.j9.user.open.OpenUser;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;

/**
 * Created by zengxf on 2017/10/9.
 */
public class OpenUserMain {

    public static void main(String[] arr) {
        System.out.println(OpenUser.class.getModule().getName());

        try {
            Field field = OpenUser.class.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InaccessibleObjectException e) {
            System.out.println("模块没有打开");
            e.printStackTrace();
        }

        try {
            Method method = OpenUser.class.getDeclaredMethod("testPri");
            System.out.println(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

}
